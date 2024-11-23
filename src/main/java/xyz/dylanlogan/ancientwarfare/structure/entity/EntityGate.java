package xyz.dylanlogan.ancientwarfare.structure.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.*;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IEntityPacketHandler;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.network.PacketEntity;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.gates.types.Gate;
import xyz.dylanlogan.ancientwarfare.structure.gates.types.GateRotatingBridge;

import java.io.IOException;

/**
 * an class to represent ALL gate types
 *
 * 
 */
public class EntityGate extends Entity implements IEntityAdditionalSpawnData, IEntityPacketHandler {

    public BlockPosition pos1;
    public BlockPosition pos2;

    public float edgePosition;//the bottom/opening edge of the gate (closed should correspond to pos1)
    public float edgeMax;//the 'fully extended' position of the gate

    public float openingSpeed = 0.f;//calculated speed of the opening gate -- used during animation

    Gate gateType = Gate.getGateByID(0);

    String ownerName;
    int health = 0;
    public int hurtAnimationTicks = 0;
    byte gateStatus = 0;
    public byte gateOrientation = 0;
    public int hurtInvulTicks = 0;

    boolean hasSetWorldEntityRadius = false;
    public boolean wasPoweredA = false;
    public boolean wasPoweredB = false;

    public EntityGate(World par1World) {
        super(par1World);
        this.yOffset = 0;
        this.ignoreFrustumCheck = true;
        this.preventEntitySpawning = true;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
    }

    public Team getTeam() {
        return worldObj.getScoreboard().getPlayersTeam(ownerName);
    }

    public Gate getGateType() {
        return this.gateType;
    }

    public void setGateType(Gate type) {
        this.gateType = type;
        setHealth(type.getMaxHealth());
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target){
        return Gate.getItemToConstruct(this.gateType.getGlobalID());
    }

    public void repackEntity() {
        if (worldObj.isRemote || isDead) {
            return;
        }
        gateType.onGateStartOpen(this);//catch gates that have proxy blocks still in the world
        gateType.onGateStartClose(this);//
        ItemStack item = Gate.getItemToConstruct(this.gateType.getGlobalID());
        EntityItem entity = new EntityItem(worldObj, posX, posY + 0.5d, posZ, item);
        this.worldObj.spawnEntityInWorld(entity);
        this.setDead();
    }

    @Override
    public void setDead() {
        super.setDead();
        if (!this.worldObj.isRemote) {
            //catch gates that have proxy blocks still in the world
            gateType.onGateStartOpen(this);
            gateType.onGateStartClose(this);
        }
    }

    protected void setOpeningStatus(byte op) {
        this.gateStatus = op;
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, op);
        }
        if (op == -1) {
            this.gateType.onGateStartClose(this);
        } else if (op == 1) {
            this.gateType.onGateStartOpen(this);
        }
    }

    @Override
    public int getBrightnessForRender(float par1) {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posZ);
        int k = MathHelper.floor_double(this.posY);
        if(pos1.y > k)
            k = pos1.y;
        if(pos2.y > k)
            k = pos2.y;
        return this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (worldObj.isRemote) {
            if (par1 == -1 || par1 == 0 || par1 == 1) {
                this.setOpeningStatus(par1);
            }
        }
        super.handleHealthUpdate(par1);
    }

    public boolean isClosed(){
        return gateStatus == 0 && edgePosition == 0;
    }

    public byte getOpeningStatus() {
        return this.gateStatus;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int val) {
        if (val < 0) {
            val = 0;
        }
        if (val < health) {
            this.hurtAnimationTicks = 20;
        }
        if (val < health && !this.worldObj.isRemote) {
            PacketEntity pkt = new PacketEntity(this);
            pkt.packetData.setInteger("health", val);
            NetworkHandler.sendToAllTracking(this, pkt);
        }
        this.health = val;
    }

    @Override
    public void setPosition(double par1, double par3, double par5) {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        if (this.gateType != null) {
            this.gateType.setCollisionBoundingBox(this);
        }
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.worldObj.isRemote) {
            return true;
        }
        boolean canInteract = par1EntityPlayer.getCommandSenderName().equals(ownerName) || par1EntityPlayer.getTeam()!=null && par1EntityPlayer.getTeam().isSameTeam(this.getTeam());
        if(canInteract){
            if (par1EntityPlayer.isSneaking()) {
                NetworkHandler.INSTANCE.openGui(par1EntityPlayer, NetworkHandler.GUI_GATE_CONTROL, getEntityId(), 0, 0);
            } else {
                this.activateGate();
            }
            return true;
        } else {
            par1EntityPlayer.addChatMessage(new ChatComponentTranslation("guistrings.gate.use_error"));
        }
        return false;
    }

    public void activateGate() {
        if (this.gateStatus == 1 && this.gateType.canActivate(this, false)) {
            this.setOpeningStatus((byte) -1);
        } else if (this.gateStatus == -1 && this.gateType.canActivate(this, true)) {
            this.setOpeningStatus((byte) 1);
        } else if (this.edgePosition == 0 && this.gateType.canActivate(this, true)) {
            this.setOpeningStatus((byte) 1);
        } else if (this.gateType.canActivate(this, false))//gate is already open/opening, set to closing
        {
            this.setOpeningStatus((byte) -1);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.gateType.onUpdate(this);
        float prevEdge = this.edgePosition;
        this.setPosition(posX, posY, posZ);
        if (this.hurtInvulTicks > 0) {
            this.hurtInvulTicks--;
        }
        this.checkForPowerUpdates();
        if (this.hurtAnimationTicks > 0) {
            this.hurtAnimationTicks--;
        }
        if (this.gateStatus == 1) {
            this.edgePosition += this.gateType.getMoveSpeed();
            if (this.edgePosition >= this.edgeMax) {
                this.edgePosition = this.edgeMax;
                this.gateStatus = 0;
                this.gateType.onGateFinishOpen(this);
            }
        } else if (this.gateStatus == -1) {
            this.edgePosition -= this.gateType.getMoveSpeed();
            if (this.edgePosition <= 0) {
                this.edgePosition = 0;
                this.gateStatus = 0;
                this.gateType.onGateFinishClose(this);
            }
        }
        this.openingSpeed = prevEdge - this.edgePosition;

        if (!hasSetWorldEntityRadius) {
            hasSetWorldEntityRadius = true;
            BlockPosition min = BlockTools.getMin(pos1, pos2);
            BlockPosition max = BlockTools.getMax(pos1, pos2);
            int xSize = max.x - min.x + 1;
            int zSize = max.z - min.z + 1;
            int ySize = max.y - min.y + 1;
            int largest = xSize > ySize ? xSize : ySize;
            largest = largest > zSize ? largest : zSize;
            largest = (largest / 2) + 1;
            if (World.MAX_ENTITY_RADIUS < largest) {
                World.MAX_ENTITY_RADIUS = largest;
            }
        }
    }

    protected void checkForPowerUpdates() {
        if (this.worldObj.isRemote) {
            return;
        }
        boolean activate = false;
        int y = pos2.y < pos1.y ? pos2.y : pos1.y;
        boolean foundPowerA = this.worldObj.isBlockIndirectlyGettingPowered(pos1.x, y, pos1.z);
        boolean foundPowerB = this.worldObj.isBlockIndirectlyGettingPowered(pos2.x, y, pos2.z);
        if (foundPowerA && !wasPoweredA) {
            activate = true;
        }
        if (foundPowerB && !wasPoweredB) {
            activate = true;
        }
        this.wasPoweredA = foundPowerA;
        this.wasPoweredB = foundPowerB;
        if (activate) {
            this.activateGate();
        }
    }

    private boolean isInsensitiveTo(DamageSource source){
        return source == null || source == DamageSource.anvil || source == DamageSource.cactus || source == DamageSource.drown || source == DamageSource.fall || source == DamageSource.fallingBlock || source == DamageSource.inWall || source == DamageSource.starve;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if(isInsensitiveTo(par1DamageSource) || par2 < 0){
            return false;
        }
        if (this.worldObj.isRemote) {
            return true;
        }
//  if(Config.gatesOnlyDamageByRams)
//    {
//    if(par1DamageSource.getEntity()==null || !(par1DamageSource.getEntity() instanceof VehicleBase))  
//      {
//      return !this.isDead;
//      }
//    VehicleBase vehicle = (VehicleBase) par1DamageSource.getEntity();
//    if(vehicle.vehicleType.getGlobalVehicleType()!=VehicleRegistry.BATTERING_RAM.getGlobalVehicleType())
//      {
//      return !this.isDead;
//      }
//    }
        if(!par1DamageSource.isExplosion()){
            if(this.hurtInvulTicks > 0) {
                return false;
            }
            this.hurtInvulTicks = 10;
        }
        int health = this.getHealth();
        health -= par2;
        this.setHealth(health);

        if (health <= 0) {
            this.setDead();
        }
        return !this.isDead;
    }

//Prevent moving from external means
    @Override
    public void travelToDimension(int dimension){

    }

    @Override
    public boolean handleWaterMovement(){
        return false;
    }

    @Override
    public void moveFlying(float moveX, float moveZ, float factor){

    }

    @Override
    public void addVelocity(double moveX, double moveY, double moveZ){

    }

//Interaction, collision handling
    @Override
    public AxisAlignedBB getBoundingBox(){
        return this.boundingBox;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return -0.1F;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void applyEntityCollision(Entity entity){
        super.applyEntityCollision(entity);
        if(isInside(entity))
            entity.addVelocity(0, -gateStatus*0.5, 0);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entity) {
        if(isInside(entity))
            entity.addVelocity(0, -gateStatus*0.5, 0);
    }

    private boolean isInside(Entity entity){
        return gateType instanceof GateRotatingBridge && boundingBox.intersectsWith(entity.boundingBox);
    }

    @Override
    public void mountEntity(Entity mont){

    }

//Rendering
    public String getTexture() {
        return "textures/models/gate/" + gateType.getTexture();
    }

    @Override
    public float getShadowSize() {
        return 0.f;
    }

//Data
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        this.pos1 = new BlockPosition(tag.getCompoundTag("pos1"));
        this.pos2 = new BlockPosition(tag.getCompoundTag("pos2"));
        this.setGateType(Gate.getGateByID(tag.getInteger("type")));
        this.ownerName = tag.getString("owner");
        this.edgePosition = tag.getFloat("edge");
        this.edgeMax = tag.getFloat("edgeMax");
        this.setHealth(tag.getInteger("health"));
        this.gateStatus = tag.getByte("status");
        this.gateOrientation = tag.getByte("orient");
        this.wasPoweredA = tag.getBoolean("power");
        this.wasPoweredB = tag.getBoolean("power2");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setTag("pos1", pos1.writeToNBT(new NBTTagCompound()));
        tag.setTag("pos2", pos2.writeToNBT(new NBTTagCompound()));
        tag.setInteger("type", this.gateType.getGlobalID());
        if (ownerName != null && !ownerName.isEmpty()) {
            tag.setString("owner", ownerName);
        }
        tag.setFloat("edge", this.edgePosition);
        tag.setFloat("edgeMax", this.edgeMax);
        tag.setInteger("health", this.getHealth());
        tag.setByte("status", this.gateStatus);
        tag.setByte("orient", gateOrientation);
        tag.setBoolean("power", this.wasPoweredA);
        tag.setBoolean("power2", this.wasPoweredB);
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(pos1.x);
        data.writeInt(pos1.y);
        data.writeInt(pos1.z);
        data.writeInt(pos2.x);
        data.writeInt(pos2.y);
        data.writeInt(pos2.z);
        data.writeInt(this.gateType.getGlobalID());
        data.writeFloat(this.edgePosition);
        data.writeFloat(this.edgeMax);
        data.writeByte(this.gateStatus);
        data.writeByte(this.gateOrientation);
        data.writeInt(health);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        this.pos1 = new BlockPosition(data.readInt(), data.readInt(), data.readInt());
        this.pos2 = new BlockPosition(data.readInt(), data.readInt(), data.readInt());
        this.gateType = Gate.getGateByID(data.readInt());
        this.edgePosition = data.readFloat();
        this.edgeMax = data.readFloat();
        this.gateStatus = data.readByte();
        this.gateOrientation = data.readByte();
        this.health = data.readInt();
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (tag.hasKey("health")) {
            this.health = tag.getInteger("health");
            this.hurtAnimationTicks = 20;
        }
    }

}
