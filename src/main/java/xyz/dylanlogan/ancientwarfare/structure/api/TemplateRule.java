package xyz.dylanlogan.ancientwarfare.structure.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.Json;
import xyz.dylanlogan.ancientwarfare.core.util.JsonTagReader;
import xyz.dylanlogan.ancientwarfare.core.util.JsonTagWriter;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateParsingException.TemplateRuleParsingException;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuildingException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * base template-rule class.  Plugins should define their own rule classes.
 * all data to place the block/entity/target of the rule must be contained in the rule.
 * ONLY one rule per block-position in the template.  So -- no entity/block combination in same space unless
 * handled specially via a plugin rule
 *
 * 
 */
public abstract class TemplateRule {

    public int ruleNumber = -1;

    /**
     * all sub-classes must implement a no-param constructor for when loaded from file (at which point they should initialize from the parseRuleData method)
     */
    public TemplateRule() {

    }

    /**
     * input params are the target position for placement of this rule and destination orientation
     */
    public abstract void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) throws StructureBuildingException;

    public abstract void parseRuleData(NBTTagCompound tag);

    public abstract void writeRuleData(NBTTagCompound tag);

    public abstract void addResources(List<ItemStack> resources);

    public abstract boolean shouldPlaceOnBuildPass(World world, int turns, int x, int y, int z, int buildPass);

    public void writeRule(BufferedWriter out) throws IOException {
        NBTTagCompound tag = new NBTTagCompound();
        writeRuleData(tag);
        writeTag(out, tag);
    }

    public void parseRule(int ruleNumber, List<String> lines) throws TemplateRuleParsingException {
        this.ruleNumber = ruleNumber;
        NBTTagCompound tag = readTag(lines);
        parseRuleData(tag);
    }

    public final void writeTag(BufferedWriter out, NBTTagCompound tag) throws IOException {
        String line = Json.getJsonData(JsonTagWriter.getJsonForTag(tag));
        out.write(line);
        out.newLine();
    }

    public final NBTTagCompound readTag(List<String> ruleData) throws TemplateRuleParsingException {
        for (String line : ruleData)//new json format
        {
            if (line.startsWith("JSON:{")) {
                return JsonTagReader.parseTagCompound(line);
            }
        }
        for (String line : ruleData)//old json format
        {
            if (line.toLowerCase(Locale.ENGLISH).startsWith("jsontag=")) {
                try {
                    NBTBase tag = JsonToNBT.func_150315_a(line.split("=", -1)[1]);
                    if (tag instanceof NBTTagCompound) {
                        return (NBTTagCompound) tag;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new TemplateRuleParsingException("Caught exception while parsing json-nbt tag: " + line, e);
                }
            }
        }
        //old tag: format
        List<String> tagLines = new ArrayList<String>();
        String line;
        Iterator<String> it = ruleData.iterator();
        while (it.hasNext() && (line = it.next()) != null) {
            if (line.startsWith("tag:")) {
                it.remove();
                while (it.hasNext() && (line = it.next()) != null) {
                    it.remove();
                    if (line.startsWith(":endtag")) {
                        break;
                    }
                    tagLines.add(line);
                }
            }
        }
        return NBTTools.readNBTFrom(tagLines);
    }

    @Override
    public String toString() {
        return "Template rule: " + ruleNumber + " type: " + getClass().getSimpleName();
    }

}
