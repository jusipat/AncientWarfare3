package xyz.dylanlogan.ancientwarfare.core.interfaces;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Tile Entities with right-click actions (usually opening a GUI)
 *
 * 
 */
public interface IInteractableTile {

    public boolean onBlockClicked(EntityPlayer player);

}
