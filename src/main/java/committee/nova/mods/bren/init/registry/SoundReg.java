package committee.nova.mods.bren.init.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import committee.nova.mods.bren.Bren;

public class SoundReg {
    public static SoundEvent ITEM_MACHINE_GUN_SHOOT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.machine_gun.shoot"));
    public static SoundEvent ITEM_AUTO_GUN_SHOOT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.auto_gun.shoot"));
    public static SoundEvent ITEM_RIFLE_SHOOT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.rifle.shoot"));
    public static SoundEvent ITEM_RIFLE_SHOOT_SILENCED = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.rifle.shoot_silenced"));
    public static SoundEvent ITEM_SHOTGUN_SHOOT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.shotgun.shoot"));
    public static SoundEvent ITEM_SHOTGUN_SHELL_INSERT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.shotgun.shell_insert"));
    public static SoundEvent ITEM_SHOTGUN_RACK = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.shotgun.rack"));
    public static SoundEvent ITEM_MACHINE_GUN_SHOOT_SILENCED = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.machine_gun.shoot_silenced"));
    public static SoundEvent ITEM_AUTO_GUN_SHOOT_SILENCED = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.auto_gun.shoot_silenced"));
    public static SoundEvent ITEM_DISTANT_GUNFIRE = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.distant_gunfire"));
    public static SoundEvent ITEM_MAGAZINE_INSERT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.magazine_insert"));
    public static SoundEvent ITEM_MAGAZINE_REMOVE = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.magazine_remove"));
    public static SoundEvent ITEM_REVOLVER_SHOOT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.revolver.shoot"));
    public static SoundEvent ITEM_REVOLVER_BULLET_INSERT = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.revolver.bullet_insert"));
    public static SoundEvent ITEM_REVOLVER_RELOAD = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.revolver.reload"));
    public static SoundEvent ITEM_REVOLVER_SPINNING = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "item.revolver.spinning"));
    public static SoundEvent PARTICLE_CASING_BOUNCE = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "particle.casing.bounce"));
    public static SoundEvent ENTITY_VILLAGER_WORK_GUNSMITH = SoundEvent.createVariableRangeEvent(
        new ResourceLocation(Bren.MODID, "entity.villager.work_gunsmith"));
}
