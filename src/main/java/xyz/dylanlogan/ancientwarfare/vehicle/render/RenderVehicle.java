package xyz.dylanlogan.ancientwarfare.vehicle.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.IVehicleType;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBallistaMobile;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBallistaStand;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBatteringRam;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBoatBallista;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBoatCatapult;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderBoatTransport;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCannonMobileFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCannonStandFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCannonStandTurret;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCatapultMobileFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCatapultMobileTurret;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCatapultStandFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderCatapultStandTurret;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderChestCart;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderHwacha;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderTrebuchetLarge;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderTrebuchetMobileFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderTrebuchetStandFixed;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderTrebuchetStandTurret;
import xyz.dylanlogan.ancientwarfare.vehicle.render.vehicle.RenderVehicleBase;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.HashMap;

public class RenderVehicle extends Render {

	private final HashMap<IVehicleType, RenderVehicleBase> vehicleRenders = new HashMap<>();

	public RenderVehicle() {
		vehicleRenders.put(VehicleRegistry.CATAPULT_STAND_FIXED, new RenderCatapultStandFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.CATAPULT_STAND_TURRET, new RenderCatapultStandTurret(renderManager));
		vehicleRenders.put(VehicleRegistry.CATAPULT_MOBILE_FIXED, new RenderCatapultMobileFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.CATAPULT_MOBILE_TURRET, new RenderCatapultMobileTurret(renderManager));
		vehicleRenders.put(VehicleRegistry.BALLISTA_STAND_FIXED, new RenderBallistaStand(renderManager));
		vehicleRenders.put(VehicleRegistry.BALLISTA_STAND_TURRET, new RenderBallistaStand(renderManager));
		vehicleRenders.put(VehicleRegistry.BALLISTA_MOBILE_FIXED, new RenderBallistaMobile(renderManager));
		vehicleRenders.put(VehicleRegistry.BALLISTA_MOBILE_TURRET, new RenderBallistaMobile(renderManager));
		vehicleRenders.put(VehicleRegistry.BATTERING_RAM, new RenderBatteringRam(renderManager));
		vehicleRenders.put(VehicleRegistry.CANNON_STAND_FIXED, new RenderCannonStandFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.CANNON_STAND_TURRET, new RenderCannonStandTurret(renderManager));
		vehicleRenders.put(VehicleRegistry.CANNON_MOBILE_FIXED, new RenderCannonMobileFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.HWACHA, new RenderHwacha(renderManager));
		vehicleRenders.put(VehicleRegistry.TREBUCHET_STAND_FIXED, new RenderTrebuchetStandFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.TREBUCHET_STAND_TURRET, new RenderTrebuchetStandTurret(renderManager));
		vehicleRenders.put(VehicleRegistry.TREBUCHET_MOBILE_FIXED, new RenderTrebuchetMobileFixed(renderManager));
		vehicleRenders.put(VehicleRegistry.TREBUCHET_LARGE, new RenderTrebuchetLarge(renderManager));
		vehicleRenders.put(VehicleRegistry.CHEST_CART, new RenderChestCart(renderManager));
		vehicleRenders.put(VehicleRegistry.BOAT_BALLISTA, new RenderBoatBallista(renderManager));
		vehicleRenders.put(VehicleRegistry.BOAT_CATAPULT, new RenderBoatCatapult(renderManager));
		vehicleRenders.put(VehicleRegistry.BOAT_TRANSPORT, new RenderBoatTransport(renderManager));
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
		if (!(entity instanceof VehicleBase)) return;

		VehicleBase vehicle = (VehicleBase) entity;
		boolean useAlpha = false;

		// Handle transparency in first-person view
		if (!AWVehicleStatics.clientSettings.renderVehiclesInFirstPerson &&
				vehicle.getControllingPassenger() == Minecraft.getMinecraft().thePlayer &&
				Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
			useAlpha = true;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		// Render the vehicle
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);

		if (vehicle.hitAnimationTicks > 0) {
			float percent = ((float) vehicle.hitAnimationTicks / 20.0F);
			GL11.glColor4f(1.0F, 1.0F - percent, 1.0F - percent, 1.0F);
		}

		bindTexture(vehicle.getTexture());
		RenderVehicleBase render = vehicleRenders.get(vehicle.vehicleType);
		if (render != null) {
			render.renderVehicle(vehicle, x, y, z, yaw, partialTicks);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();

		if (useAlpha) {
			GL11.glDisable(GL11.GL_BLEND);
		}

		// Render nameplate
		if (isInWorld(vehicle) &&
				AWVehicleStatics.clientSettings.renderVehicleNameplates &&
				vehicle.getControllingPassenger() != Minecraft.getMinecraft().thePlayer) {
			renderNamePlate(vehicle, x, y, z);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return entity instanceof VehicleBase ? ((VehicleBase) entity).getTexture() : null;
	}

	private boolean isInWorld(VehicleBase vehicle) {
		return vehicle.posY > 0;
	}

	private DecimalFormat formatter1d = new DecimalFormat("#.#");

	private void renderNamePlate(VehicleBase vehicle, double x, double y, double z) {
		double distanceSq = vehicle.getDistanceSqToEntity(renderManager.livingPlayer);
		int maxDistance = 64;
		String displayName = vehicle.vehicleType.getLocalizedName();

		if (AWVehicleStatics.clientSettings.renderVehicleNameplateHealth) {
			displayName += " " + formatter1d.format(vehicle.getHealth()) + "/" + formatter1d.format(vehicle.baseHealth);
		}

		if (distanceSq <= (maxDistance * maxDistance)) {
			FontRenderer fontRenderer = getFontRendererFromRenderManager();
			float scale = 0.016666668F * 1.6F; // Adjusted scale
			float namePlateHeight = vehicle.height + 0.75F;

			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y + namePlateHeight, (float) z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-scale, -scale, scale);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			Tessellator tessellator = Tessellator.instance;
			int textWidth = fontRenderer.getStringWidth(displayName) / 2;

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			tessellator.addVertex(-textWidth - 1, -1, 0.0D);
			tessellator.addVertex(-textWidth - 1, 8, 0.0D);
			tessellator.addVertex(textWidth + 1, 8, 0.0D);
			tessellator.addVertex(textWidth + 1, -1, 0.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			fontRenderer.drawString(displayName, -fontRenderer.getStringWidth(displayName) / 2, 0, 553648127);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			fontRenderer.drawString(displayName, -fontRenderer.getStringWidth(displayName) / 2, 0, -1);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
