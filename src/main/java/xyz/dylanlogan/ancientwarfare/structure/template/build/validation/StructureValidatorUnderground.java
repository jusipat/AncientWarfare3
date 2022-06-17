package xyz.dylanlogan.ancientwarfare.structure.template.build.validation;

import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.StringTools;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;
import xyz.dylanlogan.ancientwarfare.structure.world_gen.WorldStructureGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class StructureValidatorUnderground extends StructureValidator {

    int minGenerationDepth;
    int maxGenerationDepth;
    int minOverfill;

    public StructureValidatorUnderground() {
        super(StructureValidationType.UNDERGROUND);
    }

    @Override
    protected void readFromLines(List<String> lines) {
        for (String line : lines) {
            if (startLow(line, "mingenerationdepth=")) {
                minGenerationDepth = StringTools.safeParseInt("=", line);
            } else if (startLow(line, "maxgenerationdepth=")) {
                maxGenerationDepth = StringTools.safeParseInt("=", line);
            } else if (startLow(line, "minoverfill=")) {
                minOverfill = StringTools.safeParseInt("=", line);
            }
        }
    }

    @Override
    protected void write(BufferedWriter out) throws IOException {
        out.write("minGenerationDepth=" + minGenerationDepth);
        out.newLine();
        out.write("maxGenerationDepth=" + maxGenerationDepth);
        out.newLine();
        out.write("minOverfill=" + minOverfill);
        out.newLine();
    }

    @Override
    protected void setDefaultSettings(StructureTemplate template) {

    }

    @Override
    public boolean shouldIncludeForSelection(World world, int x, int y, int z, int face, StructureTemplate template) {
        y = WorldStructureGenerator.getTargetY(world, x, z, true);
        int tHeight = (template.ySize - template.yOffset);
        int low = minGenerationDepth + tHeight + minOverfill;
        return y > low;
    }

    @Override
    public int getAdjustedSpawnY(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        y = WorldStructureGenerator.getTargetY(world, x, z, true);
        int range = maxGenerationDepth - minGenerationDepth + 1;
        int tHeight = (template.ySize - template.yOffset);
        return y - minOverfill - world.rand.nextInt(range) - tHeight;
    }

    @Override
    public boolean validatePlacement(World world, int x, int y, int z, int face, StructureTemplate template, StructureBB bb) {
        int minY = bb.min.y + template.yOffset + minOverfill;
        int topBlockY;
        for (int bx = bb.min.x; bx <= bb.max.x; bx++) {
            for (int bz = bb.min.z; bz <= bb.max.z; bz++) {
                topBlockY = WorldStructureGenerator.getTargetY(world, bx, bz, true);
                if (topBlockY <= minY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void preGeneration(World world, BlockPosition pos, int face, StructureTemplate template, StructureBB bb) {
//  /**
//   * TODO remove debug stuff
//   */
//  int by = WorldStructureGenerator.getTargetY(world, x, z, false);
//  for(int cy = by; cy<=by+5; cy++)
//    {
//    world.setBlock(x, cy, z, Block.obsidian.blockID);
//    }
    }

}
