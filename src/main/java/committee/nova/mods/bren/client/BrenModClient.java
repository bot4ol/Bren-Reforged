package committee.nova.mods.bren.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import committee.nova.mods.bren.client.features.GunBackFeatureRenderer;
import committee.nova.mods.bren.client.huds.HudOverlay;
import committee.nova.mods.bren.client.particle.AirRingParticle;
import committee.nova.mods.bren.client.particle.CasingParticle;
import committee.nova.mods.bren.client.particle.MuzzleSmokeParticle;
import committee.nova.mods.bren.client.renderer.BulletRenderer;
import committee.nova.mods.bren.Bren;
import committee.nova.mods.bren.init.config.MConfig;
import committee.nova.mods.bren.common.entity.BulletEntity;
import committee.nova.mods.bren.init.registry.EntityReg;
import committee.nova.mods.bren.init.registry.ItemReg;
import committee.nova.mods.bren.init.registry.KeyBindingReg;
import committee.nova.mods.bren.init.registry.ParticleReg;
import committee.nova.mods.bren.common.item.GunWithMagItem;
import committee.nova.mods.bren.utils.ModModelPredicateProvider;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import static committee.nova.mods.bren.Bren.MODID;

/**
 * @author: cnlimiter
 */
@Mod.EventBusSubscriber(modid = Bren.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BrenModClient {

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindingReg.reloadKey);
    }

    @SubscribeEvent
    public static void registerParticle(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleReg.MUZZLE_SMOKE_PARTICLE.get(), MuzzleSmokeParticle.Factory::new);
        event.registerSpriteSet(ParticleReg.AIR_RING_PARTICLE.get(), AirRingParticle.Factory::new);
        event.registerSpriteSet(ParticleReg.CASING_PARTICLE.get(), CasingParticle.Factory::new);

    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("bullet_hud", new HudOverlay());
    }

    @SubscribeEvent
    public static void clientSetUp(FMLClientSetupEvent event) {
        ModModelPredicateProvider.regModels();
        EntityRenderers.register(EntityReg.BULLET.get(), BulletRenderer::new);

    }
    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, index) -> index > 0 ? -1 : ((DyeableLeatherItem)(stack.getItem())).getColor(stack),
                ItemReg.CLOTHED_MAGAZINE.get()
        );
        event.register(
                (stack, index) -> index > 0 ? -1 :  ((GunWithMagItem)(stack.getItem())).getColor(stack),
                ItemReg.MACHINE_GUN.get(), ItemReg.AUTO_GUN.get(), ItemReg.NETHERITE_MACHINE_GUN.get(), ItemReg.NETHERITE_AUTO_GUN.get()
        );
    }


    @SubscribeEvent
    public static void addPlayerLayer(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            final LivingEntityRenderer entityRenderer = event.getSkin(skin);
            if (entityRenderer instanceof PlayerRenderer r && MConfig.renderGunOnBack.get()) {
                entityRenderer.addLayer(new GunBackFeatureRenderer((RenderLayerParent<Player, PlayerModel<Player>>) entityRenderer, event.getContext().getItemRenderer()));
            }
        }
    }

    @SubscribeEvent
    public static void modelsAdd(ModelEvent.RegisterAdditional event) {
        List<ModelResourceLocation> modelIdentifierList = new ArrayList<>();
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.MACHINE_GUN.get()), modelIdentifierList, true, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.AUTO_GUN.get()), modelIdentifierList, true, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.RIFLE.get()), modelIdentifierList, false, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.SHOTGUN.get()), modelIdentifierList, false, false);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.NETHERITE_MACHINE_GUN.get()), modelIdentifierList, true, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.NETHERITE_AUTO_GUN.get()), modelIdentifierList, true, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.NETHERITE_RIFLE.get()), modelIdentifierList, false, true);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.NETHERITE_SHOTGUN.get()), modelIdentifierList, false, false);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.WINCHESTER.get()), modelIdentifierList, false, false);
        registerGUIModels(ForgeRegistries.ITEMS.getKey(ItemReg.NETHERITE_WINCHESTER.get()), modelIdentifierList, false, false);
        for (ModelResourceLocation modelIdentifier : modelIdentifierList) {
            event.register(modelIdentifier);
        }
    }





    public static List<ModelResourceLocation> registerGUIModels(ResourceLocation id, List<ModelResourceLocation> modelIdentifierList, boolean clothed, boolean hasMagazine) {
        modelIdentifierList.add(new ModelResourceLocation(MODID, id.getPath() + "_" + "gui", "inventory"));
        if (hasMagazine) {
            modelIdentifierList.add(new ModelResourceLocation(MODID, id.getPath() + "_with_magazine_" + "gui", "inventory"));
        }
        if (clothed) {
            modelIdentifierList.add(new ModelResourceLocation(MODID, id.getPath() + "_with_clothed_magazine_" + "gui", "inventory"));
        }
        return modelIdentifierList;
    }

    // Copied from my wild west mod

    public static void renderImage(ResourceLocation icon, BulletEntity bullet, PoseStack matrices, EntityRenderDispatcher dispatcher) {

        double d = dispatcher.distanceToSqr(bullet);
        if (d > 4096.0) {
            return;
        }

        matrices.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float scale = 0.040F;

        matrices.translate(0, bullet.getBoundingBox().getYsize()/2, 0);
        matrices.mulPose(dispatcher.cameraOrientation());
        matrices.scale(-scale, -scale, scale);

        renderImage(icon, matrices, -8, -8, 16, 16, 0, 1, 0, 1, 1);

        matrices.popPose();
    }


    public static void renderImage(ResourceLocation icon, PoseStack matrixStack, int x, int y, int w, int h, float u0, float u1, float v0, float v1, float alpha) {
        Matrix4f matrix = matrixStack.last().pose();
        RenderSystem.enableDepthTest();
        Minecraft.getInstance().getTextureManager().getTexture(icon).setFilter(false, false);
        RenderSystem.setShaderTexture(0, icon);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix, (float)x,			(float)(y + h),	0).uv(u0, v1).color(1.0f, 1.0f, 1.0f, alpha).endVertex();
        bufferbuilder.vertex(matrix, (float)(x + w),	(float)(y + h),	0).uv(u1, v1).color(1.0f, 1.0f, 1.0f, alpha).endVertex();
        bufferbuilder.vertex(matrix, (float)(x + w),	(float)y,		0).uv(u1, v0).color(1.0f, 1.0f, 1.0f, alpha).endVertex();
        bufferbuilder.vertex(matrix, (float)x,			(float)y,		0).uv(u0, v0).color(1.0f, 1.0f, 1.0f, alpha).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableDepthTest();
    }
}
