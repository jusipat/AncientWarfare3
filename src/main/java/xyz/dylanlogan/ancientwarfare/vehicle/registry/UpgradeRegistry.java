package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemUpgrade;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UpgradeRegistry {

	public static IVehicleUpgradeType speedUpgrade;
	public static IVehicleUpgradeType aimUpgrade;
	public static IVehicleUpgradeType reloadUpgrade;

	public static IVehicleUpgradeType powerUpgrade;
	public static IVehicleUpgradeType pitchExtUpgrade;
	public static IVehicleUpgradeType pitchUpUpgrade;
	public static IVehicleUpgradeType pitchDownUpgrade;

	private static Map<String, IVehicleUpgradeType> upgradeInstances = new HashMap<>();

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
		return upgradeInstances.values();
	}

	/**
	 * Called during init to register upgrade types as items.
	 */
	public static void registerUpgrades() {
		speedUpgrade = registerUpgrade(new VehicleUpgradeSpeed(), "speed_upgrade");
		aimUpgrade = registerUpgrade(new VehicleUpgradeAim(), "aim_upgrade");
		reloadUpgrade = registerUpgrade(new VehicleUpgradeReload(), "reload_upgrade");
		powerUpgrade = registerUpgrade(new VehicleUpgradePower(), "power_upgrade");
		pitchExtUpgrade = registerUpgrade(new VehicleUpgradeTurretPitch(), "pitch_ext_upgrade");
		pitchUpUpgrade = registerUpgrade(new VehicleUpgradePitchUp(), "pitch_up_upgrade");
		pitchDownUpgrade = registerUpgrade(new VehicleUpgradePitchDown(), "pitch_down_upgrade");
	}

	private static IVehicleUpgradeType registerUpgrade(IVehicleUpgradeType upgrade, String registryName) {
		upgradeInstances.put(registryName, upgrade);
		ItemUpgrade item = new ItemUpgrade(registryName);
		item.setUnlocalizedName(registryName);
		GameRegistry.registerItem(item, registryName); // Register the item with GameRegistry
		return upgrade;
	}

	public static IVehicleUpgradeType getUpgrade(String type) {
		return upgradeInstances.get(type);
	}

	public static IVehicleUpgradeType getUpgrade(ItemStack stack) {
		if (stack == null || stack.getItem() == null) {
			return null;
		}
		return upgradeInstances.get(stack.getUnlocalizedName());
	}
}
