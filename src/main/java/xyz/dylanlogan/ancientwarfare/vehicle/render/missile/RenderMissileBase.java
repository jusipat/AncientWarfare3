package xyz.dylanlogan.ancientwarfare.vehicle.render.missile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.MissileBase;

public abstract class RenderMissileBase extends Render {
	static Minecraft mc = Minecraft.getMinecraft();

	protected RenderMissileBase() {
		super();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (!(entity instanceof MissileBase)) {
			return;
		}
		MissileBase missile = (MissileBase) entity;

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entityYaw - 90, 0, 1, 0);
		GL11.glRotatef(missile.rotationPitch - 90, 1, 0, 0);
		GL11.glScalef(-1, -1, 1);

		float scale = missile.ammoType.getRenderScale();
		GL11.glScalef(scale, scale, scale);

		this.bindTexture(missile.getTexture());
		renderMissile(missile, missile.ammoType, x, y, z, entityYaw, partialTicks);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof MissileBase) {
			return ((MissileBase) entity).getTexture();
		}
		return null;
	}

	public abstract void renderMissile(MissileBase missile, IAmmo ammo, double x, double y, double z, float yaw, float tick);
}
