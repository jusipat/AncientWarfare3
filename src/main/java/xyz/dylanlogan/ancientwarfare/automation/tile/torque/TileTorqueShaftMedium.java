package xyz.dylanlogan.ancientwarfare.automation.tile.torque;

import xyz.dylanlogan.ancientwarfare.automation.config.AWAutomationStatics;

public final class TileTorqueShaftMedium extends TileTorqueShaft {

    @Override
    protected double getEfficiency() {
        return AWAutomationStatics.med_efficiency_factor;
    }

    @Override
    protected double getMaxTransfer() {
        return AWAutomationStatics.med_transfer_max;
    }
}
