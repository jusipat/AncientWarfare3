package xyz.dylanlogan.ancientwarfare.core.inventory;

/**
 * callback interface for tile-entities for when their inventory has changed--to alert them to update internal counters/etc
 *
 * 
 */
public interface ISidedTile {

    public int getTileMeta();

    public void onInventoryChanged();

}
