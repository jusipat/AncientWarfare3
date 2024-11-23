package xyz.dylanlogan.ancientwarfare.vehicle.helpers;

import com.google.common.collect.Lists;
import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.IPlantable;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleMovementType;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketVehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketVehicleMove;

import java.io.IOException;
import java.util.List;

public class VehicleMoveHelper implements IExtendedEntityProperties {

	private static final int VEHICLE_MOVE_UPDATE_FREQUENCY = 3;

	byte forwardInput;
	byte turnInput;
	byte powerInput;
	byte rotationInput;

	double destX;
	double destY;
	double destZ;
	float destYaw;
	float destPitch;

	int moveTicks = 0;
	int rotationTicks = 0;
	int pitchTicks = 0;

	public float forwardMotion = 0.f;
	protected float verticalMotion = 0.f;
	protected float turnMotion = 0.f;
	protected float pitchMotion = 0.f;
	protected float strafeMotion = 0.f;
	public float throttle = 0.f;

	protected float groundDrag = 0.96f;
	protected float groundStop = 0.02f;
	protected float rotationDrag = 0.94f;
	protected float rotationStop = 0.2f;

	protected float rotationSpeed = 0.f;
	protected float pitchSpeed = 0.f;

	protected boolean wasOnGround = true;

	protected VehicleBase vehicle;

	public VehicleMoveHelper(VehicleBase vehicle) {
		this.vehicle = vehicle;
	}

	public float getRotationSpeed() {
		return this.rotationSpeed;
	}

	//TODO refactor this set inputs to something like forwardStop, left, right, rotationStop, ... so that clients don't need to pass in values
	public void setForwardInput(byte in) {
		this.forwardInput = in;
	}

	public void setStrafeInput(byte in) {
		this.turnInput = in;
	}

	public void updateMoveData(double posX, double posY, double posZ, boolean air, float motion, float yaw, float pitch) {
		this.pitchTicks = VEHICLE_MOVE_UPDATE_FREQUENCY + 1;
		this.rotationTicks = VEHICLE_MOVE_UPDATE_FREQUENCY + 1;
		this.moveTicks = VEHICLE_MOVE_UPDATE_FREQUENCY + 1;

		this.destPitch = pitch;
		this.destYaw = yaw;
		this.destX = posX;
		this.destY = posY;
		this.destZ = posZ;
		if (air) {
			this.throttle = motion;
		} else {
			this.forwardMotion = motion;
		}
	}

	public void handleInputData(byte forwardInput, byte turnInput, byte powerInput, byte rotationInput) {
		this.forwardInput = forwardInput;
		this.turnInput = turnInput;
		this.powerInput = powerInput;
		this.rotationInput = rotationInput;
	}

	public void onUpdate() {
		if (vehicle.worldObj.isRemote) {
			onUpdateClient();
		} else {
			if (this.vehicle.ridingEntity == null) {
				this.clearInputFromDismount();
			}
			onUpdateServer();
			this.vehicle.nav.onMovementUpdate();
		}
	}

	protected void onUpdateClient() {
		vehicle.noClip = true;
		vehicle.motionX = 0;
		vehicle.motionY = 0;
		vehicle.motionZ = 0;
		rotationSpeed = 0;
		pitchSpeed = 0;
		if (this.rotationTicks > 0) {
			rotationSpeed = (this.destYaw - vehicle.rotationYaw) / rotationTicks;
			vehicle.rotationYaw += this.rotationSpeed;
			this.rotationTicks--;
		}
		if (this.pitchTicks > 0) {
			pitchSpeed = (this.destPitch - vehicle.rotationPitch) / pitchTicks;
			vehicle.rotationPitch += this.pitchSpeed;
			this.pitchTicks--;
		}
		if (moveTicks > 0) {
			vehicle.motionX = destX - vehicle.posX;
			vehicle.motionY = destY - vehicle.posY;
			vehicle.motionZ = destZ - vehicle.posZ;
			moveTicks--;
		}
		vehicle.wheelRotationPrev = vehicle.wheelRotation;
		if (vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR1 || vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR2) {
			vehicle.wheelRotation += throttle;
		} else if (vehicle.vehicleType.getMovementType() == VehicleMovementType.WATER || vehicle.vehicleType.getMovementType() == VehicleMovementType.WATER2) {
			vehicle.wheelRotation += forwardMotion * 64;
		} else {
			vehicle.wheelRotation += forwardMotion * 60;
		}
		//this.vehicle.move(MoverType.SELF, vehicle.motionX, vehicle.motionY, vehicle.motionZ);
	}

	protected void onUpdateServer() {
		VehicleMovementType move = vehicle.vehicleType.getMovementType();
		switch (move) {
			case GROUND:
				this.applyGroundMotion();
				break;

			case WATER:
				this.applyWaterMotion();
				break;
		}
		if (move == VehicleMovementType.AIR1 || move == VehicleMovementType.AIR2) {
			vehicle.fallDistance = 0.f;
			if (vehicle.ridingEntity != null) {
				vehicle.ridingEntity.fallDistance = 0.f;
			}
		}
		vehicle.motionX = Trig.sinDegrees(vehicle.rotationYaw) * -forwardMotion;
		vehicle.motionZ = Trig.cosDegrees(vehicle.rotationYaw) * -forwardMotion;
		//this.vehicle(MoverType.SELF, vehicle.motionX, vehicle.motionY, vehicle.motionZ);
		this.wasOnGround = vehicle.onGround;
		if (vehicle.isCollidedHorizontally) {
			forwardMotion *= 0.65f;
		}
		this.tearUpGrass();
		boolean sendUpdate = (vehicle.motionX != 0 || vehicle.motionY != 0 || vehicle.motionZ != 0 || vehicle.rotationYaw != vehicle.prevRotationYaw || vehicle.rotationPitch != vehicle.prevRotationPitch);
		sendUpdate = sendUpdate || vehicle.ridingEntity != null;
		sendUpdate = sendUpdate || this.vehicle.ticksExisted % 60 == 0;
		if (sendUpdate) {
			boolean air = move == VehicleMovementType.AIR1 || move == VehicleMovementType.AIR2;
			float motion = air ? throttle : forwardMotion;
			PacketVehicleBase pkt = new PacketVehicleMove(vehicle, vehicle.posX, vehicle.posY, vehicle.posZ, air, motion, vehicle.rotationYaw, vehicle.rotationPitch);
			NetworkHandler.sendToAllTracking(vehicle, pkt);
		}
	}

	protected void applyGroundMotion() {
		this.applyTurnInput(0.05f);
		this.applyForwardInput(0.0125f, true);
		vehicle.motionY -= 9.81 * 0.05f * 0.05f;
	}

	protected void applyWaterMotion() {
		this.applyTurnInput(0.05f);
		if (this.handleBoatBob(true) < 0) {
			this.forwardMotion *= 0.85f;
			this.strafeMotion *= 0.85f;
		}
		this.applyForwardInput(0.0125f, false);
	}

	protected void applyForwardInput(float inputFactor, boolean slowReverse) {
		if (vehicle.currentForwardSpeedMax <= 0.f) {
			forwardMotion = 0.f;
			return;
		}
		float weightAdjust = 1.f;
		if (vehicle.currentWeight > vehicle.baseWeight) {
			weightAdjust = vehicle.baseWeight / vehicle.currentWeight;
		}
		if (forwardInput != 0) {
			boolean reverse = (forwardInput == -1 && forwardMotion > 0) || (forwardInput == 1 && forwardMotion < 0);
			float maxSpeed = vehicle.currentForwardSpeedMax * weightAdjust;
			float maxReverse = -maxSpeed * (slowReverse ? 0.6f : 1.f);
			float percent = 1 - (forwardMotion >= 0 ? (forwardMotion / maxSpeed) : (forwardMotion / maxReverse));
			percent = percent > 0.25f ? 0.25f : percent;
			percent = reverse ? 0.25f : percent;
			float changeFactor = percent * forwardInput * inputFactor;
			if (reverse) {
				changeFactor *= 2;
			}
			forwardMotion += changeFactor;
			if (forwardMotion > maxSpeed) {
				forwardMotion = maxSpeed;
			}
			if (forwardMotion < maxReverse) {
				forwardMotion = maxReverse;
			}
		} else {
			forwardMotion *= groundDrag;
		}
		if (Math.abs(forwardMotion) < groundStop && forwardInput == 0) {
			forwardMotion = 0.f;
		}
	}

	protected void applyTurnInput(float inputFactor) {
		float weightAdjust = 1.f;
		if (vehicle.currentWeight > vehicle.baseWeight) {
			weightAdjust = vehicle.baseWeight / vehicle.currentWeight;
		}
		if (turnInput != 0) {
			boolean reverse = (turnInput == -1 && turnMotion > 0) || (turnInput == 1 && turnMotion < 0);
			float maxSpeed = vehicle.currentStrafeSpeedMax * weightAdjust;
			float percent = 1 - (Math.abs(turnMotion) / maxSpeed);
			percent = reverse ? 1.f : percent;
			float changeFactor = percent * (turnInput * 2) * inputFactor;
			if (reverse) {
				changeFactor *= 2;
			}
			turnMotion += changeFactor;
			if (turnMotion > maxSpeed) {
				turnMotion = maxSpeed;
			}
			if (turnMotion < -maxSpeed) {
				turnMotion = -maxSpeed;
			}
		} else {
			turnMotion *= rotationDrag;
		}
		if (Math.abs(turnMotion) < rotationStop && turnInput == 0) {
			turnMotion = 0.f;
		}
		this.vehicle.rotationYaw -= this.turnMotion;
	}

	/**
	 * code to set Y motion on a surface or subsurface water vehicle
	 *
	 * @param floats if the vehicle should return to the surface if it is underwater
	 */
	protected int handleBoatBob(boolean floats) {
		float bitHeight = vehicle.height * 0.2f;
		AxisAlignedBB bb;
		int submergedBits = 0;
		for (int i = 0; i < 5; i++) {
			bb = AxisAlignedBB.getBoundingBox(vehicle.getBoundingBox().minX, vehicle.getBoundingBox().minY + (i * bitHeight), vehicle.getBoundingBox().minZ, vehicle.getBoundingBox().maxX, vehicle.getBoundingBox().minY + ((1 + i) * bitHeight), vehicle.getBoundingBox().maxZ);
			if (vehicle.worldObj.isMaterialInBB(bb, Material.water)) {
				submergedBits++;
			} else {
				break;
			}
		}
		submergedBits -= 2;
		if (!floats && submergedBits > 0) {
			submergedBits = 0;
		}
		if (submergedBits < 0) {
			vehicle.motionY -= 9.81f * 0.05f * 0.05f;
		} else {
			vehicle.motionY += (float) submergedBits * 0.02f;
			vehicle.motionY *= 0.8f;
		}
		return submergedBits;
	}

	protected void tearUpGrass() {
		if (vehicle.worldObj.isRemote || !vehicle.onGround || !AWVehicleStatics.generalSettings.vehiclesTearUpGrass) {
			return;
		}
		for (int var24 = 0; var24 < 4; ++var24) {
			int x = MathHelper.floor_double(vehicle.posX + ((double) (var24 % 2) - 0.5D) * 0.8D);
			int y = MathHelper.floor_double(vehicle.posY);
			int z = MathHelper.floor_double(vehicle.posZ + ((double) (var24 / 2) - 0.5D) * 0.8D);
			//check top/upper blocks(riding through)
			BlockPos breakPos = new BlockPos(x, y, z);
			int state = vehicle.worldObj.getBlockMetadata(breakPos.x, breakPos.y, breakPos.z);
//			if (isTrampable(state)) {
//				BlockTools.breakBlockAndDrop(vehicle.worldObj, null, breakPos.getX(), breakPos.getY(), breakPos.getZ());
//			}
			//check lower blocks (riding on)
//			if (vehicle.worldObj.getBlockState(breakPos.down()).getBlock() == Blocks.grass) {
//				vehicle.worldObj.setBlockState(breakPos.down(), Blocks.dirt, 3);
//			}
		}
	}

	private boolean isTrampable(World world, int x, int y, int z) {
		return world.getBlock(x,y,z) instanceof IPlantable || trampableBlocks.contains(world.getBlock(x,y,z));
	}

	protected static List<Block> trampableBlocks = Lists.newArrayList(Blocks.snow, Blocks.deadbush, Blocks.tallgrass, Blocks.red_flower, Blocks.yellow_flower, Blocks.brown_mushroom, Blocks.red_mushroom);

	public void setMoveTo(double x, double y, double z) {
		float yawDiff = Trig.getYawTowardsTarget(vehicle.posX, vehicle.posZ, x, z, vehicle.rotationYaw);
		byte fMot = 0;
		byte sMot = 0;
		if (Math.abs(yawDiff) > 5)//more than 5 degrees off, correct yaw first, then move forwards
		{
			if (yawDiff < 0) {
				sMot = 1;//left
			} else {
				sMot = -1;//right
			}
		}
		if (Math.abs(yawDiff) < 10 && Trig.getVelocity(x - vehicle.posX, y - vehicle.posY, z - vehicle.posZ) >= 0.25f)//further away than 1 block, move towards it
		{
			fMot = 1;
		}
		this.forwardInput = fMot;
		this.turnInput = sMot;
	}

	public void stopMotion() {
		this.clearInputFromDismount();
		vehicle.motionX = 0;
		vehicle.motionY = 0;
		vehicle.motionZ = 0;
		turnMotion = 0;
	}

	public void clearInputFromDismount() {
		this.forwardInput = 0;
		this.turnInput = 0;
		this.powerInput = 0;
		this.rotationInput = 0;
		this.throttle = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setByte("fi", forwardInput);
		tag.setByte("si", turnInput);
		tag.setByte("pi", powerInput);
		tag.setByte("ri", rotationInput);
		tag.setFloat("tr", throttle);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.forwardInput = tag.getByte("fi");
		this.turnInput = tag.getByte("si");
		this.powerInput = tag.getByte("pi");
		this.rotationInput = tag.getByte("ri");
		this.throttle = tag.getFloat("tr");
	}

	@Override
	public void init(Entity entity, World world) {

	}
}
