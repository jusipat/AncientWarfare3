package xyz.dylanlogan.ancientwarfare.structure.template.build.validation;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;
import xyz.dylanlogan.ancientwarfare.structure.config.AWStructureStatics;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;
import xyz.dylanlogan.ancientwarfare.structure.world_gen.WorldStructureGenerator;

import java.util.Set;

public class StructureValidatorGround extends StructureValidator {

    public StructureValidatorGround() {
        super(StructureValidationType.GROUND);
    }

    @Override
    public boolean shouldIncludeForSelection(World world, int x, int y, int z, int face, StructureTemplate template) {
        Block block = world.getBlock(x, y - 1, z);
        Set<String> validTargetBlocks = getTargetBlocks();
        String name = BlockDataManager.INSTANCE.getNameForBlock(block);
        if (block == null || !validTargetBlocks.contains(name)) {
            AWLog.logDebug("Rejecting due to target block mismatch of: " + name + " at: " + x + "," + y + "," + z + " Valid blocks are: " + validTargetBlocks);
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePlacement(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        int minY = getMinY(template, bb);
        int maxY = getMaxY(template, bb);
        return validateBorderBlocks(world, template, bb, minY, maxY, false);
    }

    @Override
    public void preGeneration(World world, BlockPosition pos, int face, StructureTemplate template, StructureBB bb) {
        prePlacementBorder(world, template, bb);
        prePlacementUnderfill(world, template, bb);
    }

    @Override
    public void postGeneration(World world, BlockPosition origin, StructureBB bb) {
        BiomeGenBase biome = world.getBiomeGenForCoords(origin.x, origin.z);
        if (biome != null && biome.getEnableSnow()) {
            WorldStructureGenerator.sprinkleSnow(world, bb, getBorderSize());
        }
    }

    @Override
    protected void borderLeveling(World world, int x, int z, StructureTemplate template, StructureBB bb) {
        if (getMaxLeveling() <= 0) {
            return;
        }
        int topFilledY = WorldStructureGenerator.getTargetY(world, x, z, true);
        int step = WorldStructureGenerator.getStepNumber(x, z, bb.min.x, bb.max.x, bb.min.z, bb.max.z);
        for (int y = bb.min.y + template.yOffset + step; y <= topFilledY; y++) {
            handleClearAction(world, x, y, z, template, bb);
        }
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        Block fillBlock = Blocks.grass;
        if (biome != null && biome.topBlock != null) {
            fillBlock = biome.topBlock;
        }
        int y = bb.min.y + template.yOffset + step - 1;
        Block block = world.getBlock(x, y, z);
        if (block != null && block != Blocks.air && block != Blocks.flowing_water && block != Blocks.water && !AWStructureStatics.skippableBlocksContains(block)) {
            world.setBlock(x, y, z, fillBlock);
        }

        int skipCount = 0;
        for (int y1 = y + 1; y1 < world.getHeight(); y1++)//lazy clear block handling
        {
            block = world.getBlock(x, y1, z);
            if (block == Blocks.air) {
                skipCount++;
                if (skipCount >= 10)//exit out if 10 blocks are found that are not clearable
                {
                    break;
                }
                continue;
            }
            skipCount = 0;//if we didn't skip this block, reset skipped count
            if (AWStructureStatics.skippableBlocksContains(block)) {
                world.setBlockToAir(x, y1, z);
            }
        }
    }

}
