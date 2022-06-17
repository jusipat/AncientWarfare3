package xyz.dylanlogan.ancientwarfare.automation.tile.torque;

import xyz.dylanlogan.ancientwarfare.automation.config.AWAutomationStatics;

public final class TileDistributorHeavy extends TileDistributor {

    @Override
    protected double getEfficiency() {
        return AWAutomationStatics.high_efficiency_factor;
    }

    @Override
    protected double getMaxTransfer() {
        return AWAutomationStatics.high_transfer_max;
    }
}
