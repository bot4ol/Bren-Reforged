package committee.nova.mods.bren.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import committee.nova.mods.bren.init.registry.EnchantmentReg;
import committee.nova.mods.bren.init.registry.ItemReg;
import committee.nova.mods.bren.init.registry.SoundReg;
import committee.nova.mods.bren.utils.GunHelper;

public class LeverActionItem extends BulletOnlyGun {

    public LeverActionItem(Properties settings, Tier material, GunProperties gunProperties) {
        super(settings, material, gunProperties);
    }

    @Override
    public int getMaxCapacity(ItemStack stack) {
        // Базовая ёмкость 8, увеличивается с уровнем зачарования Overflow
        return 8 * Math.round(Math.max(1, EnchantmentHelper.getItemEnchantmentLevel(EnchantmentReg.OVERFLOW.get(), stack) / 2));
    }

    @Override
    public int reloadSpeed() {
        return 16; // время на вставку одного патрона (в тиках)
    }

    @Override
    public Item compatibleBullet() {
        return ItemReg.BULLET.get(); // использует обычные пули
    }

    @Override
    public int bulletLifespan() {
        return 35; // как у винтовки
    }

    @Override
    public float spread() {
        return 0.0f; // высокая точность
    }

    @Override
    public int bulletAmount() {
        return 1; // один выстрел за раз
    }

    @Override
    public boolean applyCustomMatrix(LivingEntity entity, GunHelper.GunStates state, PoseStack matrices, ItemStack stack, float cooldownProgress, ItemDisplayContext renderMode, boolean leftHanded) {
        // Простая анимация перезарядки (подёргивание вверх-вниз)
        if (state == GunHelper.GunStates.RELOADING && cooldownProgress > 0) {
            float sin = (float) Math.sin((cooldownProgress * 2 - 0.5) * Math.PI) * 0.5F + 0.5F;
            if (renderMode.firstPerson()) {
                matrices.translate(0, sin * 0.1, 0);
            }
            matrices.mulPose(Axis.ZP.rotationDegrees(leftHanded ? -15 : 15 * sin));
        }
        return false; // стандартные трансформации не отключаем
    }

    @Override
    public boolean hasGUIModel() {
        return true; // используем стандартную модель в GUI (можно включить, если есть отдельная текстура)
    }

    @Override
    public boolean ejectCasing() {
        return true; // выбрасывать гильзы при стрельбе
    }

    @Override
    public boolean renderOnBack() {
        return true; // отображать на спине
    }

    @Override
    protected void afterInserted(ItemStack stack, Player player) {
        // Звук вставки патрона (используем звук револьвера)
        player.level().playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundReg.ITEM_REVOLVER_BULLET_INSERT,
                player.getSoundSource(), 1.0F, 1.0F - (player.getRandom().nextFloat() - 0.5F) / 4);
    }

    @Override
    protected void onFullyLoaded(ItemStack stack, Player player) {
        // Звук завершения перезарядки (например, щелчок затвора)
        player.level().playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundReg.ITEM_SHOTGUN_RACK,
                player.getSoundSource(), 1.0F, 1.0F - (player.getRandom().nextFloat() - 0.5F) / 4);
    }
}