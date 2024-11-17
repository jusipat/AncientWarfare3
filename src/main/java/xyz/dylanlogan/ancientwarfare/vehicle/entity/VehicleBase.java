package xyz.dylanlogan.ancientwarfare.vehicle.entity;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import com.gtnewhorizon.gtnhlib.client.renderer.util.MathUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.joml.Vector3d;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IOwnable;
import xyz.dylanlogan.ancientwarfare.core.owner.Owner;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;
import xyz.dylanlogan.ancientwarfare.npc.config.AWNPCStatics;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.VehicleVarHelpers.DummyVehicleHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.IVehicleArmor;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.materials.IVehicleMaterial;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.types.VehicleType;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleAmmoHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleMoveHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleUpgradeHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.inventory.VehicleInventory;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.AmmoHwachaRocket;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.pathing.Navigator;
import xyz.dylanlogan.ancientwarfare.vehicle.pathing.Node;
import xyz.dylanlogan.ancientwarfare.vehicle.pathing.PathWorldAccessEntity;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.IVehicleUpgradeType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class VehicleBase extends Entity implements IEntityAdditionalSpawnData, IMissileHitCallback, IPathableEntity, IOwnable {

    //private static final DataParameter<Float> VEHICLE_HEALTH = EntityDataManager.createKey(VehicleBase.class, DataSerializers.FLOAT);
    //private static final DataParameter<Byte> FORWARD_INPUT = EntityDataManager.createKey(VehicleBase.class, DataSerializers.BYTE);
    //private static final DataParameter<Byte> STRAFE_INPUT = EntityDataManager.createKey(VehicleBase.class, DataSerializers.BYTE);
    public static final int VEHICLE_HEALTH = 0;
    public static final int FORWARD_INPUT = 0;
    public static final int STRAFE_INPUT = 0;

    /**
     * these are the current max stats.  set from setVehicleType().
     * these are local cached bases, after application of material factors
     * should not be altered at all after vehicle is first initialized
     */
    public float baseForwardSpeed;
    public float baseStrafeSpeed;
    public float basePitchMin;
    public float basePitchMax;
    private float baseTurretRotationMax;
    private float baseLaunchSpeedMax;
    public float baseHealth = 100;
    private float baseAccuracy = 1.f;
    public float baseWeight = 1000;//kg
    private int baseReloadTicks = 100;
    private float baseGenericResist = 0.f;
    private float baseFireResist = 0.f;
    private float baseExplosionResist = 0.f;
    private int hurtInvulTicks = 0;

    /**
     * local current stats, fully updated and modified from upgrades/etc. should not be altered aside from
     * upgrades/armor
     */
    public float currentForwardSpeedMax = 0.42f;
    public float currentStrafeSpeedMax = 2.0f;

    /**
     * how many ticks is a reloadCycle, at current upgrade status?
     * the currentReload status is stored in firingHelper
     */
    public int currentReloadTicks = 100;
    public float currentTurretPitchMin = 0.f;
    public float currentTurretPitchMax = 90.f;
    public float currentLaunchSpeedPowerMax = 32.321f;
    public float currentGenericResist = 0.f;
    public float currentFireResist = 0.f;
    public float currentExplosionResist = 0.f;
    public float currentWeight = 1000.f;
    public float currentTurretPitchSpeed = 0.f;
    public float currentTurretYawSpeed = 0.f;
    public float currentAccuracy = 1.f;
    public float currentTurretRotationMax = 45.f;

    /**
     * local variables, may be altered by input/etc...
     */
    public float localTurretRotationHome = 0.f;
    public float localTurretRotation = 0.f;
    public float localTurretDestRot = 0.f;
    private float localTurretRotInc = 1.f;
    public float localTurretPitch = 45.f;
    public float localTurretDestPitch = 45.f;
    private float localTurretPitchInc = 1.f;
    public float localLaunchPower = 31.321f;

    /**
     * set by move helper on movement update. used during client rendering to update
     * wheel rotation and other movement speed based animations (airplanes use for prop,
     * helicopter uses for main and tail rotors).
     */
    public float wheelRotation = 0.f;
    public float wheelRotationPrev = 0.f;

    /**
     * used to determine if it should allow interaction (setup time on vehicle placement)
     */
    private boolean isSettingUp = false;

    /**
     * set client-side when incoming damage is taken
     */
    public int hitAnimationTicks = 0;

    private NpcBase assignedRider = null;

    /**
     * complex stat tracking helpers, move, ammo, upgrades, general stats
     */
    public VehicleAmmoHelper ammoHelper;
    public VehicleUpgradeHelper upgradeHelper;
    public VehicleMoveHelper moveHelper;
    public VehicleFiringHelper firingHelper;
    public VehicleFiringVarsHelper firingVarsHelper;
    public VehicleInventory inventory;
    public Navigator nav;
    public PathWorldAccessEntity worldAccess;
    public IVehicleType vehicleType = VehicleRegistry.CATAPULT_STAND_FIXED;//set to dummy vehicle so it is never null...
    public int vehicleMaterialLevel = 0;//the current material level of this vehicle. should be read/set prior to calling updateBaseStats
    private Owner owner = Owner.EMPTY;

    public VehicleBase(World par1World) {
        super(par1World);
        this.upgradeHelper = new VehicleUpgradeHelper(this);
        this.moveHelper = new VehicleMoveHelper(this);
        this.ammoHelper = new VehicleAmmoHelper(this);
        this.firingHelper = new VehicleFiringHelper(this);
        this.firingVarsHelper = new DummyVehicleHelper(this);
        this.inventory = new VehicleInventory(this);
        this.worldAccess = new PathWorldAccessEntity(par1World, this);
        this.nav = new Navigator(this);
        this.nav.setStuckCheckTicks(100);
        this.stepHeight = 1.12f;
        this.entityCollisionReduction = 0.9f;
        this.onGround = false;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(VEHICLE_HEALTH,100f);
        this.dataWatcher.addObject(FORWARD_INPUT, (byte) 0);
        this.dataWatcher.addObject(STRAFE_INPUT, (byte) 0);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.vehicleType.getStackForLevel(vehicleMaterialLevel);
    }

    /**
     * overriden to help with vision checks for vehicles
     */
    @Override
    public float getEyeHeight() {
        return 1.6F;
    }

    public void setHealth(float health) {
        if (health > this.baseHealth) {
            health = this.baseHealth;
        }
        this.dataWatcher.updateObject(VEHICLE_HEALTH, health);
    }

    public boolean canTurretTurn() {
        return Math.abs(baseTurretRotationMax - 0) > 1e-9; // replaced epsilonEquals method in MathUtils
    }

    public float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(VEHICLE_HEALTH);
    }

    public void setVehicleType(IVehicleType vehicle, int materialLevel) {
        this.vehicleType = vehicle;
        this.vehicleMaterialLevel = materialLevel;
        VehicleFiringVarsHelper help = vehicle.getFiringVarsHelper(this);
        if (help != null) {
            this.firingVarsHelper = help;
        }
        float width = vehicleType.getWidth();
        float height = vehicleType.getHeight();
        this.setSize(width, height);
        for (IAmmo ammo : vehicleType.getValidAmmoTypes()) {
            this.ammoHelper.addUseableAmmo(ammo);
        }
        for (IVehicleUpgradeType up : this.vehicleType.getValidUpgrades()) {
            this.upgradeHelper.addValidUpgrade(up);
        }
        for (IVehicleArmor armor : this.vehicleType.getValidArmors()) {
            this.upgradeHelper.addValidArmor(armor);
        }
        this.inventory.setInventorySizes(vehicle.getUpgradeBaySize(), vehicle.getAmmoBaySize(), vehicle.getArmorBaySize(), vehicle.getStorageBaySize());
        this.updateBaseStats();
        this.resetCurrentStats();

        if (this.localTurretPitch < currentTurretPitchMin) {
            this.localTurretPitch = currentTurretPitchMin;
        } else if (this.localTurretPitch > currentTurretPitchMax) {
            this.localTurretPitch = currentTurretPitchMax;
        }
        this.localLaunchPower = this.firingHelper.getAdjustedMaxMissileVelocity();
        if (!this.canAimRotate()) {
            this.localTurretRotation = this.rotationYaw;
        }
        this.nav.setCanGoOnLand(vehicleType.getMovementType() == VehicleMovementType.GROUND);
    }

    private int setupTicks = 0;

    public void setSetupState(boolean state, int ticks) {
        this.isSettingUp = state;
        if (state) {
            setupTicks = ticks;
        } else {
            setupTicks = 0;
        }
    }

    public void setInitialHealth() {
        this.setHealth(this.baseHealth);
    }

    private void updateBaseStats() {
        IVehicleMaterial material = vehicleType.getMaterialType();
        int level = this.vehicleMaterialLevel;
        baseForwardSpeed = vehicleType.getBaseForwardSpeed() * material.getSpeedForwardFactor(level);
        baseStrafeSpeed = vehicleType.getBaseStrafeSpeed() * material.getSpeedStrafeFactor(level);
        basePitchMin = vehicleType.getBasePitchMin();
        basePitchMax = vehicleType.getBasePitchMax();
        baseTurretRotationMax = vehicleType.getBaseTurretRotationAmount();
        baseLaunchSpeedMax = vehicleType.getBaseMissileVelocityMax();
        baseHealth = vehicleType.getBaseHealth() * material.getHPFactor(level);
        baseAccuracy = vehicleType.getBaseAccuracy() * material.getAccuracyFactor(level);
        baseWeight = vehicleType.getBaseWeight() * material.getWeightFactor(level);
        baseExplosionResist = 0.f;
        baseFireResist = 0.f;
        baseGenericResist = 0.f;
        if (getHealth() > baseHealth) {
            this.setHealth(baseHealth);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        super.onCollideWithPlayer(par1EntityPlayer);
        if (!worldObj.isRemote && par1EntityPlayer instanceof EntityPlayerMP && par1EntityPlayer.posY > posY && ((EntityPlayerMP) par1EntityPlayer).collidedVertically) {
            EntityPlayerMP player = (EntityPlayerMP) par1EntityPlayer;
/*			TODO handle collision with players to allow them flying and disallow once they stop colliding
			probably a collection of players in collision with this entity, on update check if they have collided in the last 10? ticks and if not disallow flying
			player.capabilities.allowFlying = true;
*/
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * return an itemStack tagged appropriately for this vehicle
     *
     * @return
     */
    private ItemStack getItemForVehicle() {
        ItemStack stack = this.vehicleType.getStackForLevel(vehicleMaterialLevel);
        stack.getTagCompound().getCompoundTag("spawnData").setFloat("health", getHealth());
        return stack;
    }

    private float getHorizontalMissileOffset() {
        return this.vehicleType.getMissileHorizontalOffset();
    }

    private float getVerticalMissileOffset() {
        return this.vehicleType.getMissileVerticalOffset();
    }

    private float getForwardsMissileOffset() {
        return this.vehicleType.getMissileForwardsOffset();
    }

    public boolean isAimable() {
        return !this.isSettingUp && vehicleType.isCombatEngine();
    }

    public boolean canAimRotate() {
        return !this.isSettingUp && vehicleType.canAdjustYaw();
    }

    public boolean canAimPitch() {
        return !this.isSettingUp && vehicleType.canAdjustPitch();
    }

    public boolean canAimPower() {
        return !this.isSettingUp && vehicleType.canAdjustPower();
    }

    /**
     * used by inputHelper to determine if it should check movement input keys and send info to server..
     *
     * @return
     */
    public boolean isDrivable() {
        return !this.isSettingUp && vehicleType.isDrivable();
    }

    public boolean isMountable() {
        return !this.isSettingUp && vehicleType.isMountable();
    }

    private float getRiderForwardOffset() {
        return vehicleType.getRiderForwardsOffset();
    }

    public float getRiderVerticalOffset() {
        return vehicleType.getRiderVerticalOffest();
    }

    private float getRiderHorizontalOffset() {
        return vehicleType.getRiderHorizontalOffset();
    }

    /**
     * should return the maximum range allowed in order to hit a point at a given vertical offset
     * will vary by vehicle type (power/angle/missile offset) and current ammo selection
     * need to figure out....yah....
     *
     * @param verticalOffset
     * @return
     */
    public float getEffectiveRange(float verticalOffset) {
        if (vehicleType == VehicleRegistry.BATTERING_RAM)//TODO ugly hack...
        {
            return 1.5f;
        }
        float angle;
        if (currentTurretPitchMin < 45 && currentTurretPitchMax > 45)//if the angle stradles 45, return 45
        {
            angle = 45;
        } else if (currentTurretPitchMin < 45 && currentTurretPitchMax < 45)//else if both are below 45, get the largest
        {
            angle = currentTurretPitchMax;
        } else {
            angle = currentTurretPitchMin;//else get the lowest
        }
        return getEffectiveRange(verticalOffset, angle, firingHelper.getAdjustedMaxMissileVelocity(), 0, ammoHelper.getCurrentAmmoType() != null && ammoHelper.getCurrentAmmoType().isRocket());
    }

    private float getEffectiveRange(float y, float angle, float velocity, int maxIterations, boolean rocket) {
        float motX = Trig.sinDegrees(angle) * velocity * 0.05f;
        float motY = Trig.cosDegrees(angle) * velocity * 0.05f;
        float rocketX = 0;
        float rocketY = 0;
        if (rocket) {
            int rocketBurnTime = (int) (velocity * AmmoHwachaRocket.BURN_TIME_FACTOR);
            float motX0 = (motX / (velocity * 0.05f)) * AmmoHwachaRocket.ACCELERATION_FACTOR;
            float motY0 = (motY / (velocity * 0.05f)) * AmmoHwachaRocket.ACCELERATION_FACTOR;
            motX = motX0;
            motY = motY0;
            while (rocketBurnTime > 0) {
                rocketX += motX;
                rocketY += motY;
                rocketBurnTime--;
                motX += motX0;
                motY += motY0;
            }
            y -= rocketY;
        }
        motX *= 20.f;
        motY *= 20.f;
        float gravity = 9.81f;
        float t = motY / gravity;
        float tQ = MathHelper.sqrt_float(((motY * motY) / (gravity * gravity)) - ((2 * y) / gravity));
        float tPlus = t + tQ;
        float tMinus = t - tQ;
        t = tPlus > tMinus ? tPlus : tMinus;
        return (motX * t) + rocketX;
    }

    /**
     * get a fully translated offset position for missile spawn for the current aim and vehicle params
     *
     * @return
     */
    public Vector3d getMissileOffset() {
        float x1 = this.vehicleType.getTurretPosX();
        float y1 = this.vehicleType.getTurretPosY();
        float z1 = this.vehicleType.getTurretPosZ();
        float angle = 0;
        float len = 0;
        if (x1 != 0 || z1 != 0) {
            angle = Trig.toDegrees((float) Math.atan2(z1, x1));
            len = MathHelper.sqrt_float(x1 * x1 + z1 * z1);
            angle += this.rotationYaw;
            x1 = Trig.cosDegrees(angle) * len;
            z1 = -Trig.sinDegrees(angle) * len;
        }

        float x = this.getHorizontalMissileOffset();
        float y = this.getVerticalMissileOffset();
        float z = this.getForwardsMissileOffset();
        if (x != 0 || z != 0 || y != 0) {
            angle = Trig.toDegrees((float) Math.atan2(z, x));
            len = MathHelper.sqrt_float(x * x + z * z + y * y);
            angle += this.localTurretRotation;
            x = Trig.cosDegrees(angle) * Trig.sinDegrees(localTurretPitch + rotationPitch) * len;
            z = -Trig.sinDegrees(angle) * Trig.sinDegrees(localTurretPitch + rotationPitch) * len;
            y = Trig.cosDegrees(localTurretPitch + rotationPitch) * len;
        }
        x += x1;
        z += z1;
        y += y1;
        return new Vector3d(x, y, z);
    }

    /**
     * called on every tick that the vehicle is 'firing' to update the firing animation and to call
     * launchMissile when animation has reached launch point
     */
    public void onFiringUpdate() {
        this.firingVarsHelper.onFiringUpdate();
    }

    /**
     * called every tick after the vehicle has fired, until reload timer is complete, to update animations
     */
    public void onReloadUpdate() {
        this.firingVarsHelper.onReloadUpdate();
    }

    @Override // this isnt in 1.7
    protected void doBlockCollisions() {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        BlockPos.PooledMutableBlockPos posMin = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
        BlockPos.PooledMutableBlockPos posMax = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
        BlockPos.PooledMutableBlockPos currentPos = BlockPos.PooledMutableBlockPos.retain();

        if (worldObj.isAreaLoaded(posMin, posMax)) {
            for (int i = posMin.getX(); i <= posMax.getX(); ++i) {
                for (int j = posMin.getY(); j <= posMax.getY(); ++j) {
                    for (int k = posMin.getZ(); k <= posMax.getZ(); ++k) {
                        currentPos.setPos(i, j, k);
                        IBlockState iblockstate = world.getBlockState(currentPos);

                        try {
                            iblockstate.getBlock().onEntityCollidedWithBlock(world, currentPos, iblockstate, this);
                            onInsideBlock(iblockstate, currentPos);
                        }
                        catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
                            CrashReportCategory.addBlockInfo(crashreportcategory, currentPos, iblockstate);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }

        posMin.release();
        posMax.release();
        currentPos.release();
    }

    protected void onInsideBlock(int x, int y, int z) {
        if (worldObj.getBlock(x, y, z) == Blocks.waterlily) {
            int data = worldObj.getBlockMetadata(x, y, z);
            worldObj.destroyBlockInWorldPartially(data,x,y,z, -1);
        }
    }

    /**
     * called every tick after startLaunching() is called, until setFinishedLaunching() is called...
     */
    public void onLaunchingUpdate() {
        this.firingVarsHelper.onLaunchingUpdate();
    }

    /**
     * reset all upgradeable stats back to the base for this vehicle
     */
    public void resetCurrentStats() {
        this.currentForwardSpeedMax = this.baseForwardSpeed;
        this.currentStrafeSpeedMax = this.baseStrafeSpeed;
        this.currentTurretPitchMin = this.basePitchMin;
        this.currentTurretPitchMax = this.basePitchMax;
        this.currentTurretRotationMax = this.baseTurretRotationMax;
        this.currentReloadTicks = this.baseReloadTicks;
        this.currentLaunchSpeedPowerMax = this.baseLaunchSpeedMax;
        this.currentExplosionResist = this.baseExplosionResist;
        this.currentFireResist = this.baseFireResist;
        this.currentGenericResist = this.baseGenericResist;
        this.currentWeight = this.baseWeight;
        this.currentAccuracy = this.baseAccuracy;
    }

    @Override
    public void setDead() {
        if (!this.worldObj.isRemote && !this.isDead && this.getHealth() <= 0) {
            InventoryTools.dropItemsInWorld(worldObj, inventory.ammoInventory, posX, posY, posZ);
            InventoryTools.dropItemsInWorld(worldObj, inventory.armorInventory, posX, posY, posZ);
            InventoryTools.dropItemsInWorld(worldObj, inventory.upgradeInventory, posX, posY, posZ);
            InventoryTools.dropItemsInWorld(worldObj, inventory.storageInventory, posX, posY, posZ);
        }
        super.setDead();
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return getPassengers().isEmpty() ? null : getPassengers().get(0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            this.onUpdateClient();
        } else {
            this.onUpdateServer();
        }
        this.updateTurretPitch();
        this.updateTurretRotation();
        this.moveHelper.onUpdate();
        this.firingHelper.onTick();
        this.firingVarsHelper.onTick();
        if (this.hitAnimationTicks > 0) {
            this.hitAnimationTicks--;
        }
        if (this.isSettingUp) {
            this.setupTicks--;
            if (this.setupTicks <= 0) {
                this.isSettingUp = false;
            }
        }
        if (this.hurtInvulTicks > 0) {
            this.hurtInvulTicks--;
        }
        if (this.assignedRider != null) {
            if (assignedRider.isDead || assignedRider.ridingEntity != this || !assignedRider.isRiding() || assignedRider.ridingEntity != this || (this.getDistanceSq(assignedRider) > (AWNPCStatics.npcActionRange * AWNPCStatics.npcActionRange))) {
                //TODO config setting for vehicle search range
                this.assignedRider = null;
            }
        }
/* TODO perf test vehicles
		ServerPerformanceMonitor.addVehicleTickTime(System.nanoTime() - t1);
*/
    }

    /**
     * client-side updates
     */
    private void onUpdateClient() {
        if (getControllingPassenger() instanceof NpcBase) {
            // this.updatePassenger(getControllingPassenger()); todo: Not in 1.7
        }
    }

    //    @Override
//    public boolean startRiding(Entity entity, boolean force) {
//        if (super.startRiding(entity, force)) {
//            if (entity instanceof EntityPlayerMP) {
//                EntityPlayerMP player = (EntityPlayerMP) entity;
//                player.capabilities.allowFlying = true;
//            }
//            return true;
//        }
//        return false;
//    }

    /**
     * server-side updates...
     */
    private void onUpdateServer() {
        if (this.getControllingPassenger() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) this.getControllingPassenger();
            if (player.isSneaking()) {
                this.handleDismount(player);
                player.setSneaking(false);
            }
        }
    }

    private void handleDismount(EntityLivingBase rider) {
        int xMin = MathHelper.floor_double(this.posX - this.width / 2);
        int zMin = MathHelper.floor_double(this.posZ - this.width / 2);
        int yMin = MathHelper.floor_double(posY) - 2;

        if (rider instanceof EntityPlayerMP) {
            ((EntityPlayerMP) rider).capabilities.allowFlying = false;
        }
        rider.dismountEntity(rider);

        searchLabel:
        for (int y = yMin; y <= yMin + 3; y++) {
            for (int x = xMin; x <= xMin + (int) width; x++) {
                for (int z = zMin; z <= zMin + (int) width; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    int state = worldObj.getBlockMetadata(pos.x, pos.y, pos.z);
                    if (state.isSideSolid(worldObj, pos, EnumFacing.UP) || state.getMaterial() == Material.water) {
                        if (worldObj.isAirBlock(pos.up()) && worldObj.isAirBlock(pos.up().up())) {
                            rider.setPositionAndUpdate(x + 0.5d, y + 1, z + 0.5d);
                            break searchLabel;
                        }
                    }
                }
            }
        }
    }

    private void updateTurretPitch() {
        float prevPitch = this.localTurretPitch;
        if (!Trig.isAngleBetween(localTurretPitch, currentTurretPitchMin, currentTurretPitchMax)) {
            localTurretPitch = currentTurretPitchMin;
        }

        if (!Trig.isAngleBetween(localTurretDestPitch, currentTurretPitchMin, currentTurretPitchMax)) {
            localTurretDestPitch = currentTurretPitchMin;
        }

        if (!canAimPitch()) {
            localTurretDestPitch = localTurretPitch;
        }

        if (!Trig.anglesEqual(localTurretPitch, localTurretDestPitch)) {
            if (Math.abs(Trig.getAngleDiffSigned(localTurretDestPitch, localTurretPitch)) < localTurretPitchInc) {
                localTurretPitch = localTurretDestPitch;
            } else {
                localTurretPitch += Trig.getAngleDiffSigned(localTurretPitch, localTurretDestPitch) > 0 ? localTurretPitchInc : -localTurretPitchInc;
            }
        }
        this.currentTurretPitchSpeed = prevPitch - this.localTurretPitch;
    }

//    @Override
//    protected void addPassenger(Entity passenger) {
//        super.addPassenger(passenger);
//        if (passenger instanceof NpcFactionSiegeEngineer) {
//            currentTurretPitchMin = vehicleType.getBasePitchMin() - 4 * 3;
//            currentTurretPitchMax = vehicleType.getBasePitchMax() + 4 * 3;
//        }
//    }
//
//    @Override
//    protected void removePassenger(Entity passenger) {
//        super.removePassenger(passenger);
//        if (passenger instanceof NpcFactionSiegeEngineer) {
//            upgradeHelper.updateUpgradeStats();
//        }
//    }

    private void updateTurretRotation() {
        float prevYaw = this.localTurretRotation;
        this.localTurretRotationHome = Trig.wrapTo360(this.rotationYaw);
        if (!canAimRotate()) {
            localTurretRotation = Trig.wrapTo360(this.rotationYaw);
            localTurretDestRot = localTurretRotation;
        }
        if (Math.abs(localTurretDestRot - localTurretRotation) > localTurretRotInc) {
            while (localTurretRotation < 0) {
                localTurretRotation += 360;
                prevYaw += 360;
            }
            while (localTurretRotation >= 360) {
                localTurretRotation -= 360;
                prevYaw -= 360;
            }
            localTurretDestRot = Trig.wrapTo360(localTurretDestRot);
            float curMod = localTurretRotation;
            float destMod = localTurretDestRot;
            float diff = curMod > destMod ? curMod - destMod : destMod - curMod;
            float turnDir = 0;
            if (curMod > destMod) {
                if (diff < 180) {
                    turnDir = -1;
                } else {
                    turnDir = 1;
                }
            } else if (curMod < destMod) {
                if (diff < 180) {
                    turnDir = 1;
                } else {
                    turnDir = -1;
                }
            }
            localTurretRotation += localTurretRotInc * turnDir;
        } else {
            localTurretRotation = localTurretDestRot;
        }
        if (Math.abs(localTurretDestRot - localTurretRotation) < localTurretRotInc) {
            localTurretRotation = localTurretDestRot;
        }
        this.currentTurretYawSpeed = this.localTurretRotation - prevYaw;
        if (this.currentTurretYawSpeed > 180) {
            this.currentTurretYawSpeed -= 360.f;
        }
        if (this.currentTurretYawSpeed < -180) {
            this.currentTurretYawSpeed += 360.f;
        }
    }

    public void updateTurretAngles(float pitch, float rotation) {
        this.localTurretPitch = pitch;
        this.localTurretRotation = rotation;
        this.localTurretDestPitch = this.localTurretPitch;
        this.localTurretDestRot = this.localTurretRotation;
    }

    /**
     * spits out inventory into world, and packs the vehicle into an item, also spat into the world
     */
    public void packVehicle() {
        if (!this.worldObj.isRemote) {
            InventoryTools.dropItemInWorld(worldObj, getItemForVehicle(), posX, posY, posZ);
            InventoryTools.dropInventoryInWorld(worldObj, inventory.ammoInventory, posX, posY, posZ);
            InventoryTools.dropInventoryInWorld(worldObj, inventory.armorInventory, posX, posY, posZ);
            InventoryTools.dropInventoryInWorld(worldObj, inventory.upgradeInventory, posX, posY, posZ);
            InventoryTools.dropInventoryInWorld(worldObj, inventory.storageInventory, posX, posY, posZ);
            this.setDead();
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (!damageSource.isExplosion()) {
            if (hurtInvulTicks > 0) {
                return false;
            }
            hurtInvulTicks = 10;
        }

        if (this.worldObj.isRemote) {
            hitAnimationTicks = 20;
            return false;
        }
        super.attackEntityFrom(damageSource, amount);
        float adjDmg = upgradeHelper.getScaledDamage(damageSource, amount);
        this.setHealth(getHealth() - adjDmg);
        if (getHealth() <= 0) {
            setDead();
            return false;
        }
        return true;
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if (entity != getControllingPassenger() && !(entity instanceof NpcBase))//skip if it if it is the rider
        {
            double xDiff = entity.posX - this.posX;
            double zDiff = entity.posZ - this.posZ;
            double entityDistance = MathHelper.abs_max(xDiff, zDiff);

            if (entityDistance >= 0.009999999776482582D) {
                entityDistance = Math.sqrt(entityDistance);
                xDiff /= entityDistance;
                zDiff /= entityDistance;
                double normalizeToDistance = 1.0D / entityDistance;

                if (normalizeToDistance > 1.0D) {
                    normalizeToDistance = 1.0D;
                }

                xDiff *= normalizeToDistance;
                zDiff *= normalizeToDistance;
                xDiff *= 0.05000000074505806D;//wtf..normalize to ticks?
                zDiff *= 0.05000000074505806D;
                xDiff *= (double) (1.0F - this.entityCollisionReduction);
                zDiff *= (double) (1.0F - this.entityCollisionReduction);
                this.addVelocity(-xDiff, 0.0D, -zDiff);
                entity.addVelocity(xDiff, 0.0D, zDiff);
            }
        }
    }

    public ResourceLocation getTexture() {
        return vehicleType.getTextureForMaterialLevel(vehicleMaterialLevel);
    }

    @Override
    public void updateRiderPosition() {
        double posX = this.posX;
        double posY = this.posY + this.getRiderVerticalOffset();
        double posZ = this.posZ;
        Entity passenger = this.ridingEntity;

        float yaw = this.vehicleType.moveRiderWithTurret() ? localTurretRotation : rotationYaw;
        posX += Trig.sinDegrees(yaw) * -this.getRiderForwardOffset();
        posX += Trig.sinDegrees(yaw + 90) * this.getRiderHorizontalOffset();
        posZ += Trig.cosDegrees(yaw) * -this.getRiderForwardOffset();
        posZ += Trig.cosDegrees(yaw + 90) * this.getRiderHorizontalOffset();
        if (vehicleType.shouldRiderSit()) {
            passenger.height = 1.3f;
        }
        if (passenger instanceof NpcBase) {
            passenger.setPositionAndRotation(posX, posY + passenger.getYOffset(), posZ, 180 - localTurretRotation, passenger.rotationPitch);
            passenger.setRenderYawOffset(180 - localTurretRotation);
        } else {
            passenger.setPosition(posX, posY + passenger.getYOffset(), posZ);
            passenger.rotationYaw -= this.moveHelper.getRotationSpeed();
        }
        if (vehicleType.shouldRiderSit()) {
            passenger.setEntityBoundingBox(passenger.getEntityBoundingBox().offset(0, 0.6, 0));
        }
    }

//    @Override
//    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
//        if (this.isSettingUp) {
//            if (!player.worldObj.isRemote) {
//                player.sendMessage(new TextComponentString("Vehicle is currently being set-up.  It has " + setupTicks + " ticks remaining."));
//            }
//            return false;
//        }
//        return this.firingVarsHelper.interact(player);
//    }

    @Override
    public String toString() {
        return String.format("%s::%s @ %.2f, %.2f, %.2f  -- y:%.2f p:%.2f -- m: %.2f, %.2f, %.2f", this.vehicleType.getDisplayName(), this.getEntityId(), this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.motionX, this.motionY, this.motionZ);
    }

    @Override
    public void addVelocity(double par1, double par3, double par5) {
        super.addVelocity(par1, par3, par5);
    }

    @Override
    public void setVelocity(double par1, double par3, double par5) {

    }

    @Override
    public boolean shouldRiderSit() {
        return this.vehicleType.shouldRiderSit();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        PacketBuffer pb = new PacketBuffer(buffer);
        pb.writeFloat(this.getHealth());
        pb.writeInt(this.vehicleType.getGlobalVehicleType());
        pb.writeInt(this.vehicleMaterialLevel);
        pb.writeCompoundTag(upgradeHelper.serializeNBT());
        pb.writeCompoundTag(ammoHelper.serializeNBT());
        pb.writeCompoundTag(moveHelper.serializeNBT());
        pb.writeCompoundTag(firingHelper.serializeNBT());
        pb.writeCompoundTag(firingVarsHelper.serializeNBT());
        pb.writeFloat(localLaunchPower);
        pb.writeFloat(localTurretPitch);
        pb.writeFloat(localTurretRotation);
        pb.writeFloat(localTurretDestPitch);
        pb.writeFloat(localTurretDestRot);
        owner.serializeToBuffer(buffer);
        pb.writeFloat(localTurretRotationHome);
        pb.writeBoolean(this.isSettingUp);
        if (this.isSettingUp) {
            pb.writeInt(this.setupTicks);
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        PacketBuffer pb = new PacketBuffer(additionalData);
        this.setHealth(pb.readFloat());
        IVehicleType type = VehicleType.getVehicleType(pb.readInt());
        this.setVehicleType(type, pb.readInt());
        try {
            this.upgradeHelper.deserializeNBT(pb.readCompoundTag());
            this.ammoHelper.deserializeNBT(pb.readCompoundTag());
            this.moveHelper.deserializeNBT(pb.readCompoundTag());
            this.firingHelper.deserializeNBT(pb.readCompoundTag());
            this.firingVarsHelper.deserializeNBT(pb.readCompoundTag());
        }
        catch (IOException e) {
            AncientWarfareVehicles.LOG.error(e);
        }
        this.localLaunchPower = pb.readFloat();
        this.localTurretPitch = pb.readFloat();
        this.localTurretRotation = pb.readFloat();
        this.localTurretDestPitch = pb.readFloat();
        this.localTurretDestRot = pb.readFloat();
        this.firingHelper.clientLaunchSpeed = localLaunchPower;
        this.firingHelper.clientTurretPitch = localTurretPitch;
        this.firingHelper.clientTurretYaw = localTurretRotation;
        this.upgradeHelper.updateUpgradeStats();
        owner = new Owner(additionalData);
        this.localTurretRotationHome = pb.readFloat();
        this.isSettingUp = pb.readBoolean();
        if (this.isSettingUp) {
            this.setupTicks = pb.readInt();
        }
        this.setPosition(posX, posY, posZ);//this is to reset the bounding box, because the size of the entity changed during vehicleType setup
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        IVehicleType vehType = VehicleType.getVehicleType(tag.getInteger("vehType"));
        int level = tag.getInteger("matLvl");
        this.setVehicleType(vehType, level);
        this.setHealth(tag.getFloat("health"));
        this.localTurretRotationHome = tag.getFloat("turHome");
        this.inventory.readFromNBT(tag);
        this.upgradeHelper.deserializeNBT(tag.getCompoundTag("upgrades"));
        this.ammoHelper.deserializeNBT(tag.getCompoundTag("ammo"));
        this.moveHelper.deserializeNBT(tag.getCompoundTag("move"));
        this.firingHelper.deserializeNBT(tag.getCompoundTag("fire"));
        this.firingVarsHelper.deserializeNBT(tag.getCompoundTag("vars"));
        this.localLaunchPower = tag.getFloat("lc");
        this.localTurretPitch = tag.getFloat("tp");
        this.localTurretDestPitch = tag.getFloat("tpd");
        this.localTurretRotation = tag.getFloat("tr");
        this.localTurretDestRot = tag.getFloat("trd");
        this.upgradeHelper.updateUpgrades();
        this.ammoHelper.updateAmmoCounts();
        owner = Owner.deserializeFromNBT(tag);
        this.isSettingUp = tag.getBoolean("setup");
        if (this.isSettingUp) {
            this.setupTicks = tag.getInteger("sTick");
        }
        this.setPosition(posX, posY, posZ);//this is to reset the bounding box, because the size of the entity changed during vehicleType setup
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setInteger("vehType", this.vehicleType.getGlobalVehicleType());
        tag.setInteger("matLvl", this.vehicleMaterialLevel);
        tag.setFloat("health", this.getHealth());
        tag.setFloat("turHome", this.localTurretRotationHome);
        this.inventory.writeToNBT(tag);//yah..I wrote this one a long time ago, is why it is different.....
        tag.setTag("upgrades", this.upgradeHelper.serializeNBT());
        tag.setTag("ammo", this.ammoHelper.serializeNBT());
        tag.setTag("move", this.moveHelper.serializeNBT());
        tag.setTag("fire", this.firingHelper.serializeNBT());
        tag.setTag("vars", this.firingVarsHelper.serializeNBT());
        tag.setFloat("lc", localLaunchPower);
        tag.setFloat("tp", localTurretPitch);
        tag.setFloat("tpd", localTurretDestPitch);
        tag.setFloat("tr", localTurretRotation);
        tag.setFloat("trd", localTurretDestRot);
        owner.serializeToNBT(tag);
        tag.setBoolean("setup", this.isSettingUp);
        if (this.isSettingUp) {
            tag.setInteger("sTick", this.setupTicks);
        }
    }

    /**
     * missile callback methods...
     */
    @Override
    public void onMissileImpact(World world, double x, double y, double z) {
        if (ridingEntity instanceof IMissileHitCallback) {
            ((IMissileHitCallback) ridingEntity).onMissileImpact(world, x, y, z);
        }
    }

    @Override
    public void onMissileImpactEntity(World world, Entity entity) {
        if (ridingEntity instanceof IMissileHitCallback) {
            ((IMissileHitCallback) ridingEntity).onMissileImpactEntity(world, entity);
        }
    }

    @Override
    public void setMoveTo(double x, double y, double z, float moveSpeed) {
        this.moveHelper.setMoveTo(x, y, z);
    }

    @Override
    public boolean isPathableEntityOnLadder() {
        return false;
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public void setPath(List<Node> path) {
        this.nav.forcePath(path);
    }

    public void clearPath() {
        this.nav.clearPath();
    }

    @Override
    public float getDefaultMoveSpeed() {
        return this.currentForwardSpeedMax;
    }

    @Override
    public void onStuckDetected() {
        if (getControllingPassenger() instanceof NpcBase) {
            ((NpcBase) getControllingPassenger()).onStuckDetected();
        }
    }

//    @Nullable todo: doesn't exist in 1.7
//    @Override
//    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
//        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            return (T) inventory.storageInventory;
//        }
//        return super.getCapability(capability, facing);
//    }

    @Override
    public void setOwner(EntityPlayer player) {
        owner = new Owner(player);
    }

    @Override
    public String getOwnerName() {
        return "";
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public boolean isOwner(EntityPlayer player) {
        return owner.isOwnerOrSameTeamOrFriend(player);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        // only one rider in 1.7.10
        //getPassengers().forEach(e -> e.setPosition(posX, posY, posZ));
        this.ridingEntity.setPosition(posX, posY, posZ);
        super.writeToNBT(compound);
    }

    //    @Override
//    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
//        //moving passengers to the same position as vehicle on save otherwise they can end up in a different chunk and MC will kill them on load
//        getPassengers().forEach(e -> e.setPosition(posX, posY, posZ));
//        return super.writeToNBT(compound);
//    }

    public boolean isAmmoLoaded() {
        return vehicleType.getValidAmmoTypes().stream().anyMatch(a -> ammoHelper.getCountOf(a) > 0);
    }
}