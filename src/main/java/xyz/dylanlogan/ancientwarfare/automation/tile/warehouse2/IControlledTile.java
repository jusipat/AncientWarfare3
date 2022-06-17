package xyz.dylanlogan.ancientwarfare.automation.tile.warehouse2;

import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;

public interface IControlledTile {

    public void setController(IControllerTile tile);

    public IControllerTile getController();

    public BlockPosition getPosition();

}
