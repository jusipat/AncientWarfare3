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

	public static final String PREFIX = "ancientwarfare:vehicles/";
	public static final AWVehicleItems INSTANCE = new AWVehicleItems();

	public static final List<Item> ITEMS = new ArrayList<>();

	//public static final Item SPAWNER = new ItemSpawner();

	public void load() {
		AWItems.flameCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "flame_charge", PREFIX);
		AWItems.explosiveCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "explosive_charge", PREFIX);
		AWItems.rocketCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "rocket_charge", PREFIX);
		AWItems.clusterCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "cluster_charge", PREFIX);
		AWItems.napalmCharge = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "napalm_charge", PREFIX);
		AWItems.clayCasing = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "clay_casing", PREFIX);
		AWItems.ironCasing = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "iron_casing", PREFIX);

		AWItems.mobilityUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "mobility_unit", PREFIX);
		AWItems.turretComponents = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "turret_components", PREFIX);
		AWItems.torsionUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "torsion_unit", PREFIX);
		AWItems.counterWeightUnit = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "counter_weight_unit", PREFIX);
		AWItems.powderCase = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "powder_case", PREFIX);
		AWItems.equipmentBay = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "equipment_bay", PREFIX);

		AWItems.roughWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "rough_wood", PREFIX);
		AWItems.treatedWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "treated_wood", PREFIX);
		AWItems.ironshodWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "ironshod_wood", PREFIX);
		AWItems.ironCoreWood = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "iron_core_wood", PREFIX);

		AWItems.roughIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "rough_iron", PREFIX);
		AWItems.fineIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "fine_iron", PREFIX);
		AWItems.temperedIron = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "tempered_iron", PREFIX);
		AWItems.minorAlloy = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "minor_alloy", PREFIX);
		AWItems.majorAlloy = register(new Item().setCreativeTab(AWCoreBlockLoader.coreTab), "major_alloy", PREFIX);
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
