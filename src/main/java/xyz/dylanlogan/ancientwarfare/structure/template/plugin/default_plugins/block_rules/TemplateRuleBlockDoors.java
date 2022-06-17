package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;

import java.util.List;

public class TemplateRuleBlockDoors extends TemplateRuleVanillaBlocks {

    byte sideFlag = 0;
    boolean isTop = false;

    public TemplateRuleBlockDoors(World world, int x, int y, int z, Block block, int meta, int turns) {
        super(world, x, y, z, block, meta, turns);
        if (world.getBlock(x, y + 1, z) == block) {
            sideFlag = (byte) world.getBlockMetadata(x, y + 1, z);
        }
    }

    public TemplateRuleBlockDoors() {
    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) {
        Block block = BlockDataManager.INSTANCE.getBlockForName(blockName);
        int localMeta = BlockDataManager.INSTANCE.getRotatedMeta(block, this.meta, turns);
        if (world.getBlock(x, y - 1, z) != block)//this is the bottom door block, call placeDoor from our block...
        {
            world.setBlock(x, y, z, block, localMeta, 2);
            world.setBlock(x, y + 1, z, block, sideFlag == 0 ? 8 : sideFlag, 2);
        }
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        tag.setString("blockName", blockName);
        tag.setInteger("meta", meta);
        tag.setInteger("buildPass", buildPass);
        tag.setByte("sideFlag", sideFlag);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        this.blockName = tag.getString("blockName");
        this.meta = tag.getInteger("meta");
        this.buildPass = tag.getInteger("buildPass");
        this.sideFlag = tag.getByte("sideFlag");
    }

    @Override
    public boolean shouldReuseRule(World world, Block block, int meta, int turns, int x, int y, int z) {
        Block block1 = world.getBlock(x, y + 1, z);
        return block1 != null && blockName.equals(BlockDataManager.INSTANCE.getNameForBlock(block1)) && world.getBlockMetadata(x, y + 1, z) == sideFlag;
    }

    @Override
    public void addResources(List<ItemStack> resources) {
        if (sideFlag > 0) {
            super.addResources(resources);
        }
    }

}
