package xyz.dylanlogan.ancientwarfare.vehicle.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.gui.GuiVehicleAmmoSelection;
import xyz.dylanlogan.ancientwarfare.vehicle.gui.GuiVehicleInventory;
import xyz.dylanlogan.ancientwarfare.vehicle.gui.GuiVehicleStats;
import xyz.dylanlogan.ancientwarfare.vehicle.input.VehicleInputHandler;
import xyz.dylanlogan.ancientwarfare.vehicle.render.RenderMissile;
import xyz.dylanlogan.ancientwarfare.vehicle.render.RenderOverlay;
import xyz.dylanlogan.ancientwarfare.vehicle.render.RenderOverlayAdvanced;
import xyz.dylanlogan.ancientwarfare.vehicle.render.RenderVehicle;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		NetworkHandler.registerGui(NetworkHandler.GUI_VEHICLE_AMMO_SELECTION, GuiVehicleAmmoSelection.class);
		NetworkHandler.registerGui(NetworkHandler.GUI_VEHICLE_INVENTORY, GuiVehicleInventory.class);
		NetworkHandler.registerGui(NetworkHandler.GUI_VEHICLE_STATS, GuiVehicleStats.class);

		RenderingRegistry.registerEntityRenderingHandler(MissileBase.class, RenderMissile::new);
		RenderingRegistry.registerEntityRenderingHandler(VehicleBase.class, RenderVehicle::new);

		MinecraftForge.EVENT_BUS.register(new RenderOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderOverlayAdvanced());
	}

	@Override
	public void init() {
		VehicleInputHandler.initKeyBindings();
	}
}
