package xyz.dylanlogan.ancientwarfare.automation.proxy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.dylanlogan.ancientwarfare.core.api.ModuleStatus;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;
import xyz.dylanlogan.ancientwarfare.core.interfaces.ITorque.ITorqueTile;

public class RFProxy {

    public static RFProxy instance;

    public static void loadInstance() {
        if (ModuleStatus.redstoneFluxEnabled) {
            try {
                instance = (RFProxy) Class.forName("xyz.dylanlogan.ancientwarfare.automation.proxy.RFProxyActual").newInstance();
                AWLog.log("RF Compatability loaded successfully");
            } catch (Exception e) {
                e.printStackTrace();
                instance = new RFProxy();
            }
        } else {
            instance = new RFProxy();
        }
    }

    protected RFProxy() {
        // TODO Auto-generated constructor stub
    }

    public boolean isRFTile(TileEntity te) {
        return false;
    }

    public double transferPower(ITorqueTile generator, ForgeDirection from, TileEntity target) {
        return 0;
    }

}
