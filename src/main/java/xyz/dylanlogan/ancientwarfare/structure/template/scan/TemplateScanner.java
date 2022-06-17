package xyz.dylanlogan.ancientwarfare.structure.template.scan;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRule;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRuleBlock;
import xyz.dylanlogan.ancientwarfare.structure.api.TemplateRuleEntity;
import xyz.dylanlogan.ancientwarfare.structure.config.AWStructureStatics;
import xyz.dylanlogan.ancientwarfare.structure.template.StructurePluginManager;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TemplateScanner {

    /**
     * @param turns # of turns for proper orientation
     */
    @SuppressWarnings("unchecked")
    public static StructureTemplate scan(World world, BlockPosition min, BlockPosition max, BlockPosition key, int turns, String name) {
        int xSize = max.x - min.x + 1;
        int ySize = max.y - min.y + 1;
        int zSize = max.z - min.z + 1;

        int xOutSize = xSize, zOutSize = zSize;
        int swap;
        for (int i = 0; i < turns; i++) {
            swap = xOutSize;
            xOutSize = zOutSize;
            zOutSize = swap;
        }
        key = BlockTools.rotateInArea(key.sub(min), xSize, zSize, turns);

        short[] templateRuleData = new short[xSize * ySize * zSize];


        HashMap<String, List<TemplateRuleBlock>> pluginBlockRuleMap = new HashMap<String, List<TemplateRuleBlock>>();
        List<TemplateRule> currentRulesAll = new ArrayList<TemplateRule>();
        Block scannedBlock;
        TemplateRuleBlock scannedBlockRule = null;
        List<TemplateRuleBlock> pluginBlockRules;
        String pluginId;
        int index;
        int meta;
        int scanX, scanZ, scanY;
        BlockPosition destination = new BlockPosition();
        int nextRuleID = 1;
        for (scanY = min.y; scanY <= max.y; scanY++) {
            for (scanZ = min.z; scanZ <= max.z; scanZ++) {
                for (scanX = min.x; scanX <= max.x; scanX++) {
                    destination = BlockTools.rotateInArea(new BlockPosition(scanX, scanY, scanZ).sub(min), xSize, zSize, turns);

                    scannedBlock = world.getBlock(scanX, scanY, scanZ);

                    if (scannedBlock != null && !AWStructureStatics.shouldSkipScan(scannedBlock) && !scannedBlock.isAir(world, scanX, scanY, scanZ)) {
                        pluginId = StructurePluginManager.INSTANCE.getPluginNameFor(scannedBlock);
                        if (pluginId != null) {
                            meta = world.getBlockMetadata(scanX, scanY, scanZ);
                            pluginBlockRules = pluginBlockRuleMap.get(pluginId);
                            if (pluginBlockRules == null) {
                                pluginBlockRules = new ArrayList<TemplateRuleBlock>();
                                pluginBlockRuleMap.put(pluginId, pluginBlockRules);
                            }
                            boolean found = false;
                            for (TemplateRuleBlock rule : pluginBlockRules) {
                                if (rule.shouldReuseRule(world, scannedBlock, meta, turns, scanX, scanY, scanZ)) {
                                    scannedBlockRule = rule;
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                scannedBlockRule = StructurePluginManager.INSTANCE.getRuleForBlock(world, scannedBlock, turns, scanX, scanY, scanZ);
                                if(scannedBlockRule!=null) {
                                    scannedBlockRule.ruleNumber = nextRuleID;
                                    nextRuleID++;
                                    pluginBlockRules.add(scannedBlockRule);
                                    currentRulesAll.add(scannedBlockRule);
                                }
                            }
                            index = StructureTemplate.getIndex(destination.x, destination.y, destination.z, xOutSize, ySize, zOutSize);
                            templateRuleData[index] = (short) scannedBlockRule.ruleNumber;
                        }
                    }
                }//end scan x-level for
            }//end scan z-level for
        }//end scan y-level for

        List<TemplateRuleEntity> scannedEntityRules = new ArrayList<TemplateRuleEntity>();
        List<Entity> entitiesInAABB = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(min.x, min.y, min.z, max.x + 1, max.y + 1, max.z + 1));
        nextRuleID = 0;
        for (Entity e : entitiesInAABB) {
            int ex = MathHelper.floor_double(e.posX);
            int ey = MathHelper.floor_double(e.posY);
            int ez = MathHelper.floor_double(e.posZ);
            TemplateRuleEntity scannedEntityRule = StructurePluginManager.INSTANCE.getRuleForEntity(world, e, turns, ex, ey, ez);
            if (scannedEntityRule != null) {
                destination = BlockTools.rotateInArea(new BlockPosition(ex, ey, ez).sub(min), xSize, zSize, turns);
                scannedEntityRule.ruleNumber = nextRuleID;
                scannedEntityRule.setPosition(destination);
                scannedEntityRules.add(scannedEntityRule);
                nextRuleID++;
            }
        }

        TemplateRule[] templateRules = new TemplateRule[currentRulesAll.size() + 1];
        for (int i = 0; i < currentRulesAll.size(); i++)//offset by 1 -- we want a null rule for 0==air
        {
            templateRules[i + 1] = currentRulesAll.get(i);
        }

        TemplateRuleEntity[] entityRules = new TemplateRuleEntity[scannedEntityRules.size()];
        for (int i = 0; i < scannedEntityRules.size(); i++) {
            entityRules[i] = scannedEntityRules.get(i);
        }

        StructureTemplate template = new StructureTemplate(name, xOutSize, ySize, zOutSize, key.x, key.y, key.z);
        template.setTemplateData(templateRuleData);
        template.setRuleArray(templateRules);
        template.setEntityRules(entityRules);
        return template;
    }

}
