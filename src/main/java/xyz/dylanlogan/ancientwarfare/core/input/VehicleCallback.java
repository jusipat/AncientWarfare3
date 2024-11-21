package xyz.dylanlogan.ancientwarfare.core.input;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.shadowmage.ancientwarfare.core.input.IInputCallback;
import net.shadowmage.ancientwarfare.vehicle.entity.VehicleBase;

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
		VehicleBase vehicle = (VehicleBase) mc.player.getRidingEntity();
		callback.accept(vehicle);
	}
}
