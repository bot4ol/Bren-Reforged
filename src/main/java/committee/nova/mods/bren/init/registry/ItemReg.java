package committee.nova.mods.bren.init.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import committee.nova.mods.bren.Bren;
import committee.nova.mods.bren.common.item.*;
import committee.nova.mods.bren.init.config.MConfig;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemReg {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bren.MODID);

    // Machine Gun
    public static final float MACHINE_GUN_RECOIL = 9f;
    public static final float MACHINE_GUN_DAMAGE = MConfig.machineGunDamage.get();
    public static final float N_MACHINE_GUN_DAMAGE = MConfig.netheriteMachineGunDamage.get();

    // Auto-Gun
    public static final float AUTO_GUN_RECOIL = 12f;
    public static final float AUTO_GUN_DAMAGE = MConfig.autoGunDamage.get();
    public static final float N_AUTO_GUN_DAMAGE = MConfig.netheriteAutoGunDamage.get();

    // Rifle
    public static final float RIFLE_RECOIL = 22f;
    public static final float RIFLE_DAMAGE = MConfig.rifleDamage.get();
    public static final float N_RIFLE_DAMAGE = MConfig.netheriteRifleDamage.get();

    // Shotgun
    public static final float SHOTGUN_RECOIL = 25f;
    public static final float SHOTGUN_DAMAGE = MConfig.shotgunDamage.get();
    public static final float N_SHOTGUN_DAMAGE = MConfig.netheriteShotgunDamage.get();

    // Revolver
    public static final float REVOLVER_RECOIL = 15f;
    public static final float REVOLVER_DAMAGE = MConfig.revolverDamage.get();
    public static final float N_REVOLVER_DAMAGE = MConfig.netheriteRevolverDamage.get();

    public static final RegistryObject<Item>MACHINE_GUN = item("machine_gun",  (s) -> new MachineGunItem(
            new Item.Properties(), Tiers.IRON, TagReg.MEDIUM_MAGAZINES, new GunProperties().rangedDamage(MACHINE_GUN_DAMAGE).fireRate(3).recoil(MACHINE_GUN_RECOIL)
            .shootSound(SoundReg.ITEM_MACHINE_GUN_SHOOT, SoundReg.ITEM_MACHINE_GUN_SHOOT_SILENCED)));

    public static final RegistryObject<Item>NETHERITE_MACHINE_GUN = item("netherite_machine_gun",  (s) -> new MachineGunItem(
            new Item.Properties().fireResistant(), Tiers.NETHERITE, TagReg.MEDIUM_MAGAZINES, new GunProperties().rangedDamage(N_MACHINE_GUN_DAMAGE).fireRate(3).recoil(MACHINE_GUN_RECOIL)
            .shootSound(SoundReg.ITEM_MACHINE_GUN_SHOOT, SoundReg.ITEM_MACHINE_GUN_SHOOT_SILENCED)));

    public static final RegistryObject<Item>AUTO_GUN = item("auto_gun", (s) ->  new GunWithMagItem(
            new Item.Properties(), Tiers.IRON, TagReg.MEDIUM_MAGAZINES, new GunProperties().rangedDamage(AUTO_GUN_DAMAGE).fireRate(5).recoil(AUTO_GUN_RECOIL)
            .shootSound(SoundReg.ITEM_AUTO_GUN_SHOOT, SoundReg.ITEM_AUTO_GUN_SHOOT_SILENCED)));

    public static final RegistryObject<Item>NETHERITE_AUTO_GUN = item("netherite_auto_gun",  (s) -> new GunWithMagItem(
            new Item.Properties().fireResistant(), Tiers.NETHERITE, TagReg.MEDIUM_MAGAZINES, new GunProperties().rangedDamage(N_AUTO_GUN_DAMAGE).fireRate(4).recoil(AUTO_GUN_RECOIL)
            .shootSound(SoundReg.ITEM_AUTO_GUN_SHOOT, SoundReg.ITEM_AUTO_GUN_SHOOT_SILENCED)));

    public static final RegistryObject<Item>RIFLE = item("rifle",  (s) -> new GunWithMagItem(
            new Item.Properties(), Tiers.IRON, TagReg.SHORT_MAGAZINES, new GunProperties().rangedDamage(RIFLE_DAMAGE).fireRate(20).recoil(RIFLE_RECOIL)
            .shootSound(SoundReg.ITEM_RIFLE_SHOOT, SoundReg.ITEM_RIFLE_SHOOT_SILENCED)));

    public static final RegistryObject<Item>NETHERITE_RIFLE = item("netherite_rifle",  (s) -> new GunWithMagItem(
            new Item.Properties().fireResistant(), Tiers.NETHERITE, TagReg.SHORT_MAGAZINES, new GunProperties().rangedDamage(N_RIFLE_DAMAGE).fireRate(20).recoil(RIFLE_RECOIL)
            .shootSound(SoundReg.ITEM_RIFLE_SHOOT, SoundReg.ITEM_RIFLE_SHOOT_SILENCED)));

    public static final RegistryObject<Item>SHOTGUN = item("shotgun",  (s) -> new ShotgunItem(
            new Item.Properties(), Tiers.IRON, new GunProperties().rangedDamage(SHOTGUN_DAMAGE).fireRate(20).recoil(SHOTGUN_RECOIL).bulletSpeed(1.8F)
            .shootSound(SoundReg.ITEM_SHOTGUN_SHOOT, null)));

    public static final RegistryObject<Item>NETHERITE_SHOTGUN = item("netherite_shotgun",  (s) -> new ShotgunItem(
            new Item.Properties().fireResistant(), Tiers.NETHERITE, new GunProperties().rangedDamage(N_SHOTGUN_DAMAGE).fireRate(20).recoil(SHOTGUN_RECOIL).bulletSpeed(1.5F)
            .shootSound(SoundReg.ITEM_SHOTGUN_SHOOT, null)));

    public static final RegistryObject<Item>REVOLVER = item("revolver",  (s) -> new RevolverItem(
            new Item.Properties(), Tiers.IRON, new GunProperties().rangedDamage(REVOLVER_DAMAGE).fireRate(15).recoil(REVOLVER_RECOIL)
            .shootSound(SoundReg.ITEM_REVOLVER_SHOOT, null)));

    public static final RegistryObject<Item>NETHERITE_REVOLVER = item("netherite_revolver", (s) ->  new RevolverItem(
            new Item.Properties().fireResistant(), Tiers.NETHERITE, new GunProperties().rangedDamage(N_REVOLVER_DAMAGE).fireRate(15).recoil(REVOLVER_RECOIL)
            .shootSound(SoundReg.ITEM_REVOLVER_SHOOT, null)));
    
    public static final RegistryObject<Item> WINCHESTER = item("winchester", (s) -> new LeverActionItem(
        new Item.Properties(), Tiers.IRON, new GunProperties()
                .rangedDamage(MConfig.winchesterDamage.get())
                .fireRate(20)
                .recoil(20f)
                .shootSound(SoundReg.ITEM_RIFLE_SHOOT, SoundReg.ITEM_RIFLE_SHOOT_SILENCED)));
    
    public static final RegistryObject<Item> NETHERITE_WINCHESTER = item("netherite_winchester", (s) -> new LeverActionItem(
        new Item.Properties(), Tiers.IRON, new GunProperties()
                .rangedDamage(MConfig.netheritewinchesterDamage.get())
                .fireRate(20) 
                .recoil(20f) 
                .shootSound(SoundReg.ITEM_RIFLE_SHOOT, SoundReg.ITEM_RIFLE_SHOOT_SILENCED)));

    public static final RegistryObject<Item>MAGAZINE = item("magazine",  (s) -> new MagazineItem(new Item.Properties(), 20));
    public static final RegistryObject<Item>CLOTHED_MAGAZINE = item("clothed_magazine",  (s) -> new ColorableMagazineItem(new Item.Properties(), 20));

    public static final RegistryObject<Item>SHORT_MAGAZINE = item("short_magazine",  (s) -> new MagazineItem(new Item.Properties(), 6));

    public static final RegistryObject<Item>BULLET = item("bullet",  (s) -> new Item(new Item.Properties()));
    public static final RegistryObject<Item>SHELL = item("shell",  (s) -> new Item(new Item.Properties()));
    public static final RegistryObject<Item>AUTO_LOADER_CONTRAPTION = item("auto_loader_contraption",  (s) -> new Item(new Item.Properties()));
    public static final RegistryObject<Item>METAL_TUBE = item("metal_tube",  (s) -> new Item(new Item.Properties()));

    public static final RegistryObject<Item>WORKBENCH = item("workbench",  (s) -> new BlockItem(BlockReg.WORKBENCH.get(), new Item.Properties()));


    public static RegistryObject<Item> item(String name, Function<String, Item> item) {
        return item(name, item, true);
    }

    public static RegistryObject<Item> item(String name, Function<String, Item> item, boolean exist) {
        return item(name, () -> item.apply(name), exist);
    }

    public static RegistryObject<Item> item(String name, Supplier<Item> item) {
        return item(name, item, true);
    }

    public static RegistryObject<Item> item(String name, Supplier<Item> item, boolean exist) {
        var regItem = ITEMS.register(name, item);
        if (exist) TabReg.ACCEPT_ITEM.add(regItem);
        return regItem;
    }

    public static void reg(){}
}
