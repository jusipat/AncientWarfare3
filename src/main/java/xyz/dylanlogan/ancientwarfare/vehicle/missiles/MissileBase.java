package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.IMissileHitCallback;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;

import java.util.Iterator;
import java.util.List;

public class MissileBase extends Entity implements IEntityAdditionalSpawnData {

	/**
	 * Must be set after missile is constructed, but before spawned server side.  Client-side this will be set by the readSpawnData method.  This ammo type is responsible for many onTick qualities,
	 * effects of impact, and model/render instance used.
	 */
	public IAmmo ammoType = AmmoRegistry.ammoArrow;
	public Entity launcher = null;
	public Entity shooterLiving;
	IMissileHitCallback shooter = null;
	private int rocketBurnTime = 0;

	private boolean inGround = false;
	private boolean hasImpacted = false;
	private ChunkCoordinates persistentBlockPos = new ChunkCoordinates(0, 0, 0);
	//private BlockPos persistentBlockPos = BlockPos.ORIGIN;
	private int persistentBlock; // block metadata (old BlockState)

	/**
	 * initial velocities, used by rocket for acceleration factor
	 */
	private float mX;
	private float mY;
	private float mZ;

	public MissileBase(World par1World) {
		super(par1World);
		this.entityCollisionReduction = 1.f;
		this.setSize(0.4f, 0.4f);
	}

	private void setMissileParams(IAmmo type, float x, float y, float z, float mx, float my, float mz) {
		this.ammoType = type;
		this.setPosition(x, y, z);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;
		this.mX = mx;
		this.mY = my;
		this.mZ = mz;
		if (this.ammoType.updateAsArrow()) {
			this.onUpdateArrowRotation();
		}
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		if (this.ammoType.isRocket())//use launch power to determine rocket burn time...
		{
			float temp = MathHelper.sqrt_float(mx * mx + my * my + mz * mz);
			this.rocketBurnTime = (int) (temp * 20.f * AmmoHwachaRocket.BURN_TIME_FACTOR);

			this.mX = (float) (motionX / temp) * AmmoHwachaRocket.ACCELERATION_FACTOR;
			this.mY = (float) (motionY / temp) * AmmoHwachaRocket.ACCELERATION_FACTOR;
			this.mZ = (float) (motionZ / temp) * AmmoHwachaRocket.ACCELERATION_FACTOR;
			this.motionX = mX;
			this.motionY = mY;
			this.motionZ = mZ;
		}
	}

	public void setMissileParams2(IAmmo ammo, float x, float y, float z, float yaw, float angle, float velocity) {
		float vX = -Trig.sinDegrees(yaw) * Trig.cosDegrees(angle) * velocity * 0.05f;
		float vY = Trig.sinDegrees(angle) * velocity * 0.05f;
		float vZ = -Trig.cosDegrees(yaw) * Trig.cosDegrees(angle) * velocity * 0.05f;
		this.setMissileParams(ammo, x, y, z, vX, vY, vZ);
	}

	public void setShooter(Entity shooter) {
		this.shooterLiving = shooter;
	}

	public void setLaunchingEntity(Entity ent) {
		this.launcher = ent;
	}

	public void setMissileCallback(IMissileHitCallback shooter) {
		this.shooter = shooter;
	}

	public void onImpactEntity(Entity ent, float x, float y, float z) {
		if (Ammo.shouldEffectEntity(worldObj, ent, this)) {
			this.ammoType.onImpactEntity(worldObj, ent, x, y, z, this);
			if (this.shooter != null) {
				this.shooter.onMissileImpactEntity(worldObj, ent);
			}
		}
	}

	public void onImpactWorld(MovingObjectPosition hit) {
		this.ammoType.onImpactWorld(worldObj, hit.blockX, hit.blockY, hit.blockZ, this, hit);
		if (this.shooter != null) {
			this.shooter.onMissileImpact(worldObj, hit.blockX, hit.blockY, hit.blockZ);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderOnFire() {
		return this.ammoType.isFlaming();
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void applyEntityCollision(Entity par1Entity) {
		//NOOP
	}

	@Override
	public void onUpdate() {
		this.ticksExisted++;
		super.onUpdate();
		this.onMovementTick();
		if (!this.worldObj.isRemote) {
			if (this.ticksExisted > 6000)//5 min timer max for missiles...
			{
				this.setDead();
			}
		}
	}

	private void checkProximity() {
		if (this.motionY > 0) {
			return;//don't bother checking when travelling upwards, wait until the downward swing...
		}
		//check ground.
		int groundDiff = 0;
		int x = (int) posX;
		int y = (int) posY;
		int z = (int) posZ;
		boolean impacted = false;
		if (ammoType.groundProximity() > 0) {
			while (groundDiff <= ammoType.groundProximity()) {
				groundDiff++;
				if (!worldObj.isAirBlock(x, y - groundDiff, z)) {
					this.onImpactWorld(new MovingObjectPosition(x, y, z, 0, Vec3.createVectorHelper(x, y, z))); //TODO correct MovingObjectPosition created? Test
					impacted = true;
					break;
				}
			}
		}
		//check entities if not detonated by ground
		if (!impacted && ammoType.entityProximity() > 0) {
			float entProx = ammoType.entityProximity();
			float foundDist = 0;
			List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - entProx, posY - entProx, posZ - entProx, posX + entProx, posY + entProx, posZ + entProx));
			if (!entities.isEmpty()) {
				Iterator it = entities.iterator();
				Entity ent;
				while (it.hasNext()) {
					ent = (Entity) it.next();
					if (ent != null && ent.getClass() != MissileBase.class)//don't collide with missiles
					{
						foundDist = this.getDistanceToEntity(ent);
						if (foundDist < entProx) {
							this.onImpactEntity(ent, (float) posX, (float) posY, (float) posZ);
							break;
						}
					}
				}
			}
		}
	}

	private void onMovementTick() {
		if (this.inGround && persistentBlock != worldObj.getBlockMetadata(persistentBlockPos)) {
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			this.inGround = false;
		}
		if (!this.inGround) {
			Vec3 positionVector;
			positionVector = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 moveVector;
			moveVector = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition hitPosition = this.worldObj.rayTraceBlocks(positionVector, moveVector, false);
			Entity hitEntity = null;
			boolean testEntities = true;
			if (this.worldObj.isRemote) {
				testEntities = false;
			}
			if (testEntities) {
				List nearbyEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().offset(this.motionX, this.motionY, this.motionZ).offset(1.0D, 1.0D, 1.0D)); // .grow replaced by .offset
				double closestHit = 0.0D;
				float borderSize;

				for (int i = 0; i < nearbyEntities.size(); ++i) {
					Entity curEnt = (Entity) nearbyEntities.get(i);
					if (curEnt.canBeCollidedWith()) {
						if (this.launcher != null) {
							if (curEnt == this.launcher || curEnt == this.launcher.riddenByEntity || curEnt == this.shooterLiving || curEnt == this.shooter) {
								continue;
							}
						}
						borderSize = 0.3F;
						AxisAlignedBB var12 = curEnt.boundingBox.expand( borderSize, borderSize, borderSize);
						MovingObjectPosition checkHit = var12.calculateIntercept(positionVector, moveVector);
						if (checkHit != null) {
							double hitDistance = positionVector.distance((Vector3dc) checkHit.hitVec);
							if (hitDistance < closestHit || closestHit == 0.0D) {
								hitEntity = curEnt;
								closestHit = hitDistance;
							}
						}
					}
				}
			}
			if (hitEntity != null) {
				hitPosition = new MovingObjectPosition(hitEntity);
			}
			if (hitPosition != null) {
				if (hitPosition.entityHit != null) {
					this.onImpactEntity(hitPosition.entityHit, (float) posX, (float) posY, (float) posZ);
					this.hasImpacted = true;
					if (!this.ammoType.isPenetrating() && !this.worldObj.isRemote) {
						this.setDead();
					} else if (this.ammoType.isPenetrating()) {
						this.motionX *= 0.65f;
						this.motionY *= 0.65f;
						this.motionZ *= 0.65f;
					}
				} else {
					this.onImpactWorld(hitPosition);
					this.hasImpacted = true;
					if (!this.ammoType.isPenetrating()) {
						this.motionX = (double) ((float) (hitPosition.hitVec.xCoord - this.posX));
						this.motionY = (double) ((float) (hitPosition.hitVec.yCoord - this.posY));
						this.motionZ = (double) ((float) (hitPosition.hitVec.zCoord - this.posZ));
						float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						this.posX -= this.motionX / (double) var20 * 0.05000000074505806D;
						this.posY -= this.motionY / (double) var20 * 0.05000000074505806D;
						this.posZ -= this.motionZ / (double) var20 * 0.05000000074505806D;
						this.inGround = true;
						if (!this.ammoType.isPersistent() && !this.worldObj.isRemote) {
							this.setDead();
						} else if (this.ammoType.isPersistent()) {
							TileEntity te = worldObj.getTileEntity(hitPosition.blockX, hitPosition.blockY, hitPosition.blockZ);
							persistentBlockPos.posX = te.xCoord;
							persistentBlockPos.posY = te.yCoord;
							persistentBlockPos.posZ = te.zCoord;
							persistentBlock = te.blockMetadata;
						}
					} else {
						this.motionX *= 0.65f;
						this.motionY *= 0.65f;
						this.motionZ *= 0.65f;
					}
				}
			}

			if (this.ammoType.isProximityAmmo() && this.ticksExisted > 20) {
				checkProximity();
				if (this.isDead) {
					return;
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;

			if (this.ammoType.isRocket() && this.rocketBurnTime > 0)//if it is a rocket, accellerate if still burning
			{
				this.rocketBurnTime--;
				this.motionX += mX;
				this.motionY += mY;
				this.motionZ += mZ;
				if (this.worldObj.isRemote) {
					this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				}
			} else {
				this.motionY -= (double) this.ammoType.getGravityFactor();
			}
			this.setPosition(this.posX, this.posY, this.posZ);
			if (this.ammoType.updateAsArrow()) {
				this.onUpdateArrowRotation();
			}
		}
	}

	private void onUpdateArrowRotation() {
		double motionSpeed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = Trig.toDegrees((float) Math.atan2(this.motionX, this.motionZ)) - 90;
		this.rotationPitch = Trig.toDegrees((float) Math.atan2(this.motionY, (double) motionSpeed)) - 90;
		while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
	}

	public ResourceLocation getTexture() {
		return ammoType.getModelTexture();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.ammoType = AmmoRegistry.getAmmo(new ResourceLocation(tag.getString("ammoRegistryName")));
		this.inGround = tag.getBoolean("inGround");
		persistentBlockPos = BlockPos.fromLong(tag.getLong("persistentBlockPos"));
		persistentBlock = NBTUtil.readBlockState(tag.getCompoundTag("persistentBlock"));
		this.ticksExisted = tag.getInteger("ticks");
		this.mX = tag.getFloat("mX");
		this.mY = tag.getFloat("mY");
		this.mZ = tag.getFloat("mZ");
		if (this.ammoType == null) {
			this.ammoType = AmmoRegistry.ammoArrow;
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setString("ammoRegistryName", ammoType.getRegistryName().toString());
		tag.setBoolean("inGround", this.inGround);
		tag.setLong("persistentBlockPos", persistentBlockPos.toLong());
		NBTTagCompound block = new NBTTagCompound();
		NBTUtil.writeBlockState(block, persistentBlock);
		tag.setTag("persistentBlock", block);
		tag.setInteger("ticks", this.ticksExisted);
		tag.setFloat("mX", this.mX);
		tag.setFloat("mY", this.mY);
		tag.setFloat("mZ", this.mZ);
	}

	@Override
	protected void entityInit() {
		//NOOP
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		PacketBuffer pb = new PacketBuffer(data);
		pb.writeString(ammoType.getRegistryName().toString());
		pb.writeFloat(rotationYaw);
		pb.writeFloat(rotationPitch);
		pb.writeBoolean(inGround);
		pb.writeLong(persistentBlockPos.toLong());
		pb.writeInt(Block.getStateId(persistentBlock));
		pb.writeInt(rocketBurnTime);
		pb.writeBoolean(this.launcher != null);
		if (this.launcher != null) {
			pb.writeInt(this.launcher.getEntityId());
		}
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		PacketBuffer pb = new PacketBuffer(data);
		ammoType = AmmoRegistry.getAmmo(new ResourceLocation(pb.readString(64)));
		this.prevRotationYaw = this.rotationYaw = pb.readFloat();
		this.prevRotationPitch = this.rotationPitch = pb.readFloat();
		this.inGround = pb.readBoolean();
		persistentBlockPos = BlockPos.fromLong(pb.readLong());
		persistentBlock = Block.getStateById(pb.readInt());
		this.rocketBurnTime = pb.readInt();
		boolean hasLauncher = pb.readBoolean();
		if (hasLauncher) {
			launcher = worldObj.getEntityByID(pb.readInt());
		}
	}
}
