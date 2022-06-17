package xyz.dylanlogan.ancientwarfare.automation.tile.warehouse2;

import xyz.dylanlogan.ancientwarfare.core.inventory.InventorySlotlessBasic;

public final class TileWarehouseStorageMedium extends TileWarehouseStorage {

    public TileWarehouseStorageMedium() {

    }

    @Override
    public int getStorageAdditionSize() {
        return 2 * super.getStorageAdditionSize();
    }
}
