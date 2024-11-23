package xyz.dylanlogan.ancientwarfare.vehicle.init;

import net.minecraft.item.Item;
import xyz.dylanlogan.ancientwarfare.core.api.AWItems;
import xyz.dylanlogan.ancientwarfare.core.block.AWCoreBlockLoader;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemMisc;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemSpawner;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;

public class AWVehicleItems {
	private AWVehicleItems() {}

	public static final String PREFIX = "ancientwarfare:vehicle/";
	public static final String AMMO_PREFIX = "ancientwarfare:vehicle/ammo/ammo_";
	public static final String MISC_PREFIX = "ancientwarfare:vehicle/misc/";
	public static final AWVehicleItems INSTANCE = new AWVehicleItems();

	//public static final Item SPAWNER = new ItemSpawner();

	public void load() {
		AWItems.flameCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "flame_charge", AMMO_PREFIX);
		AWItems.explosiveCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "explosive_charge", AMMO_PREFIX);
		AWItems.rocketCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "rocket_charge", AMMO_PREFIX);
		AWItems.clusterCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "cluster_charge", AMMO_PREFIX);
		AWItems.napalmCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "napalm_charge", AMMO_PREFIX);
		AWItems.clayCasing = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "clay_casing", AMMO_PREFIX);
		AWItems.ironCasing = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "iron_casing", AMMO_PREFIX);

		AWItems.mobilityUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "mobility_unit", MISC_PREFIX);
		AWItems.turretComponents = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "turret_components", MISC_PREFIX);
		AWItems.torsionUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "torsion_unit", MISC_PREFIX);
		AWItems.counterWeightUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "counter_weight_unit", MISC_PREFIX);
		AWItems.powderCase = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "powder_case", MISC_PREFIX);
		AWItems.equipmentBay = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "equipment_bay", MISC_PREFIX);

		AWItems.roughWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_wood_1", MISC_PREFIX);
		AWItems.treatedWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_wood_2", MISC_PREFIX);
		AWItems.ironshodWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_wood_3", MISC_PREFIX);
		AWItems.ironCoreWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_wood_4", MISC_PREFIX);

		AWItems.roughIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_iron_1", MISC_PREFIX);
		AWItems.fineIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_iron_2", MISC_PREFIX);
		AWItems.temperedIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_iron_3", MISC_PREFIX);
		AWItems.minorAlloy = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_iron_4", MISC_PREFIX);
		AWItems.majorAlloy = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "material_iron_5", MISC_PREFIX);
	}




		public static void registerItems() {
		//registerItem(SPAWNER, "spawner");


		// Call other registries
		AmmoRegistry.registerAmmo();
		ArmourRegistry.registerArmorTypes();
		UpgradeRegistry.registerUpgrades();
		VehicleRegistry.registerVehicles();
	}

	public Item register(Item item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
		return item;
	}

	public Item register(Item item, String name, String textPrefix) {
		item.setTextureName(textPrefix + name);
		return register(item, name);
	}
}
