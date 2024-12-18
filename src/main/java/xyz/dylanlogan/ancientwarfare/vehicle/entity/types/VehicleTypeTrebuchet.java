package xyz.dylanlogan.ancientwarfare.vehicle.entity.types;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.materials.VehicleMaterial;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleSounds;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

public class VehicleTypeTrebuchet extends VehicleType {
	public VehicleTypeTrebuchet(int typeNum) {
		super(typeNum);
		configName = "trebuchet_base";
		vehicleMaterial = VehicleMaterial.materialWood;
		materialCount = 8;
		maxMissileWeight = 10.f;
		baseHealth = AWVehicleStatics.vehicleStats.vehicleTrebuchetHealth;
		validAmmoTypes.add(AmmoRegistry.ammoStoneShot10);
		validAmmoTypes.add(AmmoRegistry.ammoStoneShot15);
		validAmmoTypes.add(AmmoRegistry.ammoFireShot10);
		validAmmoTypes.add(AmmoRegistry.ammoFireShot15);
		validAmmoTypes.add(AmmoRegistry.ammoPebbleShot10);
		validAmmoTypes.add(AmmoRegistry.ammoPebbleShot15);
		validAmmoTypes.add(AmmoRegistry.ammoClusterShot10);
		validAmmoTypes.add(AmmoRegistry.ammoClusterShot15);
		validAmmoTypes.add(AmmoRegistry.ammoExplosive10);
		validAmmoTypes.add(AmmoRegistry.ammoExplosive15);
		validAmmoTypes.add(AmmoRegistry.ammoHE10);
		validAmmoTypes.add(AmmoRegistry.ammoHE15);
		validAmmoTypes.add(AmmoRegistry.ammoNapalm10);
		validAmmoTypes.add(AmmoRegistry.ammoNapalm15);

		if (AWVehicleStatics.generalSettings.oversizeAmmoEnabled) {
			validAmmoTypes.add(AmmoRegistry.ammoStoneShot30);
			validAmmoTypes.add(AmmoRegistry.ammoStoneShot45);
			validAmmoTypes.add(AmmoRegistry.ammoFireShot30);
			validAmmoTypes.add(AmmoRegistry.ammoFireShot45);
			validAmmoTypes.add(AmmoRegistry.ammoPebbleShot30);
			validAmmoTypes.add(AmmoRegistry.ammoPebbleShot45);
			validAmmoTypes.add(AmmoRegistry.ammoClusterShot30);
			validAmmoTypes.add(AmmoRegistry.ammoClusterShot45);
			validAmmoTypes.add(AmmoRegistry.ammoExplosive30);
			validAmmoTypes.add(AmmoRegistry.ammoExplosive45);
			validAmmoTypes.add(AmmoRegistry.ammoHE30);
			validAmmoTypes.add(AmmoRegistry.ammoHE45);
		}

		ammoBySoldierRank.put(0, AmmoRegistry.ammoStoneShot10);
		ammoBySoldierRank.put(1, AmmoRegistry.ammoStoneShot10);
		ammoBySoldierRank.put(2, AmmoRegistry.ammoStoneShot10);

		validArmors.add(ArmourRegistry.armorStone);
		validArmors.add(ArmourRegistry.armorIron);
		validArmors.add(ArmourRegistry.armorObsidian);

		validUpgrades.add(UpgradeRegistry.pitchDownUpgrade);
		validUpgrades.add(UpgradeRegistry.pitchUpUpgrade);
		validUpgrades.add(UpgradeRegistry.powerUpgrade);
		validUpgrades.add(UpgradeRegistry.reloadUpgrade);
		validUpgrades.add(UpgradeRegistry.aimUpgrade);

		displayName = "Trebuchet";
		//TODO fix display tooltip
		//  this.displayTooltip.add("Trebuchet Firing Mechanism");
		//  this.displayTooltip.add("Fixed-Emplacement");
		//  this.displayTooltip.add("No Turret");
		pitchAdjustable = false;
		powerAdjustable = true;
		yawAdjustable = false;
		mountable = true;
		combatEngine = true;
		drivable = true;
		riderSits = false;
		riderMovesWithTurret = false;
		baseForwardSpeed = 0.f;
		baseStrafeSpeed = 0.5f;
		ammoBaySize = 6;
		armorBaySize = 3;
		upgradeBaySize = 3;
		storageBaySize = 0;
		accuracy = 0.85f;

		basePitchMax = 70.f;
		basePitchMin = 70.f;
		turretVerticalOffset = (34.f + 67.5f + 24.0f) * 0.0625f;
		baseMissileVelocityMax = 40.f;
	}

	@Override
	public ResourceLocation getTextureForMaterialLevel(int level) {
		switch (level) {
			case 0:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_1.png");
			case 1:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_2.png");
			case 2:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_3.png");
			case 3:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_4.png");
			case 4:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_5.png");
			default:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/trebuchet_1.png");
		}
	}

	@Override
	public void playFiringSound(VehicleBase vehicleBase) {

		//vehicleBase.playSound(AWVehicleSounds.TREBUCHET_LAUNCH, 6, 1); todo: readd sound
	}

	@Override
	public void playReloadSound(VehicleBase vehicleBase) {

		//vehicleBase.playSound(AWVehicleSounds.TREBUCHET_RELOAD, 2, 1); todo: readd sound
	}

	@Override
	public VehicleFiringVarsHelper getFiringVarsHelper(VehicleBase veh) {
		return new TrebuchetVarHelper(veh);
	}

	public class TrebuchetVarHelper extends VehicleFiringVarsHelper {
		private float armAngle = -27.f;
		private float armSpeed = 0.f;
		private float stringAngle = -64.f;
		private float stringSpeed = 0.f;

		private TrebuchetVarHelper(VehicleBase vehicle) {
			super(vehicle);
		}

		@Override
		public void saveNBTData(NBTTagCompound tag) {
			armAngle = tag.getFloat("aA");
			armSpeed = tag.getFloat("aS");
			stringAngle = tag.getFloat("sA");
			stringSpeed = tag.getFloat("sS");
		}

		@Override
		public void loadNBTData(NBTTagCompound tag) {
			tag.setFloat("aA", armAngle);
			tag.setFloat("aS", armSpeed);
			tag.setFloat("sA", stringAngle);
			tag.setFloat("sS", stringSpeed);
		}

		@Override
		public void onFiringUpdate() {
			float increment = (90.f + 27.f) / 20.f;
			armAngle += increment;
			armSpeed = increment;
			stringAngle += 1.3162316f * increment;
			stringSpeed = 1.3162316f * increment;
			if (armAngle >= 90) {
				armSpeed = 0;
				armAngle = 90.f;
				stringAngle = 90.f;
				stringSpeed = 0.f;
				vehicle.firingHelper.startLaunching();
			}
		}

		@Override
		public void onReloadUpdate() {
			float increment = (90.f + 27.f) / (float) vehicle.currentReloadTicks;
			if (armAngle > -27) {
				armAngle -= increment;
				armSpeed = -increment;
				stringAngle -= 1.3162316f * increment;
				stringSpeed = -1.3162316f * increment;
			} else {
				armAngle = -27;
				armSpeed = 0;
				stringAngle = -64.f;
				stringSpeed = 0.f;
			}
		}

		@Override
		public void onLaunchingUpdate() {
			vehicle.firingHelper.spawnMissilesByWeightCount(0, 0, 0);
			vehicle.firingHelper.setFinishedLaunching();
		}

		@Override
		public void onReloadingFinished() {
			armAngle = -27;
			armSpeed = 0;
			stringAngle = -64.f;
			stringSpeed = 0.f;
		}

		@Override
		public float getVar1() {
			return armAngle;
		}

		@Override
		public float getVar2() {
			return armSpeed;
		}

		@Override
		public float getVar3() {
			return stringAngle;
		}

		@Override
		public float getVar4() {
			return stringSpeed;
		}

		@Override
		public float getVar5() {
			return 0;
		}

		@Override
		public float getVar6() {
			return 0;
		}

		@Override
		public float getVar7() {
			return 0;
		}

		@Override
		public float getVar8() {
			return 0;
		}

		@Override
		public void init(Entity entity, World world) {

		}
	}
}
