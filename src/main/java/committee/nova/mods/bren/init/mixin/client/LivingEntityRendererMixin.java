package committee.nova.mods.bren.init.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import committee.nova.mods.bren.common.item.GunItem;
import committee.nova.mods.bren.common.PoseType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {


	@Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private void renderer(T livingEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
		double target_degree = livingEntity.getMainArm() == HumanoidArm.LEFT ? 135D : 45D;

		ItemStack s = livingEntity.getMainHandItem();

		if (s != null) {
			if (s.getItem() instanceof GunItem gunItem) {
				if (gunItem.holdingPose() == PoseType.TWO_ARMS) {
					if (!livingEntity.isPassenger()) {
						livingEntity.setYBodyRot(livingEntity.getYHeadRot() + 45);
					}
                    pPoseStack.mulPose(Axis.YN.rotation((float) Math.toRadians(target_degree - 90)));
				}
			}
		}
	}
}
