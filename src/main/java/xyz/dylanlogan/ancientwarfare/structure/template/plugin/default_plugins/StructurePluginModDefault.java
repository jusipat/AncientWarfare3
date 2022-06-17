package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.IInventory;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.api.StructureContentPlugin;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleBlockLogic;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.TemplateRuleModBlocks;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleEntityHanging;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleEntityLogic;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleVanillaEntity;

public class StructurePluginModDefault implements StructureContentPlugin {

    private final String mod;
    public StructurePluginModDefault(String id) {
        this.mod = id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addHandledBlocks(IStructurePluginManager manager) {
        for (Block aBlock : (Iterable<Block>) GameData.getBlockRegistry()) {
            if(aBlock!=null && GameData.getBlockRegistry().getNameForObject(aBlock).startsWith(mod)) {
                if (aBlock.hasTileEntity(0)) {
                    manager.registerBlockHandler("modContainerDefault", aBlock, TemplateRuleBlockLogic.class);
                } else {
                    manager.registerBlockHandler("modBlockDefault", aBlock, TemplateRuleModBlocks.class);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addHandledEntities(IStructurePluginManager manager) {
        for (Object key : EntityList.stringToClassMapping.keySet()) {
            if(key.toString().startsWith(mod)) {
                Class<? extends Entity> clazz = (Class<? extends Entity>) EntityList.stringToClassMapping.get(key);
                if (EntityHanging.class.isAssignableFrom(clazz)) {
                    manager.registerEntityHandler("modHangingDefault", clazz, TemplateRuleEntityHanging.class);
                } else if (EntityAnimal.class.isAssignableFrom(clazz)) {
                    manager.registerEntityHandler("modAnimalDefault", clazz, TemplateRuleVanillaEntity.class);
                } else if (EntityLiving.class.isAssignableFrom(clazz) || IInventory.class.isAssignableFrom(clazz)) {
                    manager.registerEntityHandler("modEquippedDefault", clazz, TemplateRuleEntityLogic.class);
                }
            }
        }
    }

}
