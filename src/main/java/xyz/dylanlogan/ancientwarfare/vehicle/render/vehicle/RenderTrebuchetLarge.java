package xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import xyz.dylanlogan.ancientwarfare.vehicle.model.ModelTrebuchetStandFixed;

public class RenderTrebuchetLarge extends RenderVehicleBase {

	private ModelTrebuchetStandFixed model = new ModelTrebuchetStandFixed();

	public RenderTrebuchetLarge() {
		super();
	}

	@Override
	public void renderVehicle(VehicleBase vehicle, double x, double y, double z, float yaw, float tick) {
		// Enable rescale normal
		GL11.glPushMatrix();

		// Scale the trebuchet model
		GL11.glScalef(2.5f, 2.5f, 2.5f);

		// Obtain firing variables
		VehicleFiringVarsHelper var = vehicle.firingVarsHelper;

		// Set arm rotations based on firing variables
		model.setArmRotations(
				var.getVar1() + tick * var.getVar2(),
				var.getVar3() + tick * var.getVar4()
		);

		// Render the model
		model.render(vehicle, 0, 0, 0, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float tick) {
		if (entity instanceof VehicleBase) {
			this.renderVehicle((VehicleBase) entity, x, y, z, yaw, tick);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null; // Define texture resource if needed
	}
}
