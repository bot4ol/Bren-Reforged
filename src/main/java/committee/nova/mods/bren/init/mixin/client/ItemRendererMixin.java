package committee.nova.mods.bren.init.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import committee.nova.mods.bren.client.renderer.WeaponTickHolder;
import committee.nova.mods.bren.Bren;
import committee.nova.mods.bren.common.entity.IGunUser;
import committee.nova.mods.bren.init.registry.AttributeReg;
import committee.nova.mods.bren.common.item.GunItem;
import committee.nova.mods.bren.common.item.GunWithMagItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @ModifyVariable(at = @At("HEAD"), method = "render", ordinal = 0, argsOnly = true)
    private BakedModel editGuiModel(BakedModel defaultModel, ItemStack stack, ItemDisplayContext renderMode) {

        if (renderMode == ItemDisplayContext.GUI || renderMode == ItemDisplayContext.FIXED || renderMode == ItemDisplayContext.GROUND) {
            if (stack.getItem() instanceof GunItem gunItem) {
                if (!gunItem.hasGUIModel()) {
                    return defaultModel;
                }
                return bakeGuiModel(stack);
            }
        }
        return defaultModel;
    }

    private BakedModel bakeGuiModel(ItemStack stack) {
        var identifier = ForgeRegistries.ITEMS.getKey(stack.getItem());
        String itemName = identifier.getPath();
        String formattedName = itemName.toLowerCase().replace(' ', '_');
        BakedModel bakedModel;
        if (!GunWithMagItem.hasMagazine(stack)) {
            bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(Bren.MODID, formattedName + "_gui", "inventory"));
        } else {
            bakedModel = !GunWithMagItem.hasColorableMagazine(stack) ?
                    this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(Bren.MODID, formattedName + "_with_magazine_gui", "inventory")) :
                    this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(Bren.MODID, formattedName + "_with_clothed_magazine_gui", "inventory"));

        }
        return bakedModel;
    }


    @Inject(at = @At("HEAD"), method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V")
    private void render(LivingEntity entity, ItemStack item, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource pBuffer, Level pLevel, int pCombinedLight, int pCombinedOverlay, int pSeed, CallbackInfo ci) {
        if (entity != null) {

            var minecraftClient = Minecraft.getInstance();
            float delta = minecraftClient.getFrameTime();

            if (item.getItem() instanceof GunItem gunItem && entity.getOffhandItem() != item) {

                if (entity instanceof IGunUser gunUser) {

                    float f1 = 0;

                    if (entity instanceof Player player) {
                        var cooldownManager = player.getCooldowns();
                        f1 = cooldownManager.getCooldownPercent(item.getItem(), delta);
                        f1 = Math.max(f1 - 0.15F, 0);
                    }

                    boolean customMatrix = gunItem.applyCustomMatrix(entity, gunUser.getGunState(), matrices, item, f1, renderMode, leftHanded);

                    if (!customMatrix && f1 <= 0.95F) {

                        float f = 1 - WeaponTickHolder.getAnimationTicks(delta)/16;
                        boolean reloading = WeaponTickHolder.getAnimationTicks(delta) == 0;

                        float rangedDamage = (float)entity.getAttributeValue(AttributeReg.RANGED_DAMAGE.get());
                        float kick = !reloading ? Math.max(rangedDamage, 4) / 4 : 1;

                        if (renderMode.firstPerson()) {

                            float sin = (float) Math.sin((f * 2 - 0.5) * Math.PI) * 0.5F + 0.5F;
                            float sin2 = (float) Math.sin((f1 * 2 - 0.5) * Math.PI) * 0.5F + 0.5F;
                            float sin3 = reloading ? sin2 : (float) Math.sin(1 - f);

                            double d = (Math.sin(((float) entity.tickCount + delta) / 2) * (reloading ? sin2 : f1)) * 30;

                            matrices.translate(0, 0, reloading ? 0 : sin / 2 + f1 / 4);
                            matrices.mulPose(Axis.ZP.rotationDegrees((float) (leftHanded ? -15 + d : 15 + d)));
                            matrices.mulPose(Axis.XP.rotationDegrees((sin3 * 10) * kick));

                        } else {
                            float z = Math.max((1 - f + f1) / 2, 0);
                            float f2 = reloading ? ((float) Math.sin((f1 * 2 - 0.5) * Math.PI) * 0.5F + 0.5F) / 3 : z;
                            //matrices.mulPose(Axis.YP.rotationDegrees(leftHanded ? 10 : -10));
                            matrices.mulPose(Axis.ZP.rotationDegrees(leftHanded ? 45 : -45));
                            matrices.mulPose(Axis.XP.rotationDegrees(f2 * 30 + 45));

                            matrices.translate(0, -f2 / 4 + 0.25F, f2 / 8 - 0.25F);
                        }
                    }
                }
            }
        }
    }
}
