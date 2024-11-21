package xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

public abstract class RenderVehicleBase extends Render {

	protected RenderVehicleBase() {
		this.shadowSize = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof VehicleBase) {
			return ((VehicleBase) entity).getTexture();
		}
		return null;
	}

	public abstract void renderVehicle(VehicleBase entity, double x, double y, double z, float entityYaw, float partialTicks);

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity instanceof VehicleBase) {
			renderVehicle((VehicleBase) entity, x, y, z, entityYaw, partialTicks);
		}
	}
}
