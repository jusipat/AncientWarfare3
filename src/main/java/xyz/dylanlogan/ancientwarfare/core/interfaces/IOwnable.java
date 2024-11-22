package xyz.dylanlogan.ancientwarfare.core.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import xyz.dylanlogan.ancientwarfare.core.owner.Owner;

/**
 * Tile entities/Entities that are owned by a player -- called by spawning/placing items to set owner
 *
 * 
 */
public interface IOwnable {

    public void setOwner(EntityPlayer player);

    public String getOwnerName();

    public boolean isOwner(EntityPlayer player);
}
