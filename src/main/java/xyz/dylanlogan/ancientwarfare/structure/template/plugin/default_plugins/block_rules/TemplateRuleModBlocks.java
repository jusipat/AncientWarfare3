package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRuleBlock;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;

import java.util.List;

public class TemplateRuleModBlocks extends TemplateRuleBlock {

    public String blockName;
    public int meta;

    public TemplateRuleModBlocks(World world, int x, int y, int z, Block block, int meta, int turns) {
        super(world, x, y, z, block, meta, turns);
        this.blockName = BlockDataManager.INSTANCE.getNameForBlock(block);
        this.meta = meta;
    }

    public TemplateRuleModBlocks() {

    }

    @Override
    public boolean shouldReuseRule(World world, Block block, int meta, int turns, int x, int y, int z) {
        return BlockDataManager.INSTANCE.getNameForBlock(block).equals(blockName) && meta == this.meta;
    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) {
        Block block = BlockDataManager.INSTANCE.getBlockForName(blockName);
        world.setBlock(x, y, z, block, meta, 3);
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        tag.setString("blockName", blockName);
        tag.setInteger("meta", meta);
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        blockName = tag.getString("blockName");
        meta = tag.getInteger("meta");
    }

    @Override
    public void addResources(List<ItemStack> resources) {
        /**
         * TODO
         */
    }

    @Override
    public boolean shouldPlaceOnBuildPass(World world, int turns, int x, int y, int z, int buildPass) {
        return buildPass == 0;
    }

}
