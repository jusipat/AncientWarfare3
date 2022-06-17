package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructureBuilder;

public class TemplateRuleVanillaSkull extends TemplateRuleBlockLogic {

    int rotation;

    public TemplateRuleVanillaSkull(World world, int x, int y, int z, Block block, int meta, int turns) {
        super(world, x, y, z, block, meta, turns);
        int t = tag.getInteger("Rot");
        AWLog.logDebug("base rot: " + t);
        t += 4 * turns;
        t += 8;//rotate to opposite, no clue why this is needed...
        t %= 16;
        AWLog.logDebug("rotated Rot: " + t);
    }

    public TemplateRuleVanillaSkull() {
    }

    @Override
    public void handlePlacement(World world, int turns, int x, int y, int z, IStructureBuilder builder) {
        tag.setInteger("Rot", (rotation + 4 * turns) % 16);
        AWLog.logDebug("pre-place rot: " + tag.getInteger("Rot"));
        super.handlePlacement(world, turns, x, y, z, builder);
    }

}
