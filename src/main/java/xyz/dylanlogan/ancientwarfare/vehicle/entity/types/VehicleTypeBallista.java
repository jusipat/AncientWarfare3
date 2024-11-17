package xyz.dylanlogan.ancientwarfare.vehicle.entity.types;

import xyz.dylanlogan.ancientwarfare.vehicle.VehicleVarHelpers.BallistaVarHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.materials.VehicleMaterial;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleSounds;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

import java.util.Random;

public abstract class VehicleTypeBallista extends VehicleType {

	protected Random rand;

	public VehicleTypeBallista(int typeNum) {
		super(typeNum);
		configName = "ballista_base";

		vehicleMaterial = VehicleMaterial.materialWood;
		materialCount = 5;
		baseHealth = AWVehicleStatics.vehicleStats.vehicleBallistaHealth;

		maxMissileWeight = 2.f;

		validAmmoTypes.add(AmmoRegistry.ammoBallistaBolt);
		validAmmoTypes.add(AmmoRegistry.ammoBallistaBoltFlame);
		validAmmoTypes.add(AmmoRegistry.ammoBallistaBoltExplosive);
		validAmmoTypes.add(AmmoRegistry.ammoBallistaBoltIron);

		ammoBySoldierRank.put(0, AmmoRegistry.ammoBallistaBolt);
		ammoBySoldierRank.put(1, AmmoRegistry.ammoBallistaBolt);
		ammoBySoldierRank.put(2, AmmoRegistry.ammoBallistaBoltFlame);

		validUpgrades.add(UpgradeRegistry.pitchDownUpgrade);
		validUpgrades.add(UpgradeRegistry.pitchUpUpgrade);
		validUpgrades.add(UpgradeRegistry.pitchExtUpgrade);
		validUpgrades.add(UpgradeRegistry.powerUpgrade);
		validUpgrades.add(UpgradeRegistry.reloadUpgrade);
		validUpgrades.add(UpgradeRegistry.aimUpgrade);

		validArmors.add(ArmourRegistry.armorStone);
		validArmors.add(ArmourRegistry.armorObsidian);
		validArmors.add(ArmourRegistry.armorIron);

		storageBaySize = 0;
		accuracy = 0.98f;
		baseStrafeSpeed = 2.25f;
		baseForwardSpeed = 4.f * 0.05f;
		basePitchMax = 15;
		basePitchMin = -15;
		mountable = true;
		combatEngine = true;
		pitchAdjustable = true;
		powerAdjustable = false;


		/*
		 * default values that should be overriden by ballista types...
		 */
		baseMissileVelocityMax = 42.f;//stand versions should have higher velocity, as should fixed version--i.e. mobile turret should have the worst of all versions
		width = 2;
		height = 2;

		armorBaySize = 3;
		upgradeBaySize = 3;
		ammoBaySize = 6;

		drivable = false;//adjust based on isMobile or not
		yawAdjustable = false;//adjust based on hasTurret or not
		turretRotationMax = 360.f;//adjust based on mobile fixed (0), stand fixed(90'), or mobile or stand turret (360)
	}

	@Override
	public VehicleFiringVarsHelper getFiringVarsHelper(VehicleBase veh) {
		return new BallistaVarHelper(veh);
	}

	@Override
	public void playReloadSound(VehicleBase vehicleBase) {

		//vehicleBase.playSound(AWVehicleSounds.BALLISTA_RELOAD, 1, 1); // todo: reimp sounds
	}

	@Override
	public void playFiringSound(VehicleBase vehicleBase) {

		//vehicleBase.playSound(AWVehicleSounds.BALLISTA_LAUNCH, 6, 1);
	}
}
