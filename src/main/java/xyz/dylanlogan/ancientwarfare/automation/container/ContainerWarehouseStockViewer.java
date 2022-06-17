package xyz.dylanlogan.ancientwarfare.automation.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.automation.tile.warehouse2.TileWarehouseStockViewer;
import xyz.dylanlogan.ancientwarfare.automation.tile.warehouse2.TileWarehouseStockViewer.WarehouseStockFilter;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerTileBase;
import xyz.dylanlogan.ancientwarfare.core.interfaces.INBTSerialable;

import java.util.ArrayList;
import java.util.List;

public class ContainerWarehouseStockViewer extends ContainerTileBase<TileWarehouseStockViewer> {

    public final List<WarehouseStockFilter> filters = new ArrayList<WarehouseStockFilter>();

    public ContainerWarehouseStockViewer(EntityPlayer player, int x, int y, int z) {
        super(player, x, y, z);
        filters.addAll(tileEntity.getFilters());
        tileEntity.addViewer(this);
        addPlayerSlots(88);//240-8-4-4*18
    }

    /**
     * should be called from the tile whenever its client-side filters change
     */
    public void onFiltersChanged() {
        refreshGui();
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (tag.hasKey("filterList")) {
            tileEntity.setFilters(INBTSerialable.Helper.read(tag, "filterList", WarehouseStockFilter.class));
        }
        super.handlePacketData(tag);
    }

    public void sendFiltersToServer() {
        NBTTagCompound tag = new NBTTagCompound();
        INBTSerialable.Helper.write(tag, "filterList", filters);
        sendDataToServer(tag);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        tileEntity.removeViewer(this);
        super.onContainerClosed(par1EntityPlayer);
    }

}
