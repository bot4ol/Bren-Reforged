package committee.nova.mods.bren.client.animations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import committee.nova.mods.bren.common.entity.IGunUser;
import committee.nova.mods.bren.utils.GunHelper;

public class GunEntityModelAnimator {
    public static void oneArm(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
                              ModelPart leftArm, ModelPart rightArm, ModelPart head, float gunAmount) {
        if (livingEntity instanceof IGunUser gunUser && !livingEntity.isSleeping()) {
            boolean isLeftHanded = livingEntity.getMainArm().equals(HumanoidArm.LEFT);

            float h_pi = 1.570796F;
            float p = headPitch * 0.01745329F;
            float y = netHeadYaw * 0.01745329F;

            ModelPart arm = isLeftHanded ? leftArm : rightArm;

            arm.yRot = y;
            arm.xRot = p - h_pi;
        }
    }

    public static void revolver(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
                              ModelPart leftArm, ModelPart rightArm, ModelPart head, float gunAmount) {
        if (livingEntity instanceof IGunUser gunUser && !livingEntity.isSleeping()) {
            boolean reloading = gunUser.getGunState().equals(GunHelper.GunStates.RELOADING);

            boolean isLeftHanded = livingEntity.getMainArm().equals(HumanoidArm.LEFT);
            ModelPart arm = isLeftHanded ? leftArm : rightArm;

            float rotX = 0;
            float rotY = 0;
            float f = 0;
            float f1 = 1.570796F;

            if (livingEntity instanceof Player player) {
                Minecraft client = Minecraft.getInstance();

                f = player.getCooldowns().getCooldownPercent(player.getMainHandItem().getItem(), client.getFrameTime());
            }

            float sin = reloading ? (float) Math.sin((f*2 - 0.5)*Math.PI) * 0.5F + 0.5F : 0;

            rotY = (float) (Math.cos(f*15)*0.08726646);
            rotX = (float) (Math.sin(f*15)*0.08726646) - sin;

            float p = headPitch * 0.01745329F;
            float y = netHeadYaw * 0.01745329F;

            arm.xRot = p - f1 + rotX;
            arm.yRot = y + rotY;
        }
    }

    public static void angles(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
                              ModelPart leftArm, ModelPart rightArm, ModelPart head, float gunAmount) {

        if (livingEntity instanceof IGunUser gunUser && !livingEntity.isSleeping()) {

            boolean reloading = gunUser.getGunState().equals(GunHelper.GunStates.RELOADING);
            float kick = 2.5F;

            gunAmount = Math.max(gunAmount - 0.15F, 0);
            float f = (((float)gunUser.getGunTicks()/16) + gunAmount)/2;
            float f1 = (float) (Math.sin(f)/Math.PI) * (kick/2);


            boolean isLeftHanded = livingEntity.getMainArm().equals(HumanoidArm.LEFT);
            ModelPart arm = isLeftHanded ? leftArm : rightArm;
            ModelPart other_arm = !isLeftHanded ? leftArm : rightArm;

            float l = isLeftHanded ? -1 : 1;

            float p = headPitch * 0.01745329F;
            float y = netHeadYaw * 0.01745329F;

            float f2 = f1*kick/2;

            float fr = ((float) Math.sin((gunAmount * 2 - 0.5) * Math.PI) * 0.5F + 0.5F);
            float f3 = reloading ? fr/4 : f2 ;
            float f4 = reloading ? (isLeftHanded ? -fr/4 : fr/4) : f2 * l;

            arm.yRot = isLeftHanded ? y + 0.7853982F : y - 0.7853982F;
            arm.xRot = 0.2181662F + p + f3/2;
            arm.zRot += f4;

            other_arm.xRot = -0.6981317F + p/3 - f3/2 - (reloading ? fr:0);
            other_arm.yRot = (isLeftHanded ? -1.090831F - y : 1.090831F + y) + (p/2) * l + f3/3;

            //head.yRot = y - 0.7853982F * l; это строчку нахуй никогда не трогать, я в рот ебал создателя этого порта, хули всё так криво?

        }
    }
}
