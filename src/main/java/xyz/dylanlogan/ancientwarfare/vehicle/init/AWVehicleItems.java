import net.minecraft.item.Item;
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

	public static final List<Item> ITEMS = new ArrayList<>();

	public static final Item SPAWNER = new ItemSpawner();

	public static void registerItems() {
		registerItem(SPAWNER, "spawner");

		// Ammo materials
		registerItem(new ItemMisc("flame_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL), "flame_charge");
		registerItem(new ItemMisc("explosive_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL), "explosive_charge");
		registerItem(new ItemMisc("rocket_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL), "rocket_charge");
		registerItem(new ItemMisc("cluster_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL), "cluster_charge");
		registerItem(new ItemMisc("napalm_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL), "napalm_charge");
		registerItem(new ItemMisc("clay_casing", ItemMisc.VehicleItemType.AMMO_MATERIAL), "clay_casing");
		registerItem(new ItemMisc("iron_casing", ItemMisc.VehicleItemType.AMMO_MATERIAL), "iron_casing");

		// Vehicle components
		registerItem(new ItemMisc("mobility_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "mobility_unit");
		registerItem(new ItemMisc("turret_components", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "turret_components");
		registerItem(new ItemMisc("torsion_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "torsion_unit");
		registerItem(new ItemMisc("counter_weight_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "counter_weight_unit");
		registerItem(new ItemMisc("powder_case", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "powder_case");
		registerItem(new ItemMisc("equipment_bay", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "equipment_bay");

		// Materials
		registerItem(new ItemMisc("rough_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "rough_wood");
		registerItem(new ItemMisc("treated_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "treated_wood");
		registerItem(new ItemMisc("ironshod_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "ironshod_wood");
		registerItem(new ItemMisc("iron_core_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "iron_core_wood");

		// Additional vehicle components
		registerItem(new ItemMisc("rough_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "rough_iron");
		registerItem(new ItemMisc("fine_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "fine_iron");
		registerItem(new ItemMisc("tempered_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "tempered_iron");
		registerItem(new ItemMisc("minor_alloy", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "minor_alloy");
		registerItem(new ItemMisc("major_alloy", ItemMisc.VehicleItemType.VEHICLE_COMPONENT), "major_alloy");

		// Call other registries
		AmmoRegistry.registerAmmo();
		ArmourRegistry.registerArmorTypes();
		UpgradeRegistry.registerUpgrades();
		VehicleRegistry.registerVehicles();
	}

	private static void registerItem(Item item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
		ITEMS.add(item);
	}
}
