package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;

public class TemplateRuleBlockSign extends TemplateRuleVanillaBlocks {

    public String signContents[];

    public TemplateRuleBlockSign(World world, int x, int y, int z, Block block, int meta, int turns) {
        super(world, x, y, z, block, meta, turns);
        TileEntitySign te = (TileEntitySign) world.getTileEntity(x, y, z);
        signContents = new String[te.signText.length];
        for (int i = 0; i < signContents.length; i++) {
            signContents[i] = te.signText[i];
        }
        if (block == Blocks.standing_sign) {
            this.meta = (meta + 4 * turns) % 16;
        }
    }

    public TemplateRuleBlockSign() {
    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) {
        Block block = Block.getBlockFromName(blockName);
//  Block block = wall? Blocks.wall_sign : Blocks.standing_sign;//BlockDataManager.getBlockByName(blockName);
        int meta = 0;
        if (block == Blocks.standing_sign) {
            meta = (this.meta + 4 * turns) % 16;
        } else {
            meta = BlockDataManager.INSTANCE.getRotatedMeta(block, this.meta, turns);
        }
        if (world.setBlock(x, y, z, block, meta, 2)) {
            TileEntitySign te = (TileEntitySign) world.getTileEntity(x, y, z);
            if (te != null) {
                for (int i = 0; i < this.signContents.length; i++) {
                    te.signText[i] = this.signContents[i];
                }
            }
            world.markBlockForUpdate(x, y, z);
        }
    }

    @Override
    public boolean shouldReuseRule(World world, Block block, int meta, int turns, int x, int y, int z) {
        return false;
    }

    @Override
    public void writeRuleData(NBTTagCompound tag) {
        super.writeRuleData(tag);
        for (int i = 0; i < 4; i++) {
            tag.setString("signContents" + i, signContents[i]);
        }
    }

    @Override
    public void parseRuleData(NBTTagCompound tag) {
        super.parseRuleData(tag);
        this.signContents = new String[4];
        for (int i = 0; i < 4; i++) {
            this.signContents[i] = tag.getString("signContents" + i);
        }
    }

}
