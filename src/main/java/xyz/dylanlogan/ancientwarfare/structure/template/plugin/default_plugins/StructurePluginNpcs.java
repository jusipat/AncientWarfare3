package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import xyz.dylanlogan.ancientwarfare.npc.block.AWNPCBlockLoader;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.api.StructureContentPlugin;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleBlockLogic;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleEntityNpc;

public class StructurePluginNpcs implements StructureContentPlugin {

    public StructurePluginNpcs() {

    }

    @Override
    public void addHandledBlocks(IStructurePluginManager manager) {
        manager.registerBlockHandler("awTownHall", AWNPCBlockLoader.townHall, TemplateRuleBlockLogic.class);
    }

    @Override
    public void addHandledEntities(IStructurePluginManager manager) {
        for (Object obj : EntityList.classToStringMapping.keySet()) {
            Class<? extends Entity> clazz = (Class<? extends Entity>) obj;
            if (NpcBase.class.isAssignableFrom(clazz)) {
                manager.registerEntityHandler("AWNpc", clazz, TemplateRuleEntityNpc.class);
            }
        }
    }

}
