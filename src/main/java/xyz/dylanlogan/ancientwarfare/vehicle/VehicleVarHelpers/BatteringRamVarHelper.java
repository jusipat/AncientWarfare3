package xyz.dylanlogan.ancientwarfare.vehicle.VehicleVarHelpers;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.types.VehicleTypeBatteringRam;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleSounds;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.DamageType;

import java.util.List;
import java.util.Random;

public class BatteringRamVarHelper extends VehicleFiringVarsHelper {

	float logAngle = 0.f;
	float logSpeed = 0.f;

	/**
	 * @param vehicle
	 */
	public BatteringRamVarHelper(VehicleBase vehicle) {
		super(vehicle);
	}

	@Override
	public void onFiringUpdate() {
		if (logAngle >= 30) {
			vehicle.firingHelper.startLaunching();
			logSpeed = 0;
		} else {
			logAngle++;
			logSpeed = 1;
		}
	}

	@Override
	public void onReloadUpdate() {
		if (logAngle < 0) {
			logAngle++;
			logSpeed = 1;
		} else {
			logAngle = 0;
			logSpeed = 0;
		}
	}

	@Override
	public void onLaunchingUpdate() {
		if (logAngle <= -30) {
			vehicle.firingHelper.setFinishedLaunching();
			doDamageEffects();
			logSpeed = 0;
		} else {
			logAngle -= 2;
			logSpeed = -2;
		}
	}

	public void doDamageEffects() {
		if (vehicle.worldObj.isRemote) {
			return;
		}
		BlockPos[] effectedPositions = VehicleTypeBatteringRam.getEffectedPositions(vehicle);
		AxisAlignedBB bb;
		List<Entity> hitEntities;
		for (BlockPos pos : effectedPositions) {
			if (pos == null) {
				continue;
			}
			bb = AxisAlignedBB.getBoundingBox(pos.x, pos.y, pos.z, pos.x+1,pos.y+1, pos.z+1);
			hitEntities = vehicle.worldObj.getEntitiesWithinAABBExcludingEntity(vehicle, bb);
			if (hitEntities != null) {
				boolean firstGateBlock = true; // only used if a gate was hit
				for (Entity ent : hitEntities) {
					System.out.println("entity: " + ent);
					ent.attackEntityFrom(DamageType.batteringDamage, 5 + vehicle.vehicleMaterialLevel);
					if (ent instanceof EntityGate) {
						String gateTypeName = (((EntityGate) ent).getGateType().getDisplayName());
						if (gateTypeName.contains("wood") && firstGateBlock) {
							//ent.playSound(AWVehicleSounds.BATTERING_RAM_HIT_WOOD, 3, 1);
							firstGateBlock = false; // makes playing the sound only once, the gate can hit the Gate entity multiple times at once
						} else if (gateTypeName.contains("iron") && firstGateBlock) {
							System.out.println("sound played");
							//ent.playSound(AWVehicleSounds.BATTERING_RAM_HIT_IRON, 3, 1);
							firstGateBlock = false;
						}
					}
				}
			}
			if (AWVehicleStatics.generalSettings.batteringRamBreaksBlocks) {
				Random rand = new Random();
				if (rand.nextDouble() < ((double) AWVehicleStatics.generalSettings.batteringRamBlockBreakPercentageChance / 100)) {
					BlockTools.breakBlockAndDrop(vehicle.worldObj, null, pos.x, pos.y, pos.z);
				}
			}
		}
	}

	@Override
	public void onReloadingFinished() {
		logAngle = 0;
		logSpeed = 0;
	}

	@Override
	public float getVar1() {
		return logAngle;
	}

	@Override
	public float getVar2() {
		return logSpeed;
	}

	@Override
	public float getVar3() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar4() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar5() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar6() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar7() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar8() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setFloat("lA", logAngle);
		tag.setFloat("lS", logSpeed);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		logAngle = tag.getFloat("lA");
		logSpeed = tag.getFloat("lS");
	}

	@Override
	public void init(Entity entity, World world) {

	}
}
