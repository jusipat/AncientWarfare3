package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins;

import xyz.dylanlogan.ancientwarfare.structure.api.IStructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.api.StructureContentPlugin;

public class StructurePluginVehicles implements StructureContentPlugin {

    public StructurePluginVehicles() {

    }

    @Override
    public void addHandledBlocks(IStructurePluginManager manager) {

    }

    @Override
    public void addHandledEntities(IStructurePluginManager manager) {
        //manager.registerEntityHandler("ancientWarfareVehicle", EntityVehicle.class, TemplateRuleEntityLogic.class);
    }
}
