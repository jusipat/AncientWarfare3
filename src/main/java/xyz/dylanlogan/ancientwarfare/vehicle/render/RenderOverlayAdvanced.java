package xyz.dylanlogan.ancientwarfare.vehicle.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.joml.Vector3d;
import xyz.dylanlogan.ancientwarfare.core.util.RenderTools;
import xyz.dylanlogan.ancientwarfare.core.util.Trig;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleMovementType;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.AmmoHwachaRocket;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleRegistry;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderOverlayAdvanced {

	@SubscribeEvent
	public void renderLast(RenderWorldLastEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (AWVehicleStatics.clientSettings.renderAdvOverlay && player.ridingEntity instanceof VehicleBase && Minecraft.getMinecraft().currentScreen == null) {
			RenderOverlayAdvanced.renderAdvancedVehicleOverlay((VehicleBase) player.ridingEntity, player, event.partialTicks);
		}
	}

	private static void renderAdvancedVehicleOverlay(VehicleBase vehicle, EntityPlayer player, float partialTick) {
		if (vehicle.vehicleType == VehicleRegistry.BATTERING_RAM) {
			renderBatteringRamOverlay(vehicle, player, partialTick);
		} else if (vehicle.ammoHelper.getCurrentAmmoType() != null && vehicle.ammoHelper.getCurrentAmmoType().isRocket()) {
			renderOverlay(vehicle, player, partialTick,
					(rOffset, speed, accVec, gravity) -> drawRocketFlightPath(vehicle, player, rOffset, speed, accVec, gravity));
		} else {
			renderOverlay(vehicle, player, partialTick, (rOffset, speed, accVec, gravity) -> drawNormalTrajectory(vehicle, player, rOffset, gravity, accVec));
		}
	}

	private static void renderOverlay(VehicleBase vehicle, EntityPlayer player, float partialTick, IDynamicOverlayPartRenderer dynamicRenderer) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1, 1, 1, 0.6f);

		Vector3d renderOffset = new Vector3d(vehicle.posX - player.posX, vehicle.posY - player.posY, vehicle.posZ - player.posZ);

		drawStraightLine(vehicle, partialTick, renderOffset);

		drawDynamicPart(vehicle, partialTick, renderOffset, dynamicRenderer);

		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private static void drawStraightLine(VehicleBase vehicle, float partialTick, Vector3d renderOffset) {
		double x2 = renderOffset.x - 20 * Trig.sinDegrees(vehicle.rotationYaw + partialTick * vehicle.moveHelper.getRotationSpeed());
		double z2 = renderOffset.z - 20 * Trig.cosDegrees(vehicle.rotationYaw + partialTick * vehicle.moveHelper.getRotationSpeed());
		GL11.glLineWidth(3f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(renderOffset.x, renderOffset.y + 0.12d, renderOffset.z);
		GL11.glVertex3d(x2, renderOffset.y + 0.12d, z2);
		GL11.glEnd();
	}

	private static void drawDynamicPart(VehicleBase vehicle, float partialTick, Vector3d renderOffset, IDynamicOverlayPartRenderer dynamicRenderer) {
		GL11.glLineWidth(4f);
		GL11.glColor4f(1.f, 0.4f, 0.4f, 0.4f);
		GL11.glBegin(GL11.GL_LINES);

		double gravity = 9.81d * 0.05d * 0.05d;
		double speed = vehicle.localLaunchPower * 0.05d;
		double angle = 90 - vehicle.localTurretPitch - vehicle.rotationPitch;
		double yaw = vehicle.localTurretRotation + partialTick * vehicle.moveHelper.getRotationSpeed();

		double vH = -Trig.sinDegrees((float) angle) * speed;
		Vector3d accelerationVector = new Vector3d(Trig.sinDegrees((float) yaw) * vH, Trig.cosDegrees((float) angle) * speed, Trig.cosDegrees((float) yaw) * vH);

		dynamicRenderer.render(renderOffset, speed, accelerationVector, gravity);
		GL11.glEnd();
	}

	private static void drawRocketFlightPath(VehicleBase vehicle, EntityPlayer player, Vector3d renderOffset, double speed, Vector3d accelerationVector,
			double gravity) {
		int rocketBurnTime = (int) (speed * 20.f * AmmoHwachaRocket.BURN_TIME_FACTOR);

		Vector3d offset = vehicle.getMissileOffset();
		double x2 = renderOffset.x + offset.x;
		double y2 = renderOffset.y + offset.y;
		double z2 = renderOffset.z + offset.z;

		double floorY = renderOffset.y;

		Vector3d adjustedAccelerationVector = accelerationVector;
		if (vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR1 || vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR2) {
			adjustedAccelerationVector = adjustedAccelerationVector.add(vehicle.motionX, vehicle.motionY, vehicle.motionZ);
			floorY = -player.posY;
		}

		float xAcc = (float) (adjustedAccelerationVector.x / speed) * AmmoHwachaRocket.ACCELERATION_FACTOR;
		float yAcc = (float) (adjustedAccelerationVector.y / speed) * AmmoHwachaRocket.ACCELERATION_FACTOR;
		float zAcc = (float) (adjustedAccelerationVector.z / speed) * AmmoHwachaRocket.ACCELERATION_FACTOR;
		adjustedAccelerationVector = new Vector3d(xAcc, yAcc, zAcc);

		while (y2 >= floorY) {
			GL11.glVertex3d(x2, y2, z2);
			x2 += adjustedAccelerationVector.x;
			z2 += adjustedAccelerationVector.z;
			y2 += adjustedAccelerationVector.y;
			if (rocketBurnTime > 0) {
				rocketBurnTime--;
				adjustedAccelerationVector = adjustedAccelerationVector.add(xAcc, yAcc, zAcc);
			} else {
				adjustedAccelerationVector = adjustedAccelerationVector.add(0, -gravity, 0);
			}
			GL11.glVertex3d(x2, y2, z2);
		}
	}

	private static void drawNormalTrajectory(VehicleBase vehicle, EntityPlayer player, Vector3d renderOffset, double gravity, Vector3d accelerationVector) {
		Vector3d offset = vehicle.getMissileOffset();
		double x2 = renderOffset.x + offset.x;
		double y2 = renderOffset.y + offset.y;
		double z2 = renderOffset.z + offset.z;

		double floorY = renderOffset.y;

		Vector3d adjustedAccelerationVector = accelerationVector;
		if (vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR1 || vehicle.vehicleType.getMovementType() == VehicleMovementType.AIR2) {
			adjustedAccelerationVector = adjustedAccelerationVector.add(vehicle.motionX, vehicle.motionY, vehicle.motionZ);
			floorY = -player.posY;
		}

		while (y2 >= floorY) {
			GL11.glVertex3d(x2, y2, z2);
			x2 += adjustedAccelerationVector.x;
			z2 += adjustedAccelerationVector.z;
			y2 += adjustedAccelerationVector.y;
			adjustedAccelerationVector = adjustedAccelerationVector.add(0, -gravity, 0);
			GL11.glVertex3d(x2, y2, z2);
		}
	}

	private static void renderBatteringRamOverlay(VehicleBase vehicle, EntityPlayer player, float partialTick) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1, 1, 1, 0.6f);

		double x1 = vehicle.posX - player.posX;
		double y1 = vehicle.posY - player.posY;
		double z1 = vehicle.posZ - player.posZ;

		// Vectors for a straight line
		double x2 = x1 - 20 * Trig.sinDegrees(vehicle.rotationYaw);
		double z2 = z1 - 20 * Trig.cosDegrees(vehicle.rotationYaw);
		GL11.glLineWidth(3f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(x1, y1 + 0.12d, z1);
		GL11.glVertex3d(x2, y1 + 0.12d, z2);
		GL11.glEnd();

		Vector3d offset = vehicle.getMissileOffset();
		int bx = (int) (vehicle.posX + offset.x);
		int by = (int) (vehicle.posY + offset.y);
		int bz = (int) (vehicle.posZ + offset.z);

		// Adjusted bounding box creation
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(bx - 1, by, bz, bx + 2, by + 1, bz + 1);
		bb = adjustBBForPlayerPos(bb, player, partialTick);
		RenderTools.drawOutlinedBoundingBox(bb, 1.f, 0.2f, 0.2f);

		bb = AxisAlignedBB.getBoundingBox(bx, by, bz - 1, bx + 1, by + 1, bz + 2);
		bb = adjustBBForPlayerPos(bb, player, partialTick);
		RenderTools.drawOutlinedBoundingBox(bb, 1.f, 0.2f, 0.2f);

		bb = AxisAlignedBB.getBoundingBox(bx, by - 1, bz, bx + 1, by + 2, bz + 1);
		bb = adjustBBForPlayerPos(bb, player, partialTick);
		RenderTools.drawOutlinedBoundingBox(bb, 1.f, 0.2f, 0.2f);

		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}


	private static AxisAlignedBB adjustBBForPlayerPos(AxisAlignedBB bb, EntityPlayer player, float partialTick) {
		double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTick;
		double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTick;
		double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTick;
		return bb.offset(-x, -y, -z);
	}

	private interface IDynamicOverlayPartRenderer {
		void render(Vector3d renderOffset, double speed, Vector3d accelerationVector, double gravity);
	}
}
