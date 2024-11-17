package xyz.dylanlogan.ancientwarfare.vehicle.input;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

@SideOnly(Side.CLIENT)
public class VehicleKeyConflictContext implements IKeyConflictContext {
	public static final VehicleKeyConflictContext INSTANCE = new VehicleKeyConflictContext();

	private VehicleKeyConflictContext() {
	}

	@Override
	public boolean isActive() {
		Minecraft mc = Minecraft.getMinecraft();
		return mc.currentScreen == null && mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.ridingEntity instanceof VehicleBase;
	}

	@Override
	public boolean conflicts(IKeyConflictContext other) {
		return this == other;
	}
}
