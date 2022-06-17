package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRuleEntity;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;
import xyz.dylanlogan.ancientwarfare.structure.gates.types.Gate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuildingException;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuildingException.EntityPlacementException;

import java.util.List;

public class TemplateRuleGates extends TemplateRuleEntity {

    String gateType;
    int orientation;
    BlockPosition pos1 = new BlockPosition();
    BlockPosition pos2 = new BlockPosition();

    /**
     * scanner-constructor.  called when scanning an entity.
     *
     * @param world  the world containing the scanned area
     * @param entity the entity being scanned
     * @param turns  how many 90' turns to rotate entity for storage in template
     * @param x      world x-coord of the enitty (floor(posX)
     * @param y      world y-coord of the enitty (floor(posY)
     * @param z      world z-coord of the enitty (floor(posZ)
     */
    public TemplateRuleGates(World world, Entity entity, int turns, int x, int y, int z) {
        super(world, entity, turns, x, y, z);
        EntityGate gate = (EntityGate) entity;

        this.pos1 = BlockTools.rotateAroundOrigin(gate.pos1.offset(-x, -y, -z), turns);
        this.pos2 = BlockTools.rotateAroundOrigin(gate.pos2.offset(-x, -y, -z), turns);
        this.orientation = (gate.gateOrientation + turns) % 4;
        this.gateType = Gate.getGateNameFor(gate);
    }

    public TemplateRuleGates() {

    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) throws EntityPlacementException {
        BlockPosition p1 = BlockTools.rotateAroundOrigin(pos1, turns).offset(x, y, z);
        BlockPosition p2 = BlockTools.rotateAroundOrigin(pos2, turns).offset(x, y, z);

        BlockPosition min = BlockTools.getMin(p1, p2);
        BlockPosition max = BlockTools.getMax(p1, p2);
        for (int x1 = min.x; x1 <= max.x; x1++) {
            for (int y1 = min.y; y1 <= max.y; y1++) {
                for (int z1 = min.z; z1 <= max.z; z1++) {
                    world.setBlock(x1, y1, z1, Blocks.air);
                }
            }
        }

        EntityGate gate = Gate.constructGate(world, p1, p2, Gate.getGateByName(gateType), (byte) ((orientation + turns) % 4));
        if (gate == null) {
            throw new StructureBuildingException.EntityPlacementException("Could not create gate for type: " + gateType);
        }
        world.spawnEntityInWorld(gate);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        gateType = tag.getString("gateType");
        orientation = tag.getByte("orientation");
        pos1 = new BlockPosition(tag.getCompoundTag("pos1"));
        pos2 = new BlockPosition(tag.getCompoundTag("pos2"));
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        tag.setString("gateType", gateType);
        tag.setByte("orientation", (byte) orientation);
        tag.setTag("pos1", pos1.writeToNBT(new NBTTagCompound()));
        tag.setTag("pos2", pos2.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void addResources(List<ItemStack> resources) {
        resources.add(Gate.getItemToConstruct(Gate.getGateByName(gateType).getGlobalID()));
    }

    @Override
    public boolean shouldPlaceOnBuildPass(World world, int turns, int x, int y, int z, int buildPass) {
        return buildPass == 3;
    }

}
