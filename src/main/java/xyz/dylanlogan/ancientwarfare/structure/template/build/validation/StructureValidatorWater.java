package xyz.dylanlogan.ancientwarfare.structure.template.build.validation;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;

public class StructureValidatorWater extends StructureValidator {

    public StructureValidatorWater() {
        super(StructureValidationType.WATER);
    }

    @Override
    public boolean shouldIncludeForSelection(World world, int x, int y, int z, int face, StructureTemplate template) {
        Block block = world.getBlock(x, y - 1, z);
        return block == Blocks.water || block == Blocks.flowing_water;
    }

    @Override
    public boolean validatePlacement(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        int minY = getMinY(template, bb);
        return validateBorderBlocks(world, template, bb, 0, minY, true);
    }

    @Override
    public void preGeneration(World world, BlockPosition pos, int face, StructureTemplate template, StructureBB bb) {

    }

    @Override
    public void handleClearAction(World world, int x, int y, int z, StructureTemplate template, StructureBB bb) {
        if (y < bb.min.y + template.yOffset) {
            world.setBlock(x, y, z, Blocks.water);
        } else {
            super.handleClearAction(world, x, y, z, template, bb);
        }
    }
}
