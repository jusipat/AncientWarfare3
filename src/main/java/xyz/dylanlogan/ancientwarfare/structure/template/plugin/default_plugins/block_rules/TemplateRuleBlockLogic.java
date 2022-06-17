package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;

public class TemplateRuleBlockLogic extends TemplateRuleVanillaBlocks {

    public NBTTagCompound tag = new NBTTagCompound();

    public TemplateRuleBlockLogic(World world, int x, int y, int z, Block block, int meta, int turns) {
        super(world, x, y, z, block, meta, turns);
        TileEntity te = world.getTileEntity(x, y, z);
        if(te!=null) {
            te.writeToNBT(tag);
            tag.removeTag("x");
            tag.removeTag("y");
            tag.removeTag("z");
        }
    }

    public TemplateRuleBlockLogic() {
    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) {
        super.handlePlacement(world, turns, x, y, z, builder);
        int localMeta = BlockDataManager.INSTANCE.getRotatedMeta(block, this.meta, turns);
        world.setBlockMetadataWithNotify(x, y, z, localMeta, 3);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            tag.setInteger("x", x);
            tag.setInteger("y", y);
            tag.setInteger("z", z);
            te.readFromNBT(tag);
        }
    }

    @Override
    public boolean shouldReuseRule(World world, Block block, int meta, int turns, int x, int y, int z) {
        return false;
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        super.writeRuleData(tag);
        tag.setTag("teData", this.tag);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        super.parseRuleData(tag);
        this.tag = tag.getCompoundTag("teData");
    }
}
