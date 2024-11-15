package xyz.dylanlogan.ancientwarfare.core.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * proxy interface to allow for advanced gui interaction from containers
 * base gui class should implement this interface
 *
 * 
 */
public interface IContainerGuiCallback {

    public void refreshGui();

    public void handlePacketData(NBTTagCompound data);

}
