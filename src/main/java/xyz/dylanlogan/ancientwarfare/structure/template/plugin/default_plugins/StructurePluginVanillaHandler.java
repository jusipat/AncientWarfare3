package xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import xyz.dylanlogan.ancientwarfare.core.api.AWBlocks;
import xyz.dylanlogan.ancientwarfare.structure.api.IStructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;
import xyz.dylanlogan.ancientwarfare.structure.api.StructureContentPlugin;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.block_rules.*;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleEntityHanging;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleEntityLogic;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleGates;
import xyz.dylanlogan.ancientwarfare.structure.template.plugin.default_plugins.entity_rules.TemplateRuleVanillaEntity;

import java.util.HashSet;

public class StructurePluginVanillaHandler implements StructureContentPlugin {

    public StructurePluginVanillaHandler() {

    }

    @Override
    public void addHandledBlocks(IStructurePluginManager manager) {
        HashSet<Block> specialHandledBlocks = new HashSet<Block>();
        specialHandledBlocks.add(Blocks.iron_door);
        specialHandledBlocks.add(Blocks.wooden_door);
        specialHandledBlocks.add(Blocks.standing_sign);
        specialHandledBlocks.add(Blocks.wall_sign);
        specialHandledBlocks.add(Blocks.mob_spawner);
        specialHandledBlocks.add(Blocks.command_block);
        specialHandledBlocks.add(Blocks.brewing_stand);
        specialHandledBlocks.add(Blocks.beacon);
        specialHandledBlocks.add(Blocks.dispenser);
        specialHandledBlocks.add(Blocks.lit_furnace);
        specialHandledBlocks.add(Blocks.furnace);
        specialHandledBlocks.add(Blocks.chest);
        specialHandledBlocks.add(Blocks.dropper);
        specialHandledBlocks.add(Blocks.hopper);
        specialHandledBlocks.add(Blocks.beacon);
        specialHandledBlocks.add(Blocks.trapped_chest);
        specialHandledBlocks.add(Blocks.flower_pot);
        specialHandledBlocks.add(Blocks.skull);

        for (Block block : (Iterable<Block>) GameData.getBlockRegistry()) {
            if (block != null && GameData.getBlockRegistry().getNameForObject(block).startsWith("minecraft:") && !specialHandledBlocks.contains(block)) {
                manager.registerBlockHandler("vanillaBlocks", block, TemplateRuleVanillaBlocks.class);
            }
        }
        specialHandledBlocks.clear();

        manager.registerBlockHandler("vanillaDoors", Blocks.iron_door, TemplateRuleBlockDoors.class);
        manager.registerBlockHandler("vanillaDoors", Blocks.wooden_door, TemplateRuleBlockDoors.class);
        manager.registerBlockHandler("vanillaSign", Blocks.wall_sign, TemplateRuleBlockSign.class);
        manager.registerBlockHandler("vanillaSign", Blocks.standing_sign, TemplateRuleBlockSign.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.mob_spawner, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.command_block, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.brewing_stand, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.beacon, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.lit_furnace, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.furnace, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaLogic", Blocks.beacon, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("vanillaInventory", Blocks.dispenser, TemplateRuleBlockInventory.class);
        manager.registerBlockHandler("vanillaInventory", Blocks.chest, TemplateRuleBlockInventory.class);
        manager.registerBlockHandler("vanillaInventory", Blocks.dropper, TemplateRuleBlockInventory.class);
        manager.registerBlockHandler("vanillaInventory", Blocks.hopper, TemplateRuleBlockInventory.class);
        manager.registerBlockHandler("vanillaInventory", Blocks.trapped_chest, TemplateRuleBlockInventory.class);
        manager.registerBlockHandler("vanillaFlowerPot", Blocks.flower_pot, TemplateRuleFlowerPot.class);
        manager.registerBlockHandler("vanillaSkull", Blocks.skull, TemplateRuleVanillaSkull.class);

        manager.registerBlockHandler("awAdvancedSpawner", AWBlocks.advancedSpawner, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("awCoreLogic", AWBlocks.engineeringStation, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("awCoreLogic", AWBlocks.researchStation, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("awStructureLogic", AWBlocks.draftingStation, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("awStructureLogic", AWBlocks.builderBlock, TemplateRuleBlockLogic.class);
        manager.registerBlockHandler("awStructureLogic", AWBlocks.soundBlock, TemplateRuleBlockLogic.class);
    }

    @Override
    public void addHandledEntities(IStructurePluginManager manager) {
        manager.registerEntityHandler("vanillaEntities", EntityPig.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntitySheep.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityCow.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityChicken.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityBoat.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityIronGolem.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityWolf.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityOcelot.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntityWither.class, TemplateRuleVanillaEntity.class);
        manager.registerEntityHandler("vanillaEntities", EntitySnowman.class, TemplateRuleVanillaEntity.class);

        manager.registerEntityHandler("vanillaHangingEntity", EntityPainting.class, TemplateRuleEntityHanging.class);
        manager.registerEntityHandler("vanillaHangingEntity", EntityItemFrame.class, TemplateRuleEntityHanging.class);

        manager.registerEntityHandler("vanillaLogicEntity", EntityHorse.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityVillager.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityMinecartHopper.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityMinecartChest.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityMinecartEmpty.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityMinecartFurnace.class, TemplateRuleEntityLogic.class);
        manager.registerEntityHandler("vanillaLogicEntity", EntityMinecartTNT.class, TemplateRuleEntityLogic.class);

        manager.registerEntityHandler("awGate", EntityGate.class, TemplateRuleGates.class);
    }
}
