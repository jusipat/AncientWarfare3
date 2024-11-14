package xyz.dylanlogan.ancientwarfare.vehicle.container;

import net.minecraft.entity.player.EntityPlayer;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

public class ContainerVehicle extends ContainerBase {
	public VehicleBase vehicle;

	public ContainerVehicle(EntityPlayer player, int entityId, int dummy1, int dummy2) {
		super(player);
		this.vehicle = (VehicleBase) player.world.getEntityByID(entityId);
	}
}
