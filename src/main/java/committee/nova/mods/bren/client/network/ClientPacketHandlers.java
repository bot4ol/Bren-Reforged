package committee.nova.mods.bren.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.phys.Vec3;
import committee.nova.mods.bren.client.renderer.RecoilSys;
import committee.nova.mods.bren.client.renderer.WeaponTickHolder;
import committee.nova.mods.bren.common.item.GunItem;
import committee.nova.mods.bren.init.config.MConfig;
import committee.nova.mods.bren.init.registry.SoundReg;

public final class ClientPacketHandlers {
    private ClientPacketHandlers() {
    }

    public static void handleShot(Vec3 origin, Vec3 direction, boolean ejectCasing) {
        var client = Minecraft.getInstance();
        var world = client.level;
        if (world == null) {
            return;
        }

        GunItem.shotParticles(world, origin, direction, world.getRandom());
        if (MConfig.spawnCasingParticles.get() && ejectCasing) {
            GunItem.ejectCasingParticle(world, origin, direction, world.getRandom());
        }
    }

    public static void handleRecoil(float recoil) {
        var client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }

        RecoilSys.shotEvent(client.player, recoil);
    }

    public static void handleShootAnimation() {
        WeaponTickHolder.setTicks(16);
    }

    public static void handleShootSound(float volume) {
        var client = Minecraft.getInstance();
        var world = client.level;
        if (world == null) {
            return;
        }

        var pitch = 1.0F - (world.getRandom().nextFloat() - 0.5F) / 8;
        var soundInstance = SimpleSoundInstance.forUI(SoundReg.ITEM_DISTANT_GUNFIRE, pitch, volume);
        client.getSoundManager().play(soundInstance);
    }
}