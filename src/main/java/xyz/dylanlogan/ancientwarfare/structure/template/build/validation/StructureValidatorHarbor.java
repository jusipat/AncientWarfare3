package xyz.dylanlogan.ancientwarfare.structure.template.build.validation;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;
import xyz.dylanlogan.ancientwarfare.structure.world_gen.WorldStructureGenerator;

import java.util.HashSet;
import java.util.Set;

public class StructureValidatorHarbor extends StructureValidator {

    BlockPosition testMin = new BlockPosition();
    BlockPosition testMax = new BlockPosition();

    Set<String> validTargetBlocks;
    Set<String> validTargetBlocksSide;
    Set<String> validTargetBlocksRear;

    public StructureValidatorHarbor() {
        super(StructureValidationType.HARBOR);
        validTargetBlocks = new HashSet<String>();
        validTargetBlocksSide = new HashSet<String>();
        validTargetBlocksRear = new HashSet<String>();
        validTargetBlocks.addAll(WorldStructureGenerator.defaultTargetBlocks);
        validTargetBlocksSide.addAll(WorldStructureGenerator.defaultTargetBlocks);
        validTargetBlocksRear.add(BlockDataManager.INSTANCE.getNameForBlock(Blocks.water));
        validTargetBlocksRear.add(BlockDataManager.INSTANCE.getNameForBlock(Blocks.flowing_water));
        validTargetBlocksSide.add(BlockDataManager.INSTANCE.getNameForBlock(Blocks.water));
        validTargetBlocksSide.add(BlockDataManager.INSTANCE.getNameForBlock(Blocks.flowing_water));
    }

    @Override
    protected void setDefaultSettings(StructureTemplate template) {

    }

    @Override
    public boolean shouldIncludeForSelection(World world, int x, int y, int z, int face, StructureTemplate template) {
        /**
         * testing that front target position is valid block
         * then test back target position to ensure that it has water at same level
         * or at an acceptable level difference
         */
        Block block = world.getBlock(x, y - 1, z);
        if (block != null && validTargetBlocks.contains(BlockDataManager.INSTANCE.getNameForBlock(block))) {
            testMin = new BlockPosition(x, y, z).moveForward(face, template.zOffset);
            int by = WorldStructureGenerator.getTargetY(world, testMin.x, testMin.z, false);
            if (y - by > getMaxFill()) {
                return false;
            }
            block = world.getBlock(testMin.x, by, testMin.z);
            if (block == Blocks.water || block == Blocks.flowing_water) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getAdjustedSpawnY(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        testMin = new BlockPosition(x, y, z).moveForward(face, template.zOffset);
        return WorldStructureGenerator.getTargetY(world, testMin.x, testMin.z, false) + 1;
    }

    @Override
    public boolean validatePlacement(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        int bx, bz;

        int minY = getMinY(template, bb);
        int maxY = getMaxY(template, bb);
        StructureBB temp = bb.getFrontCorners(face, testMin, testMax);
        testMin = temp.min;
        testMax = temp.max;
        for (bx = testMin.x; bx <= testMax.x; bx++) {
            for (bz = testMin.z; bz <= testMax.z; bz++) {
                if (!validateBlockHeightAndType(world, bx, bz, minY, maxY, false, validTargetBlocks)) {
                    return false;
                }
            }
        }

        temp = bb.getRearCorners(face, testMin, testMax);
        testMin = temp.min;
        testMax = temp.max;
        for (bx = testMin.x; bx <= testMax.x; bx++) {
            for (bz = testMin.z; bz <= testMax.z; bz++) {
                if (!validateBlockHeightAndType(world, bx, bz, minY, maxY, false, validTargetBlocksRear)) {
                    return false;
                }
            }
        }

        temp = bb.getRightCorners(face, testMin, testMax);
        testMin = temp.min;
        testMax = temp.max;
        for (bx = testMin.x; bx <= testMax.x; bx++) {
            for (bz = testMin.z; bz <= testMax.z; bz++) {
                if (!validateBlockHeightAndType(world, bx, bz, minY, maxY, false, validTargetBlocksSide)) {
                    return false;
                }
            }
        }

        temp = bb.getLeftCorners(face, testMin, testMax);
        testMin = temp.min;
        testMax = temp.max;
        for (bx = testMin.x; bx <= testMax.x; bx++) {
            for (bz = testMin.z; bz <= testMax.z; bz++) {
                if (!validateBlockHeightAndType(world, bx, bz, minY, maxY, false, validTargetBlocksSide)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void preGeneration(World world, BlockPosition pos, int face, StructureTemplate template, StructureBB bb) {
        prePlacementBorder(world, template, bb);
    }

    @Override
    public void handleClearAction(World world, int x, int y, int z, StructureTemplate template, StructureBB bb) {
        if (y >= bb.min.y + template.yOffset) {
            super.handleClearAction(world, x, y, z, template, bb);
        } else {
            world.setBlock(x, y, z, Blocks.water);
        }
    }

}
