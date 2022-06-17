package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuildingException.EntityPlacementException;

public class TemplateRuleEntityHanging extends TemplateRuleVanillaEntity {

    public NBTTagCompound tag = new NBTTagCompound();
    public int direction;

    BlockPosition hangTarget = new BlockPosition();//cached location for use during placement

    public TemplateRuleEntityHanging(World world, Entity entity, int turns, int x, int y, int z) {
        super(world, entity, turns, x, y, z);
        EntityHanging hanging = (EntityHanging) entity;
        entity.writeToNBT(tag);
        this.direction = (hanging.hangingDirection + turns) % 4;
        tag.removeTag("UUIDMost");
        tag.removeTag("UUIDLeast");
    }

    public TemplateRuleEntityHanging() {

    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) throws EntityPlacementException {
        Entity e = EntityList.createEntityByName(mobID, world);
        if (e == null) {
            throw new EntityPlacementException("Could not create entity for type: " + mobID);
        }
        int direction = (this.direction + turns) % 4;
        hangTarget = new BlockPosition(x, y, z, (direction + 2) % 4);
        tag.setByte("Direction", (byte) direction);
        tag.setInteger("TileX", hangTarget.x);
        tag.setInteger("TileY", hangTarget.y);
        tag.setInteger("TileZ", hangTarget.z);
        e.readFromNBT(tag);
        world.spawnEntityInWorld(e);
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        super.writeRuleData(tag);
        tag.setInteger("direction", direction);
        tag.setTag("entityData", this.tag);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        super.parseRuleData(tag);
        this.tag = tag.getCompoundTag("entityData");
        this.direction = tag.getInteger("direction");
    }

}
