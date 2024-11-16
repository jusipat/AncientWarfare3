package xyz.dylanlogan.ancientwarfare.vehicle.input;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class VehicleCallback implements IInputCallback {
	private final Consumer<VehicleBase> callback;

	public VehicleCallback(Consumer<VehicleBase> callback) {
		this.callback = callback;
	}

	@Override
	public void onKeyPressed() {
		Minecraft mc = Minecraft.getMinecraft();
		VehicleBase vehicle = (VehicleBase) mc.thePlayer.ridingEntity;
		callback.accept(vehicle);
	}
}
