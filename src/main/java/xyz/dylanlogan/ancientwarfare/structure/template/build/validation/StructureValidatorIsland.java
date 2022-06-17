package xyz.dylanlogan.ancientwarfare.structure.template.build.validation;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.StringTools;
import xyz.dylanlogan.ancientwarfare.structure.block.BlockDataManager;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;
import xyz.dylanlogan.ancientwarfare.structure.world_gen.WorldStructureGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class StructureValidatorIsland extends StructureValidator {

    int minWaterDepth;
    int maxWaterDepth;

    public StructureValidatorIsland() {
        super(StructureValidationType.ISLAND);
    }

    @Override
    protected void readFromLines(List<String> lines) {
        for (String line : lines) {
            if (startLow(line, "minwaterdepth=")) {
                minWaterDepth = StringTools.safeParseInt("=", line);
            } else if (startLow(line, "maxwaterdepth=")) {
                maxWaterDepth = StringTools.safeParseInt("=", line);
            }
        }
    }

    @Override
    protected void write(BufferedWriter out) throws IOException {
        out.write("minWaterDepth=" + minWaterDepth);
        out.newLine();
        out.write("minWaterDepth=" + maxWaterDepth);
        out.newLine();
    }

    @Override
    protected void setDefaultSettings(StructureTemplate template) {
//  this.validTargetBlocks.addAll(WorldStructureGenerator.defaultTargetBlocks);
        this.minWaterDepth = template.yOffset / 2;
        this.maxWaterDepth = template.yOffset;
    }

    @Override
    public boolean shouldIncludeForSelection(World world, int x, int y, int z, int face, StructureTemplate template) {
        int water = 0;
        int startY = y - 1;
        y = WorldStructureGenerator.getTargetY(world, x, z, true) + 1;
        water = startY - y + 1;
        if (water < minWaterDepth || water > maxWaterDepth) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePlacement(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        int minY = y - maxWaterDepth;
        int maxY = y - minWaterDepth;
        return validateBorderBlocks(world, template, bb, minY, maxY, true);
    }

    @Override
    public void preGeneration(World world, BlockPosition pos, int face, StructureTemplate template, StructureBB bb) {
        Block block;
        Set<String> validTargetBlocks = getTargetBlocks();
        for (int bx = bb.min.x; bx <= bb.max.x; bx++) {
            for (int bz = bb.min.z; bz <= bb.max.z; bz++) {
                for (int by = bb.min.y - 1; by > 0; by--) {
                    block = world.getBlock(bx, by, bz);
                    if (block != null && validTargetBlocks.contains(BlockDataManager.INSTANCE.getNameForBlock(block))) {
                        break;
                    } else {
                        world.setBlock(bx, by, bz, Blocks.dirt);
                    }
                }
            }
        }
    }

    @Override
    public void handleClearAction(World world, int x, int y, int z, StructureTemplate template, StructureBB bb) {
        int maxWaterY = bb.min.y + template.yOffset - 1;
        if (y <= maxWaterY) {
            world.setBlock(x, y, z, Blocks.water);
        } else {
            super.handleClearAction(world, x, y, z, template, bb);
        }
    }

}
