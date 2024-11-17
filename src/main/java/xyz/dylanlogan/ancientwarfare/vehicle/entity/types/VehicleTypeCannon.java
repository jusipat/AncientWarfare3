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
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

public class VehicleTypeCannon extends VehicleType {
	public VehicleTypeCannon(int typeNum) {
		super(typeNum);
		configName = "cannon_base";
		baseHealth = AWVehicleStatics.vehicleStats.vehicleCannonHealth;
		vehicleMaterial = VehicleMaterial.materialIron;
		materialCount = 5;
		maxMissileWeight = 10.f;

		validAmmoTypes.add(AmmoRegistry.ammoIronShot5);
		validAmmoTypes.add(AmmoRegistry.ammoIronShot10);
		validAmmoTypes.add(AmmoRegistry.ammoGrapeShot5);
		validAmmoTypes.add(AmmoRegistry.ammoGrapeShot10);
		validAmmoTypes.add(AmmoRegistry.ammoCanisterShot5);
		validAmmoTypes.add(AmmoRegistry.ammoCanisterShot10);

		if (AWVehicleStatics.generalSettings.oversizeAmmoEnabled) {
			validAmmoTypes.add(AmmoRegistry.ammoIronShot15);
			validAmmoTypes.add(AmmoRegistry.ammoIronShot25);
			validAmmoTypes.add(AmmoRegistry.ammoGrapeShot15);
			validAmmoTypes.add(AmmoRegistry.ammoGrapeShot25);
			validAmmoTypes.add(AmmoRegistry.ammoCanisterShot15);
			validAmmoTypes.add(AmmoRegistry.ammoCanisterShot25);
		}

		ammoBySoldierRank.put(0, AmmoRegistry.ammoIronShot5);
		ammoBySoldierRank.put(1, AmmoRegistry.ammoIronShot5);
		ammoBySoldierRank.put(2, AmmoRegistry.ammoIronShot5);

		validUpgrades.add(UpgradeRegistry.pitchDownUpgrade);
		validUpgrades.add(UpgradeRegistry.pitchUpUpgrade);
		validUpgrades.add(UpgradeRegistry.pitchExtUpgrade);
		validUpgrades.add(UpgradeRegistry.powerUpgrade);
		validUpgrades.add(UpgradeRegistry.reloadUpgrade);
		validUpgrades.add(UpgradeRegistry.aimUpgrade);

		validArmors.add(ArmourRegistry.armorStone);
		validArmors.add(ArmourRegistry.armorIron);
		validArmors.add(ArmourRegistry.armorObsidian);

		turretVerticalOffset = 11.5f * 0.0625f;
		storageBaySize = 0;
		accuracy = 0.98f;
		drivable = true;
		baseForwardSpeed = 0.f;
		baseStrafeSpeed = .75f;
		basePitchMax = 15;
		basePitchMin = -15;
		mountable = true;
		combatEngine = true;
		pitchAdjustable = true;
		powerAdjustable = false;
		yawAdjustable = false;

		baseMissileVelocityMax = 42.f;
		width = 2;
		height = 2;

		armorBaySize = 3;
		upgradeBaySize = 3;
		ammoBaySize = 6;
	}

	@Override
	public ResourceLocation getTextureForMaterialLevel(int level) {
		switch (level) {
			case 0:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_1.png");
			case 1:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_2.png");
			case 2:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_3.png");
			case 3:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_4.png");
			case 4:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_5.png");
			default:
				return new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/cannon_1.png");
		}
	}

	@Override
	public VehicleFiringVarsHelper getFiringVarsHelper(VehicleBase veh) {
		return new CannonVarHelper(veh);
	}

	public class CannonVarHelper extends VehicleFiringVarsHelper {

		private int firingTicks = 0;

		private CannonVarHelper(VehicleBase vehicle) {
			super(vehicle);
		}

		@Override
		public void saveNBTData(NBTTagCompound tag) {
			tag.setInteger("fT", firingTicks);
		}

		@Override
		public void loadNBTData(NBTTagCompound compound) {

		}

		@Override
		public void init(Entity entity, World world) {

		}

		@Override
		public void onFiringUpdate() {
			if (firingTicks == 0 && !vehicle.worldObj.isRemote) {
				vehicle.playSound("fireworks.launch", 0.50F, .25F);
				vehicle.playSound("game.tnt.primed", 1.0F, 0.5F);
			}
			firingTicks++;
			if (vehicle.worldObj.isRemote) {
				//TODO offset
				vehicle.worldObj.spawnParticle("smoke", vehicle.posX, vehicle.posY + 1.2d, vehicle.posZ, 0.0D, 0.05D, 0.0D);
			}
			if (firingTicks > 10) {
				if (!vehicle.worldObj.isRemote) {
					vehicle.playSound("random.explode", 1.f, 1.f);
				}
				vehicle.firingHelper.startLaunching();
				firingTicks = 0;
			}
		}

		@Override
		public void onReloadUpdate() {
			//noop
		}

		@Override
		public void onLaunchingUpdate() {
			vehicle.firingHelper.spawnMissile(0, 0, 0);
			//TODO play explosion sound
			//TODO spawn particles for explosion in direction of missile flight @ end of barrel (translate/offset)
			vehicle.firingHelper.setFinishedLaunching();
		}

		@Override
		public void onReloadingFinished() {
			firingTicks = 0;
		}

		@Override
		public float getVar1() {
			return 0;
		}

		@Override
		public float getVar2() {
			return 0;
		}

		@Override
		public float getVar3() {
			return 0;
		}

		@Override
		public float getVar4() {
			return 0;
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
	}
}
