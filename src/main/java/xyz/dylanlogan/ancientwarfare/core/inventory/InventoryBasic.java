package xyz.dylanlogan.ancientwarfare.core.inventory;

import net.minecraft.inventory.IInvBasic;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.interfaces.INBTSerialable;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;

public class InventoryBasic extends net.minecraft.inventory.InventoryBasic implements INBTSerialable {

    public InventoryBasic(int size) {
        super("AW.InventoryBasic", false, size);
    }

    public InventoryBasic(int size, IInvBasic listener) {
        this(size);
        func_110134_a(listener);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        InventoryTools.readInventoryFromNBT(this, tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        InventoryTools.writeInventoryToNBT(this, tag);
        return tag;
    }

}
