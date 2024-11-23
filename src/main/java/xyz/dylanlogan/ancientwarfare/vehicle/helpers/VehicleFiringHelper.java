/**
 * Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
 * This software is distributed under the terms of the GNU General Public License.
 * Please see COPYING for precise license information.
 * <p>
 * This file is part of Ancient Warfare.
 * <p>
 * Ancient Warfare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Ancient Warfare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Ancient Warfare.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.dylanlogan.ancientwarfare.vehicle.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import org.joml.Vector3d;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.ITarget;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleMovementType;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.Ammo;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.AmmoHwachaRocket;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.MissileBase;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAimUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketFireUpdate;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

/**
 * handles aiming, firing, updating turret, and client/server comms for input updates
 *
 * @author Shadowmage
 */
public class VehicleFiringHelper implements IExtendedEntityProperties {

	private static final int TRAJECTORY_ITERATIONS_CLIENT = 20;

	protected static Random rng = new Random();
	/**
	 * these values are updated when the client chooses an aim point, used by overlay rendering gui
	 */
	public float clientHitRange = 0.f;
	public float clientHitPosX = 0.f;
	public float clientHitPosY = 0.f;
	public float clientHitPosZ = 0.f;

	/**
	 * client-side values used by the riding player to check current input vs previous to see if new input packets should be sent...
	 */
	public float clientTurretYaw = 0.f;
	public float clientTurretPitch = 0.f;
	public float clientLaunchSpeed = 0.f;

	/**
	 * used on final launch, to calc final angle from 'approximate' firing arm/turret angle
	 */
	public Vector3d targetPos = null;

	/**
	 * is this vehicle in the process of launching a missile ? (animation, etc)
	 */
	private boolean isFiring = false;

	/**
	 * has started launching...
	 */
	private boolean isLaunching = false;
	/**
	 * if this vehicle isFiring, has it already finished launched, and is in the process of cooling down?
	 */
	private boolean isReloading = false;

	/**
	 * how many ticks until this vehicle is done reloading and can fire again
	 */
	private int reloadingTicks = 0;

	private VehicleBase vehicle;

	public VehicleFiringHelper(VehicleBase vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * spawns the number of missiles that this vehicle should fire by weight-count
	 * at the given offset from normal missile spawn position (offset is world
	 * coordinates, needs translating prior to being passed in)
	 */
	public void spawnMissilesByWeightCount(float ox, float oy, float oz) {
		int count = getMissileLaunchCount();
		for (int i = 0; i < count; i++) {
			spawnMissile(ox, oy, oz);
		}
	}

	/**
	 * spawn a missile of current missile type, with current firing paramaters, with additional raw x, y, z offsets
	 */
	public void spawnMissile(float ox, float oy, float oz) {
		if (!vehicle.worldObj.isRemote) {
			IAmmo ammo = vehicle.ammoHelper.getCurrentAmmoType();
			if (ammo == null) {
				return;
			}
			if (vehicle.ammoHelper.getCurrentAmmoCount() > 0) {
				vehicle.ammoHelper.decreaseCurrentAmmo();

				Vector3d off = vehicle.getMissileOffset();
				float x = (float) (vehicle.posX + off.x + ox);
				float y = (float) (vehicle.posY + off.y + oy);
				float z = (float) (vehicle.posZ + off.z + oz);

				int count = ammo.hasSecondaryAmmo() ? ammo.getSecondaryAmmoTypeCount() : 1;
				//      Config.logDebug("type: "+ammo.getDisplayName()+" missile count to fire: "+count + " hasSecondaryAmmo: "+ammo.hasSecondaryAmmo() + " secType: "+ammo.getSecondaryAmmoType());
				MissileBase missile = null;
				float maxPower;
				float yaw;
				float pitch;
				float accuracy;
				float power;
				for (int i = 0; i < count; i++) {
					maxPower = getAdjustedMaxMissileVelocity();
					power = Math.min(vehicle.localLaunchPower, maxPower);
					yaw = vehicle.localTurretRotation;
					pitch = vehicle.localTurretPitch + vehicle.rotationPitch;
					if (AWVehicleStatics.clientSettings.adjustMissilesForAccuracy) {
						accuracy = getAccuracyAdjusted();
						yaw += (float) rng.nextGaussian() * (1.f - accuracy) * 10.f;
						if (vehicle.canAimPower() && !ammo.isRocket()) {
							power += (float) rng.nextGaussian() * (1.f - accuracy) * 2.5f;
							if (power < 1.f) {
								power = 1.f;
							}
						} else if (vehicle.canAimPitch()) {
							pitch += (float) rng.nextGaussian() * (1.f - accuracy) * 10.f;
						} else if (ammo != null && ammo.isRocket()) {
							power += power / vehicle.currentLaunchSpeedPowerMax;
							pitch += (rng.nextFloat() * 2.f - 1.f) * (1.f - accuracy) * 50.f;
						}
					}
					missile = getMissile2(x, y, z, yaw, pitch, power);
					if (vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR1 || vehicle.vehicleType
							.getMovementType() == VehicleMovementType.AIR2) {
						missile.motionX += vehicle.motionX;
						missile.motionY += vehicle.motionY;
						missile.motionZ += vehicle.motionZ;
					}
					if (missile != null) {
						vehicle.worldObj.spawnEntityInWorld(missile);
					}
				}
			}
		}
	}

	MissileBase getMissile2(float x, float y, float z, float yaw, float pitch, float velocity) {
		IAmmo ammo = vehicle.ammoHelper.getCurrentAmmoType();
		if (ammo != null) {
			MissileBase missile = new MissileBase(vehicle.worldObj);
			if (ammo.hasSecondaryAmmo()) {
				ammo = ammo.getSecondaryAmmoType();
			}
			missile.setMissileParams2(ammo, x, y, z, yaw, pitch, velocity);
			missile.setMissileCallback(vehicle);
			missile.setLaunchingEntity(vehicle);
			missile.setShooter(vehicle.ridingEntity);
			return missile;
		}
		return null;
	}

	public void onTick() {
		if (this.isReloading) {
			this.vehicle.onReloadUpdate();
			if (this.reloadingTicks <= 0) {
				this.setFinishedReloading();
				this.vehicle.firingVarsHelper.onReloadingFinished();
			}
			this.reloadingTicks--;
		}
		if (this.isFiring) {
			vehicle.onFiringUpdate();
		}
		if (this.isLaunching) {
			vehicle.onLaunchingUpdate();
		}
		if (vehicle.worldObj.isRemote) {
			if (!vehicle.canAimPitch()) {
				this.clientTurretPitch = vehicle.localTurretPitch;
			}
			if (!vehicle.canAimPower()) {
				this.clientLaunchSpeed = vehicle.localLaunchPower;
			}
			if (!vehicle.canAimRotate()) {
				this.clientTurretYaw = vehicle.rotationYaw;
			}
		}
		if (!vehicle.canAimPower()) {
			vehicle.localLaunchPower = vehicle.currentLaunchSpeedPowerMax;
		}
		if (vehicle.canAimRotate()) {
			float diff = vehicle.rotationYaw - vehicle.prevRotationYaw;
			vehicle.localTurretRotation += diff;
			this.clientTurretYaw += diff;
		}
	}

	/**
	 * get how many missiles can be fired at the current missileType and weight
	 * will return at least 1
	 */
	public int getMissileLaunchCount() {
		IAmmo ammo = vehicle.ammoHelper.getCurrentAmmoType();
		int missileCount = 1;
		if (ammo != null) {
			missileCount = (int) (vehicle.vehicleType.getMaxMissileWeight() / ammo.getAmmoWeight());
			if (missileCount < 1) {
				missileCount = 1;
			}
		}
		return missileCount;
	}

	/**
	 * gets the adjusted max missile velocity--adjusted by missile weight percentage of vehicleMaxMissileWeight
	 */
	public float getAdjustedMaxMissileVelocity() {
		float velocity = vehicle.currentLaunchSpeedPowerMax;
		IAmmo ammo = vehicle.ammoHelper.getCurrentAmmoType();
		if (ammo != null) {
			float missileWeight = ammo.getAmmoWeight();
			float maxWeight = vehicle.vehicleType.getMaxMissileWeight();
			if (missileWeight > maxWeight) {
				float totalWeight = missileWeight + maxWeight;
				float temp = maxWeight / totalWeight;
				temp *= 2;
				velocity *= temp;
			}
		}
		//  Config.logDebug("adj velocity: "+velocity);
		return velocity;
	}

	/**
	 * get accuracy after adjusting for rider (soldier)
	 */
	private float getAccuracyAdjusted() {
		return vehicle.currentAccuracy;
	}

	/**
	 * if not already firing, this will initiate the launch sequence (phase 1 of 3).
	 * Called by this to start missileLaunch. (triggered from packet)
	 */
	private void initiateLaunchSequence() {
		if (!this.isFiring && !this.isLaunching && this.reloadingTicks <= 0) {
			this.isFiring = true;
			this.isLaunching = false;
			this.isReloading = false;
			vehicle.vehicleType.playFiringSound(vehicle);
		}
	}

	/**
	 * setReloading to finished. private for a reason... (return to phase 0)
	 */
	private void setFinishedReloading() {
		this.isFiring = false;
		this.isReloading = false;
		this.isLaunching = false;
		this.reloadingTicks = 0;
	}

	/**
	 * initiate actual launching of missiles (phase 2 of 3)
	 */
	public void startLaunching() {
		this.isFiring = false;
		this.isLaunching = true;
		this.isReloading = false;
	}

	/**
	 * finish the launching sequence, and begin reloading (phase 3 of 3)
	 */
	public void setFinishedLaunching() {
		this.isFiring = false;
		this.isReloading = true;
		this.isLaunching = false;
		this.reloadingTicks = vehicle.currentReloadTicks;
		vehicle.vehicleType.playReloadSound(vehicle);
	}

	public void handleFireUpdate() {
		if (reloadingTicks <= 0 || vehicle.worldObj.isRemote) {

			boolean shouldFire = vehicle.ammoHelper.getCurrentAmmoCount() > 0 || vehicle.ammoHelper.doesntUseAmmo();
			if (shouldFire) {

				if (!vehicle.worldObj.isRemote) {
					NetworkHandler.sendToAllTracking(vehicle, new PacketFireUpdate(vehicle));
				}
				this.initiateLaunchSequence();
			}
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public void updateAim(Optional<Float> pitch, Optional<Float> yaw, Optional<Float> power) {
		boolean sendReply = false;
		if (pitch.isPresent()) {
			sendReply = true;
			vehicle.localTurretDestPitch = pitch.get();
		}
		if (yaw.isPresent()) {
			sendReply = true;
			vehicle.localTurretDestRot = yaw.get();
		}
		if (power.isPresent()) {
			sendReply = true;
			vehicle.localLaunchPower = power.get();
		}
		if (!vehicle.worldObj.isRemote && sendReply) {
			NetworkHandler.sendToAllTracking(vehicle, new PacketAimUpdate(vehicle, pitch, yaw, power));
		}
	}

	public void handleFireInput() {
		if (isReadyToFire()) {
			NetworkHandler.sendToServer(new PacketFireUpdate(vehicle));
		}
	}

	public boolean isReadyToFire() {
		return !isFiring && !isLaunching && !isReloading && hasAmmo();
	}

	private boolean hasAmmo() {
		return vehicle.ammoHelper.doesntUseAmmo() || (vehicle.ammoHelper.getCurrentAmmoCount() > 0 && vehicle.ammoHelper.getCurrentAmmoType() != null);
	}

	public void handleAimKeyInput(float pitch, float yaw) {
		boolean pitchUpdated = false;
		boolean powerUpdated = false;
		boolean yawUpdated = false;
		if (vehicle.canAimPitch()) {
			float pitchTest = this.clientTurretPitch + pitch;
			if (pitchTest < vehicle.currentTurretPitchMin) {
				pitchTest = vehicle.currentTurretPitchMin;
			} else if (pitchTest > vehicle.currentTurretPitchMax) {
				pitchTest = vehicle.currentTurretPitchMax;
			}
			if (!(Math.abs(pitchTest - this.clientTurretPitch) > 1e-9)) {
				pitchUpdated = true;
				this.clientTurretPitch = pitchTest;
			}
		} else if (vehicle.canAimPower()) {
			float powerTest = clientLaunchSpeed + pitch;
			if (powerTest < 0) {
				powerTest = 0;
			} else if (powerTest > getAdjustedMaxMissileVelocity()) {
				powerTest = getAdjustedMaxMissileVelocity();
			}
			if (!(Math.abs(clientLaunchSpeed - powerTest) > 1e-5f)) {
				powerUpdated = true;
				this.clientLaunchSpeed = powerTest;
			}
		}
		if (vehicle.canAimRotate()) {
			yawUpdated = true;
			this.clientTurretYaw += yaw;
		}

		if (powerUpdated || pitchUpdated || yawUpdated) {
			Optional<Float> turretPitch = pitchUpdated ? Optional.of(clientTurretPitch) : Optional.empty();
			Optional<Float> turretYaw = yawUpdated ? Optional.of(clientTurretYaw) : Optional.empty();
			Optional<Float> power = powerUpdated ? Optional.of(clientLaunchSpeed) : Optional.empty();

			NetworkHandler.sendToServer(new PacketAimUpdate(vehicle, turretPitch, turretYaw, power));
		}
	}

	/**
	 * CLIENT SIDE--used client side to update client desired pitch and yaw and send these to server/other clients...
	 */
	public void handleAimInput(Vec3 target) {
		boolean updated = false;
		boolean updatePitch = false;
		boolean updatePower = false;
		boolean updateYaw = false;
		Vector3d offset = vehicle.getMissileOffset();
		float x = (float) (vehicle.posX + offset.x);
		float y = (float) (vehicle.posY + offset.y);
		float z = (float) (vehicle.posZ + offset.z);
		float tx = (float) (target.xCoord - x);
		float ty = (float) (target.yCoord - y);
		float tz = (float) (target.zCoord - z);
		float range = MathHelper.sqrt_float(tx * tx + tz * tz);
		if (vehicle.canAimPitch()) {
			Tuple angles = getLaunchAngleToHit(tx, ty, tz, vehicle.localLaunchPower);
			float ang1 = (float) angles.getFirst();
			float ang2 = (float) angles.getSecond();
			if (Float.isNaN(ang1) || Float.isNaN(ang2)) {
			} else if (Trig.isAngleBetween((float) angles.getSecond(), vehicle.currentTurretPitchMin, vehicle.currentTurretPitchMax)) {
				if ((Math.abs(this.clientTurretPitch - (float)angles.getSecond()) > 1e-5f)) {
					this.clientTurretPitch = (float) angles.getSecond();
					updated = true;
					updatePitch = true;
				}
			} else if (Trig.isAngleBetween((float)angles.getFirst(), vehicle.currentTurretPitchMin, vehicle.currentTurretPitchMax)) {

				if ((Math.abs(this.clientTurretPitch - (float)angles.getFirst()) > 1e-5f)) {
					this.clientTurretPitch = (float)angles.getFirst();
					updated = true;
					updatePitch = true;
				}
			}
		} else if (vehicle.canAimPower()) {
			float power = iterativeSpeedFinder(tx, ty, tz, vehicle.localTurretPitch + vehicle.rotationPitch, TRAJECTORY_ITERATIONS_CLIENT,
					(vehicle.ammoHelper.getCurrentAmmoType() != null && vehicle.ammoHelper.getCurrentAmmoType().isRocket()));
			if (!epsilonEquals(clientLaunchSpeed, power) && power < getAdjustedMaxMissileVelocity()) {
				this.clientLaunchSpeed = power;
				updated = true;
				updatePower = true;
			}
		}
		if (vehicle.canAimRotate()) {
			float yaw = getAimYaw(target.xCoord, target.zCoord);
			if (!epsilonEquals(yaw, clientTurretYaw) && (vehicle.currentTurretRotationMax >= 180 || Trig
					.isAngleBetween(yaw, vehicle.localTurretRotationHome - vehicle.currentTurretRotationMax,
							vehicle.localTurretRotationHome + vehicle.currentTurretRotationMax))) {
				if (Math.abs(yaw - clientTurretYaw) > 0.25f) {
					this.clientTurretYaw = yaw;
					updated = true;
					updateYaw = true;
				}
			}
		}


		if (updated) {
			this.clientHitRange = range;
			Optional<Float> turretPitch = updatePitch ? Optional.of(clientTurretPitch) : Optional.empty();
			Optional<Float> turretYaw = updateYaw ? Optional.of(clientTurretYaw) : Optional.empty();
			Optional<Float> power = updatePower ? Optional.of(clientLaunchSpeed) : Optional.empty();
			NetworkHandler.sendToServer(new PacketAimUpdate(vehicle, turretPitch, turretYaw, power));
		}
	}

	public boolean isAtTarget() {
		return isAtTarget(0.35f);
	}

	private static boolean epsilonEquals(float a, float b) {
		final float EPSILON = 1e-5f;
		return Math.abs(a - b) < EPSILON;
	}

	private boolean isAtTarget(float range) {
		float yaw = Trig.wrapTo360(vehicle.localTurretRotation);
		float dest = Trig.wrapTo360(vehicle.localTurretDestRot);

		return epsilonEquals(vehicle.localTurretDestPitch, vehicle.localTurretPitch) && Math.abs(yaw - dest) < range;
	}

	public boolean isNearTarget() {
		return isAtTarget(5f);
	}

	public void handleSoldierTargetInput(double targetX, double targetY, double targetZ) throws IOException {
		boolean updated = false;
		boolean updatePitch = false;
		boolean updatePower = false;
		boolean updateYaw = false;
		Vector3d offset = vehicle.getMissileOffset();
		float x = (float) (vehicle.posX + offset.x);
		float y = (float) (vehicle.posY + offset.y);
		float z = (float) (vehicle.posZ + offset.z);
		float tx = (float) (targetX - x);
		float ty = (float) (targetY - y);
		float tz = (float) (targetZ - z);
		if (vehicle.canAimPitch()) {
			Tuple angles = getLaunchAngleToHit(tx, ty, tz, vehicle.localLaunchPower);
			float ang1 = (float) angles.getFirst();
			float ang2 = (float) angles.getSecond();
			if (Float.isNaN(ang1) || Float.isNaN(ang2)) {
			} else if (Trig.isAngleBetween((float) angles.getSecond(), vehicle.currentTurretPitchMin, vehicle.currentTurretPitchMax)) {
				if (!epsilonEquals(vehicle.localTurretDestPitch, (float) angles.getSecond())) {
					vehicle.localTurretDestPitch = (float) angles.getSecond();
					updated = true;
					updatePitch = true;
				}
			} else if (Trig.isAngleBetween((float) angles.getFirst(), vehicle.currentTurretPitchMin, vehicle.currentTurretPitchMax)) {
				if (!epsilonEquals(vehicle.localTurretDestPitch, (float) angles.getFirst())) {
					vehicle.localTurretDestPitch = (float) angles.getFirst();
					updated = true;
					updatePitch = true;
				}
			}
		} else if (vehicle.canAimPower()) {
			float power = iterativeSpeedFinder(tx, ty, tz, vehicle.localTurretPitch + vehicle.rotationPitch, TRAJECTORY_ITERATIONS_CLIENT,
					(vehicle.ammoHelper.getCurrentAmmoType() != null && vehicle.ammoHelper.getCurrentAmmoType().isRocket()));
			if (!epsilonEquals(vehicle.localLaunchPower, power) && power < getAdjustedMaxMissileVelocity()) {
				this.vehicle.localLaunchPower = power;
				updated = true;
				updatePower = true;
			}
		}
		if (vehicle.canAimRotate()) {
			float yaw = getAimYaw(targetX, targetZ);
			if (!epsilonEquals(yaw, vehicle.localTurretDestRot) && (vehicle.currentTurretRotationMax >= 180 || Trig
					.isAngleBetween(yaw, vehicle.localTurretRotationHome - vehicle.currentTurretRotationMax,
							vehicle.localTurretRotationHome + vehicle.currentTurretRotationMax))) {
				this.vehicle.localTurretDestRot = yaw;
				updated = true;
				updateYaw = true;
			}
		}
		if (updated && !vehicle.worldObj.isRemote) {
			Optional<Float> turretPitch = updatePitch ? Optional.of(vehicle.localTurretDestPitch) : Optional.empty();
			Optional<Float> turretYaw = updateYaw ? Optional.of(vehicle.localTurretDestRot) : Optional.empty();
			Optional<Float> power = updatePower ? Optional.of(vehicle.localLaunchPower) : Optional.empty();
			NetworkHandler.sendToAllTracking(vehicle, new PacketAimUpdate(vehicle, turretPitch, turretYaw, power));
		}
	}

	private float getAimYaw(double targetX, double targetZ) {
		Vector3d offset = vehicle.getMissileOffset();
		float vecX = (float) (vehicle.posX + (vehicle.canTurretTurn() ? offset.x : 0) - targetX);
		float vecZ = (float) (vehicle.posZ + (vehicle.canTurretTurn() ? offset.z : 0) - targetZ);
		//noinspection SuspiciousNameCombination
		return Trig.wrapTo360(Trig.toDegrees((float) Math.atan2(vecX, vecZ)));
	}

	private static final float NEGLIGIBLE_ANGLE_DIFFERENCE = 0.35f;

	public boolean isAimedAt(ITarget target) {
		return isPitchPointedAt(target) && isYawPointedAt(target) && isPowerSetToPointAt(target);
	}

	private boolean isYawPointedAt(ITarget target) {
		float minYaw = getAimYaw(target.getBoundingBox().minX, target.getBoundingBox().minZ);
		float maxYaw = getAimYaw(target.getBoundingBox().maxX, target.getBoundingBox().maxZ);

		if (minYaw > maxYaw) {
			float temp = minYaw;
			minYaw = maxYaw;
			maxYaw = temp;
		}

		float vehicleRotation = vehicle.canAimRotate() ? vehicle.localTurretRotation : vehicle.rotationYaw;

		return Trig.isAngleBetween(vehicleRotation, minYaw, maxYaw);
	}

	private boolean isPitchPointedAt(ITarget target) {
		if (!vehicle.canAimPitch()) {
			return true;
		}

		Vector3d offset = vehicle.getMissileOffset();
		float targetX = (float) target.getBoundingBox().minX - (float) (vehicle.posX + offset.x);
		float targetY = (float) target.getBoundingBox().minY - (float) (vehicle.posY + offset.y);
		float targetZ = (float) target.getBoundingBox().minZ - (float) (vehicle.posZ + offset.z);

		Tuple anglesMin = getLaunchAngleToHit(targetX, targetY, targetZ, vehicle.localLaunchPower);
		double angle1 = (Double) anglesMin.getFirst();
		double angle2 = (Double) anglesMin.getSecond();
		double turretPitch = vehicle.localTurretPitch;
		//noinspection SimplifiableIfStatement
		if (Math.abs(angle2 - turretPitch) < NEGLIGIBLE_ANGLE_DIFFERENCE || Math
				.abs(angle1 - turretPitch) < NEGLIGIBLE_ANGLE_DIFFERENCE) {
			return true;
		}

		targetX = (float) target.getBoundingBox().maxX - (float) (vehicle.posX + offset.x);
		targetY = (float) target.getBoundingBox().maxY - (float) (vehicle.posY + offset.y);
		targetZ = (float) target.getBoundingBox().maxZ - (float) (vehicle.posZ + offset.z);

		Tuple anglesMax = getLaunchAngleToHit(targetX, targetY, targetZ, vehicle.localLaunchPower);
		angle1 = (Double) anglesMax.getFirst(); angle2 = (Double) anglesMax.getSecond();
		//noinspection SimplifiableIfStatement
		if (Math.abs(angle2 - turretPitch) < NEGLIGIBLE_ANGLE_DIFFERENCE || Math
				.abs(angle1 - turretPitch) < NEGLIGIBLE_ANGLE_DIFFERENCE) {
			return true;
		}
            double angleMin1 = (Double) anglesMin.getFirst();
            double angleMin2 = (Double) anglesMin.getSecond();
            double angleMax1 = (Double) anglesMax.getFirst();
            double angleMax2 = (Double) anglesMax.getSecond();
            return Trig.isAngleBetween((float)turretPitch, (float)angleMin1, (float)angleMax1) ||
                    Trig.isAngleBetween((float)turretPitch, (float)angleMin2, (float)angleMax2);

    }

	private boolean isPowerSetToPointAt(ITarget target) {
		if (!vehicle.canAimPower()) {
			return true;
		}
		Vector3d offset = vehicle.getMissileOffset();
		float x = (float) (vehicle.posX + offset.x);
		float y = (float) (vehicle.posY + offset.y);
		float z = (float) (vehicle.posZ + offset.z);

		float minDistY;
		float maxDistY;
		if (Math.abs(target.getBoundingBox().minY - y) < Math.abs(target.getBoundingBox().maxY - y)) {
			minDistY = (float) (target.getBoundingBox().minY - y);
			maxDistY = (float) (target.getBoundingBox().maxY - y);
		} else {
			minDistY = (float) (target.getBoundingBox().maxY - y);
			maxDistY = (float) (target.getBoundingBox().minY - y);
		}

		float powerMin = getPowerFor((float) target.getBoundingBox().minX - x, minDistY, (float) target.getBoundingBox().minZ - z);
		float powerMax = getPowerFor((float) target.getBoundingBox().maxX - x, maxDistY, (float) target.getBoundingBox().maxZ - z);

		if (powerMin > powerMax) {
			float temp = powerMin;
			powerMin = powerMax;
			powerMax = temp;
		}

		return vehicle.localLaunchPower >= powerMin && vehicle.localLaunchPower <= powerMax;
	}

	public static Tuple getLaunchAngleToHit(double targetX, double targetY, double targetZ, double launchPower) {
		double gravity = 9.81d; // Adjust if your game uses different gravity
		double dx = targetX; // Horizontal distance
		double dy = targetY; // Vertical distance
		double dz = targetZ; // Depth distance

		double distance = Math.sqrt(dx * dx + dz * dz);
		double velocitySquared = launchPower * launchPower;

		double angle1, angle2;

		// Solving for launch angles
		double discriminant = velocitySquared * velocitySquared - gravity * (gravity * distance * distance + 2 * dy * velocitySquared);
		if (discriminant < 0) {
			return null; // No solution, target is out of range
		}

		angle1 = Math.toDegrees(Math.atan((velocitySquared - Math.sqrt(discriminant)) / (gravity * distance)));
		angle2 = Math.toDegrees(Math.atan((velocitySquared + Math.sqrt(discriminant)) / (gravity * distance)));

		return new Tuple(angle1, angle2);
	}



	private float getPowerFor(float distX, float distY, float distZ) {
		float pitch = vehicle.localTurretPitch + vehicle.rotationPitch;
		boolean isRocket = false;

		if (vehicle.ammoHelper.getCurrentAmmoType() != null) {
			Object ammoType = vehicle.ammoHelper.getCurrentAmmoType();
			if (ammoType instanceof Ammo) {
				isRocket = ((AmmoHwachaRocket) ammoType).isRocket();
			}
		}

		return iterativeSpeedFinder(distX, distY, distZ, pitch, TRAJECTORY_ITERATIONS_CLIENT, isRocket);
	}

	public static float iterativeSpeedFinder(float distX, float distY, float distZ, float pitch, int iterations, boolean isRocket) {
		final float gravity = 9.81f * 0.05f; // Gravity in the game world, scaled
		float targetDistance = (float) Math.sqrt(distX * distX + distZ * distZ); // Horizontal distance
		float speed = 1.0f; // Initial guess for speed

		for (int i = 0; i < iterations; i++) {
			float time = targetDistance / (speed * (float) Math.cos(Math.toRadians(pitch))); // Time to hit horizontal target
			float predictedY = (speed * (float) Math.sin(Math.toRadians(pitch)) * time) - (0.5f * gravity * time * time); // Vertical displacement

			if (Math.abs(predictedY - distY) < 0.1f) { // Close enough
				break;
			}

			if (predictedY < distY) {
				speed += 0.1f;
			} else {
				speed -= 0.1f;
			}
		}
		return speed;
	}



	public float getAimYaw(ITarget target) {
		return getAimYaw(target.getX(), target.getZ());
	}

	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setInteger("rt", reloadingTicks);
		tag.setBoolean("f", this.isFiring);
		tag.setBoolean("r", this.isReloading);
		tag.setBoolean("l", this.isLaunching);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.reloadingTicks = tag.getInteger("rt");
		this.isFiring = tag.getBoolean("f");
		this.isReloading = tag.getBoolean("r");
		this.isLaunching = tag.getBoolean("l");
	}

	@Override
	public void init(Entity entity, World world) {

	}
}
