package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins;

import xyz.dylanlogan.ancientwarfare.automation.block.AWAutomationBlockLoader;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.api.StructureContentPlugin;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleAutomationLogic;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleRotable;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleTorqueMultiblock;

public class StructurePluginAutomation implements StructureContentPlugin {


    public StructurePluginAutomation() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addHandledBlocks(IStructurePluginManager manager) {
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteCropFarm, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteAnimalFarm, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteFishFarm, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteForestry, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteMushroomFarm, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteReedFarm, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteQuarry, TemplateRuleRotable.class);
        manager.registerBlockHandler("awWorksite", AWAutomationBlockLoader.worksiteWarehouse, TemplateRuleRotable.class);

        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.torqueShaft, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.torqueConduit, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.torqueDistributor, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.flywheel, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.torqueGeneratorSterling, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.torqueGeneratorWaterwheel, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.handCrankedEngine, TemplateRuleRotable.class);
        manager.registerBlockHandler("awTorqueTile", AWAutomationBlockLoader.windmillControl, TemplateRuleRotable.class);

        manager.registerBlockHandler("awTorqueMulti", AWAutomationBlockLoader.flywheelStorage, TemplateRuleTorqueMultiblock.class);
        manager.registerBlockHandler("awTorqueMulti", AWAutomationBlockLoader.windmillBlade, TemplateRuleTorqueMultiblock.class);

        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.warehouseCrafting, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.warehouseInterface, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.warehouseStockViewer, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.warehouseStorageBlock, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.chunkLoaderSimple, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.chunkLoaderDeluxe, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.mailbox, TemplateRuleAutomationLogic.class);
        manager.registerBlockHandler("awAutomationLogic", AWAutomationBlockLoader.worksiteAutoCrafting, TemplateRuleAutomationLogic.class);
    }

    @Override
    public void addHandledEntities(IStructurePluginManager manager) {
        //noop, no entities in automation module
    }

}
