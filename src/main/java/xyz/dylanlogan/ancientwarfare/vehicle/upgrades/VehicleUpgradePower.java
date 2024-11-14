package xyz.dylanlogan.ancientwarfare.vehicle.upgrades;

import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

public class VehicleUpgradePower extends VehicleUpgradeBase {

	public VehicleUpgradePower() {
		super("vehicle_upgrade_power");
	}

	@Override
	public void applyVehicleEffects(VehicleBase vehicle) {
		vehicle.currentLaunchSpeedPowerMax *= 1.1f;
	}

}
