package xyz.dylanlogan.ancientwarfare.automation.block;

import net.minecraft.block.material.Material;
import xyz.dylanlogan.ancientwarfare.automation.item.AWAutomationItemLoader;
import xyz.dylanlogan.ancientwarfare.core.block.BlockRotationHandler.RotationType;

public abstract class BlockTorqueGenerator extends BlockTorqueBase {

    protected BlockTorqueGenerator(String regName) {
        super(Material.rock);
        this.setCreativeTab(AWAutomationItemLoader.automationTab);
        this.setBlockName(regName);
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public boolean invertFacing() {
        return false;
    }

}
