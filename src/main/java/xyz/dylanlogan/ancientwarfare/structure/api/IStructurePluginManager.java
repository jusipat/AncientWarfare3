package xyz.dylanlogan.ancientwarfare.structure.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public interface IStructurePluginManager extends IStructurePluginRegister {

    public void registerEntityHandler(String pluginName, Class<? extends Entity> entityClass, Class<? extends TemplateRuleEntity> ruleClass);

    public void registerBlockHandler(String pluginName, Block block, Class<? extends TemplateRuleBlock> ruleClass);

}
