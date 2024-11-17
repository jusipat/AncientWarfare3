package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemUpgrade;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.IVehicleUpgradeType;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradeAim;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradePitchDown;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradePitchUp;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradePower;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradeReload;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradeSpeed;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.VehicleUpgradeTurretPitch;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpgradeRegistry {

	public static IVehicleUpgradeType speedUpgrade;
	public static IVehicleUpgradeType aimUpgrade;
	public static IVehicleUpgradeType reloadUpgrade;

	public static IVehicleUpgradeType powerUpgrade;
	public static IVehicleUpgradeType pitchExtUpgrade;
	public static IVehicleUpgradeType pitchUpUpgrade;
	public static IVehicleUpgradeType pitchDownUpgrade;

	private static Map<ResourceLocation, IVehicleUpgradeType> upgradeInstances = new HashMap<>();

	private UpgradeRegistry() {
	}

	public static UpgradeRegistry instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradeRegistry();
		}
		return INSTANCE;
	}

	private static UpgradeRegistry INSTANCE;

	public Collection<IVehicleUpgradeType> getUpgradeList() {
		return this.upgradeInstances.values();
	}

	/**
	 * called during init to register upgrade types as items
	 */
	public static void registerUpgrades(IForgeRegistry<Item> registry) {
		speedUpgrade = registerUpgrade(new VehicleUpgradeSpeed(), registry);
		aimUpgrade = registerUpgrade(new VehicleUpgradeAim(), registry);
		reloadUpgrade = registerUpgrade(new VehicleUpgradeReload(), registry);
		powerUpgrade = registerUpgrade(new VehicleUpgradePower(), registry);
		pitchExtUpgrade = registerUpgrade(new VehicleUpgradeTurretPitch(), registry);
		pitchUpUpgrade = registerUpgrade(new VehicleUpgradePitchUp(), registry);
		pitchDownUpgrade = registerUpgrade(new VehicleUpgradePitchDown(), registry);
	}

	private static IVehicleUpgradeType registerUpgrade(IVehicleUpgradeType upgrade, String registryName) {
		upgradeInstances.put(registryName, upgrade);
		ItemUpgrade item = new ItemUpgrade(registryName);
		item.setUnlocalizedName(registryName); // Set the unlocalized name for the item
		GameRegistry.registerItem(item, registryName); // Register the item with GameRegistry
		return upgrade;
	}


	public static Optional<IVehicleUpgradeType> getUpgrade(ResourceLocation type) {
		return Optional.ofNullable(upgradeInstances.get(type));
	}

	public static Optional<IVehicleUpgradeType> getUpgrade(ItemStack stack) {
		return Optional.ofNullable(upgradeInstances.get(stack.getItem().getRegistryName()));
	}
}
