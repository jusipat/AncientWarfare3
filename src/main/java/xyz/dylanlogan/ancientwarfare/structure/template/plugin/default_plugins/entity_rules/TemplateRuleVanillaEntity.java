package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRuleEntity;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuildingException.EntityPlacementException;

import java.util.List;

public class TemplateRuleVanillaEntity extends TemplateRuleEntity {

    public String mobID;
    public float xOffset;
    public float zOffset;
    public float rotation;

    public TemplateRuleVanillaEntity(World world, Entity entity, int turns, int x, int y, int z) {
        this.mobID = EntityList.getEntityString(entity);
        rotation = (entity.rotationYaw + 90.f * turns) % 360.f;
        float x1, z1;
        x1 = (float) (entity.posX % 1.d);
        z1 = (float) (entity.posZ % 1.d);
        if (x1 < 0) {
            x1++;
        }
        if (z1 < 0) {
            z1++;
        }
        xOffset = BlockTools.rotateFloatX(x1, z1, turns);
        zOffset = BlockTools.rotateFloatZ(x1, z1, turns);
    }

    public TemplateRuleVanillaEntity() {

    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) throws EntityPlacementException {
        Entity e = EntityList.createEntityByName(mobID, world);
        if (e == null) {
            throw new EntityPlacementException("Could not create entity for type: " + mobID);
        }
        float x1 = BlockTools.rotateFloatX(xOffset, zOffset, turns);
        float z1 = BlockTools.rotateFloatZ(xOffset, zOffset, turns);
        float yaw = (rotation + 90.f * turns) % 360.f;
        e.setPosition(x + x1, y, z + z1);
        e.rotationYaw = yaw;
        world.spawnEntityInWorld(e);
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        tag.setString("mobID", mobID);
        tag.setFloat("xOffset", xOffset);
        tag.setFloat("zOffset", zOffset);
        tag.setFloat("rotation", rotation);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        mobID = tag.getString("mobID");
        xOffset = tag.getFloat("xOffset");
        zOffset = tag.getFloat("zOffset");
        rotation = tag.getFloat("rotation");
    }

    @Override
    public boolean shouldPlaceOnBuildPass(World world, int turns, int x, int y, int z, int buildPass) {
        return buildPass == 3;
    }

    @Override
    public void addResources(List<ItemStack> resources) {

    }

}
