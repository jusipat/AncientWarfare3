package xyz.dylanlogan.ancientwarfare.vehicle.init;

import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import xyz.dylanlogan.ancientwarfare.core.item.ItemBase;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemMisc;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemSpawner;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleRegistry;

@GameRegistry.ObjectHolder(AncientWarfareVehicles.MOD_ID)
@EventBusSubscriber
public class AWVehicleItems {
	private AWVehicleItems() {}

	public static final ItemBase SPAWNER = InjectionTools.nullValue();

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(new ItemSpawner());
		//TODO do we really need items that are duplicates of ammo just for crafting?
		registry.register(new ItemMisc("flame_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("explosive_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("rocket_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("cluster_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("napalm_charge", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("clay_casing", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("iron_casing", ItemMisc.VehicleItemType.AMMO_MATERIAL));
		registry.register(new ItemMisc("mobility_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("turret_components", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("torsion_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("counter_weight_unit", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("powder_case", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("equipment_bay", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("rough_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("treated_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("ironshod_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("iron_core_wood", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("rough_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("fine_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("tempered_iron", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("minor_alloy", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));
		registry.register(new ItemMisc("major_alloy", ItemMisc.VehicleItemType.VEHICLE_COMPONENT));

		AmmoRegistry.registerAmmo(registry);
		ArmourRegistry.registerArmorTypes(registry);
		UpgradeRegistry.registerUpgrades(registry);
		VehicleRegistry.registerVehicles();
	}
}
