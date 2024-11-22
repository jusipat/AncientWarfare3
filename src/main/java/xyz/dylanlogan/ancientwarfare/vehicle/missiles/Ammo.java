package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.joml.Vector3i;
import xyz.dylanlogan.ancientwarfare.core.owner.Owner;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Ammo implements IAmmo {
	int entityDamage;
	int vehicleDamage;
	private static final float GRAVITY_FACTOR = 9.81f * 0.05f * 0.05f;
	String configName = "none";
	ResourceLocation modelTexture = new ResourceLocation("missingno");
	boolean isRocket = false;
	boolean isArrow = false;
	boolean isPersistent = false;
	boolean isFlaming = false;
	boolean isProximityAmmo = false;
	boolean isCraftable = true;
	boolean isEnabled = true;
	float groundProximity = 0.f;
	float entityProximity = 0.f;
	float ammoWeight = 10;
	float renderScale = 1.f;
	int secondaryAmmoCount = 0;

	private ResourceLocation registryName;

	public Ammo(String regName) {
		registryName = new ResourceLocation(AncientWarfareVehicles.MOD_ID, regName);
	}

	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	@Override
	public void setEntityDamage(int damage) {
		if (damage < 0) {
			damage = 0;
		}
		this.entityDamage = damage;
	}

	@Override
	public void setVehicleDamage(int damage) {
		if (damage < 0) {
			damage = 0;
		}
		this.vehicleDamage = damage;
	}

	@Override
	public String getConfigName() {
		return configName;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean val) {
		this.isEnabled = val;
	}

	@Override
	public ResourceLocation getModelTexture() {
		return modelTexture;
	}

	@Override
	public boolean isRocket() {
		return isRocket;
	}

	@Override
	public boolean isPersistent() {
		return isPersistent;
	}

	@Override
	public boolean updateAsArrow() {
		return isArrow;
	}

	@Override
	public boolean isAvailableAsItem() {
		return this.isCraftable;
	}

	@Override
	public float getAmmoWeight() {
		return ammoWeight;
	}

	/**
	 * override to implement upgrade-specific ammo use.  this method will be checked before a vehicle will accept ammo into its bay, or fire ammo from its bay
	 */
	@Override
	public boolean isAmmoValidFor(VehicleBase vehicle) {
		return true;
	}

	@Override
	public float getRenderScale() {
		return renderScale;
	}

	@Override
	public float getGravityFactor() {
		return GRAVITY_FACTOR;
	}

	@Override
	public int getEntityDamage() {
		return entityDamage;
	}

	@Override
	public int getVehicleDamage() {
		return vehicleDamage;
	}

	@Override
	public boolean isFlaming() {
		return isFlaming;
	}

	@Override
	public boolean isPenetrating() {
		return false;
	}

	@Override
	public IAmmo getSecondaryAmmoType() {
		return null;
	}

	@Override
	public int getSecondaryAmmoTypeCount() {
		return secondaryAmmoCount;
	}

	@Override
	public boolean hasSecondaryAmmo() {
		return false;
	}

	@Override
	public boolean isProximityAmmo() {
		return isProximityAmmo;
	}

	@Override
	public float entityProximity() {
		return entityProximity;
	}

	@Override
	public float groundProximity() {
		return groundProximity;
	}

	private void breakBlockAndDrop(World world, BlockPos pos) {
		if (!AWVehicleStatics.generalSettings.shotsDestroysBlocks) {
			return;
		}
		BlockTools.breakBlockAndDrop(world, null, pos.x, pos.y, pos.z);
	}

	protected void igniteBlock(World world, int x, int y, int z, int maxSearch) {
		if (!AWVehicleStatics.generalSettings.blockFires) {
			return;
		}
		for (int i = 0; i < maxSearch && y - i >= 1; i++) {
			BlockPos curPos = new BlockPos(x, y - i, z);
			if (!world.isAirBlock(curPos.getX(), curPos.getY(), curPos.getZ()) && world.isAirBlock(curPos.getX(), curPos.getY()+1, curPos.getZ())) {
				world.setBlock(curPos.getX(), curPos.getY(), curPos.getZ(), Blocks.fire);
			}
		}
	}

	public static boolean shouldEffectEntity(World world, Entity entity, MissileBase missile) {
		if (!AWVehicleStatics.generalSettings.allowFriendlyFire && missile.shooterLiving instanceof NpcBase) {
			@Nonnull NpcBase npc = ((NpcBase) missile.shooterLiving);
			if (entity instanceof NpcBase) {
				//Owner targetNpcOwner = ((NpcBase) entity).getOwner(); todo: fix owner get
				//return !npc.getOwner().isOwnerOrSameTeamOrFriend(world, targetNpcOwner.getUUID(), targetNpcOwner.getName());
			} else if (entity instanceof EntityPlayer) {
				//return !npc.getOwner().isOwnerOrSameTeamOrFriend(world, entity.getUniqueID(), ((EntityPlayer) entity).getDisplayName());
			}
		}
		return true;
	}

	protected void setBlockToLava(World world, int x, int y, int z, int maxSearch) {
		if (!AWVehicleStatics.generalSettings.blockFires) {
			return;
		}
		for (int i = 0; i < maxSearch && y - i >= 1; i++) {
			BlockPos curPos = new BlockPos(x, y - i, z);
			if (!world.isAirBlock(curPos.getX(), curPos.getY(), curPos.getZ()) && world.isAirBlock(curPos.getX(), curPos.getY()+1, curPos.getZ())) {
				world.setBlock(curPos.getX(), curPos.getY(), curPos.getZ(), Blocks.lava);
			}
		}
	}

	protected void createExplosion(World world, @Nullable MissileBase missile, float x, float y, float z, float power) {
		boolean destroyBlocks = AWVehicleStatics.generalSettings.shotsDestroysBlocks;
		boolean fires = AWVehicleStatics.generalSettings.blockFires;

		world.newExplosion(missile, x, y, z, power, fires, destroyBlocks);
	}

	protected void spawnGroundBurst(World world, MovingObjectPosition hit, float maxVelocity, IAmmo type, int count, float minPitch, Entity shooter) {
		Vec3 dirVec = hit.hitVec;
		Vec3 hitVec = hit.hitVec.addVector(dirVec.xCoord * 0.2f, dirVec.yCoord * 0.2f, dirVec.zCoord * 0.2f);
		spawnBurst(world, maxVelocity, type, count, minPitch, shooter, EnumFacing.getFront(hit.sideHit), (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
	}

	private void spawnBurst(World world, float maxVelocity, IAmmo type, int count, float minPitch, Entity shooter, EnumFacing sideHit, float x, float y, float z) {
		world.newExplosion(null, x, y, z, 0.25f, false, true);
		createExplosion(world, null, x, y, z, 1.f);
		float randRange = 90 - minPitch;
		if (type.hasSecondaryAmmo()) {
			count = type.getSecondaryAmmoTypeCount();
			type = type.getSecondaryAmmoType();
		}
		for (int i = 0; i < count; i++) {
			float yaw;
			float pitch;
			float randVelocity;
			float velocity;
			if (sideHit.getFrontOffsetY() == 0) {
				pitch = 90 - (world.rand.nextFloat() * randRange);
				yaw = world.rand.nextFloat() * 360.f;
				randVelocity = world.rand.nextFloat();
				randVelocity = randVelocity < 0.5f ? 0.5f : randVelocity;
				velocity = maxVelocity * randVelocity;
			} else {
				float minYaw = getMinYaw(sideHit);
				float maxYaw = getMaxYaw(sideHit);
				if (minYaw > maxYaw) {
					float tmp = maxYaw;
					maxYaw = minYaw;
					minYaw = tmp;
				}
				float yawRange = maxYaw - minYaw;
				pitch = 90 - (world.rand.nextFloat() * randRange);
				yaw = minYaw + (world.rand.nextFloat() * yawRange);
				randVelocity = world.rand.nextFloat();
				randVelocity = randVelocity < 0.5f ? 0.5f : randVelocity;
				velocity = maxVelocity * randVelocity;
			}
			MissileBase missile = getMissileByType(type, world, x, y, z, yaw, pitch, velocity, shooter);
			world.spawnEntityInWorld(missile);
		}
	}

	private float getMinYaw(EnumFacing side) {
		switch (side) {
			case NORTH://north
				return 360f - 45f;
			case SOUTH://south
				return 180f - 45f;
			case WEST://west
				return 90f - 45f;
			case EAST://east
				return 270f - 45f;
			default:
				return 0;
		}
	}

	private float getMaxYaw(EnumFacing side) {
		switch (side) {
			case NORTH://north
				return 360f + 45f;
			case SOUTH://south
				return 180f + 45f;
			case WEST://west
				return 90f + 45f;
			case EAST://east
				return 270f + 45f;
			default:
				return 0;
		}
	}

	protected void spawnAirBurst(World world, float x, float y, float z, float maxVelocity, IAmmo type, int count, Entity shooter) {
		spawnBurst(world, maxVelocity, type, count, -90, shooter, EnumFacing.DOWN, x, y, z);
	}

	protected void breakAroundOnLevel(World world, BlockPos origin, BlockPos center, float maxHardness) {
		affectBlock(world, origin, center, maxHardness);
//		affectBlock(world, origin, center.north(), maxHardness);
//		affectBlock(world, origin, center.east(), maxHardness);
//		affectBlock(world, origin, center.west(), maxHardness);
//		affectBlock(world, origin, center.south(), maxHardness);
//		affectBlock(world, origin, center.north().east(), maxHardness);
//		affectBlock(world, origin, center.east().south(), maxHardness);
//		affectBlock(world, origin, center.south().west(), maxHardness);
//		affectBlock(world, origin, center.west().north(), maxHardness);
	}

	private void affectBlock(World world, BlockPos origin, BlockPos pos, float maxHardness) {
		double distanceAdjustedHardness = maxHardness - origin.distance(pos.getX(), pos.getY(), pos.getZ()) * 15;
		if (distanceAdjustedHardness > 0 && distanceAdjustedHardness > world.getBlock(pos.x, pos.y, pos.z).getBlockHardness(world, pos.x, pos.y, pos.z)) {
			breakBlockAndDrop(world, pos);
		}
	}

	private MissileBase getMissileByType(IAmmo type, World world, float x, float y, float z, float yaw, float pitch, float velocity, Entity shooter) {
		MissileBase missile = new MissileBase(world);
		missile.setShooter(shooter);
		missile.setMissileParams2(type, x, y, z, yaw, pitch, velocity);
		return missile;
	}
}
