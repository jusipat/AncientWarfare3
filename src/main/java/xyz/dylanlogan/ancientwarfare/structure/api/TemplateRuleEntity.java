package xyz.dylanlogan.ancientwarfare.structure.api;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateParsingException.TemplateRuleParsingException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public abstract class TemplateRuleEntity extends TemplateRule {

    private int x, y, z;

    /**
     * Called by reflection
     * @param world
     * @param entity
     * @param turns
     * @param x
     * @param y
     * @param z
     */
    public TemplateRuleEntity(World world, Entity entity, int turns, int x, int y, int z) {

    }
    /**
     * Called by reflection
     */
    public TemplateRuleEntity() {

    }

    public final void writeRule(BufferedWriter out) throws IOException {
        out.write("position=" + NBTTools.getCSVStringForArray(new int[]{x, y, z}));
        out.newLine();
        super.writeRule(out);
    }

    public final void parseRule(int ruleNumber, List<String> lines) throws TemplateRuleParsingException {
        this.ruleNumber = ruleNumber;
        for (String line : lines) {
            if (line.toLowerCase(Locale.ENGLISH).startsWith("position=")) {
                int[] pos = NBTTools.safeParseIntArray("=", line);
                x = pos[0];
                y = pos[1];
                z = pos[2];
                break;
            }
        }
        NBTTagCompound tag = readTag(lines);
        parseRuleData(tag);
    }

    public final void setPosition(BlockPosition pos){
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public final BlockPosition getPosition(){
        return new BlockPosition(x, y, z);
    }

}
