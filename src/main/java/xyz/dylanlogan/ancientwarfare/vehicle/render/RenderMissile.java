package xyz.dylanlogan.ancientwarfare.vehicle.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.MissileBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.render.missile.RenderArrow;
import xyz.dylanlogan.ancientwarfare.vehicle.render.missile.RenderShot;

import javax.annotation.Nullable;
import java.util.HashMap;

public class RenderMissile extends Render<MissileBase> {
	private HashMap<IAmmo, Render<MissileBase>> missileRenders = new HashMap<>();
	private RenderArrow arrowRender;
	private RenderShot shotRender;

	public RenderMissile(RenderManager renderManager) {
		super(renderManager);

		arrowRender = new RenderArrow(renderManager);
		shotRender = new RenderShot(renderManager);

		missileRenders.put(AmmoRegistry.ammoStoneShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoStoneShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoStoneShot30, shotRender);
		missileRenders.put(AmmoRegistry.ammoStoneShot45, shotRender);
		missileRenders.put(AmmoRegistry.ammoFireShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoFireShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoFireShot30, shotRender);
		missileRenders.put(AmmoRegistry.ammoFireShot45, shotRender);
		missileRenders.put(AmmoRegistry.ammoClusterShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoClusterShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoClusterShot30, shotRender);
		missileRenders.put(AmmoRegistry.ammoClusterShot45, shotRender);
		missileRenders.put(AmmoRegistry.ammoPebbleShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoPebbleShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoPebbleShot30, shotRender);
		missileRenders.put(AmmoRegistry.ammoPebbleShot45, shotRender);
		missileRenders.put(AmmoRegistry.ammoNapalm10, shotRender);
		missileRenders.put(AmmoRegistry.ammoNapalm15, shotRender);
		missileRenders.put(AmmoRegistry.ammoNapalm30, shotRender);
		missileRenders.put(AmmoRegistry.ammoNapalm45, shotRender);
		missileRenders.put(AmmoRegistry.ammoExplosive10, shotRender);
		missileRenders.put(AmmoRegistry.ammoExplosive15, shotRender);
		missileRenders.put(AmmoRegistry.ammoExplosive30, shotRender);
		missileRenders.put(AmmoRegistry.ammoExplosive45, shotRender);
		missileRenders.put(AmmoRegistry.ammoHE10, shotRender);
		missileRenders.put(AmmoRegistry.ammoHE15, shotRender);
		missileRenders.put(AmmoRegistry.ammoHE30, shotRender);
		missileRenders.put(AmmoRegistry.ammoHE45, shotRender);

		missileRenders.put(AmmoRegistry.ammoIronShot5, shotRender);
		missileRenders.put(AmmoRegistry.ammoIronShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoIronShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoIronShot25, shotRender);
		missileRenders.put(AmmoRegistry.ammoGrapeShot5, shotRender);
		missileRenders.put(AmmoRegistry.ammoGrapeShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoGrapeShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoGrapeShot25, shotRender);
		missileRenders.put(AmmoRegistry.ammoCanisterShot5, shotRender);
		missileRenders.put(AmmoRegistry.ammoCanisterShot10, shotRender);
		missileRenders.put(AmmoRegistry.ammoCanisterShot15, shotRender);
		missileRenders.put(AmmoRegistry.ammoCanisterShot25, shotRender);

		missileRenders.put(AmmoRegistry.ammoArrow, arrowRender);
		missileRenders.put(AmmoRegistry.ammoRocket, arrowRender);
		missileRenders.put(AmmoRegistry.ammoHwachaRocketFlame, arrowRender);
		missileRenders.put(AmmoRegistry.ammoHwachaRocketExplosive, arrowRender);
		missileRenders.put(AmmoRegistry.ammoHwachaRocketAirburst, arrowRender);
		missileRenders.put(AmmoRegistry.ammoBallistaBolt, arrowRender);
		missileRenders.put(AmmoRegistry.ammoBallistaBoltExplosive, arrowRender);
		missileRenders.put(AmmoRegistry.ammoBallistaBoltFlame, arrowRender);
		missileRenders.put(AmmoRegistry.ammoBallistaBoltIron, arrowRender);

		missileRenders.put(AmmoRegistry.ammoBallShot, shotRender);
		missileRenders.put(AmmoRegistry.ammoBallIronShot, shotRender);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(MissileBase entity) {
		return entity.getTexture();
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		Render render = missileRenders.get(entity.ammoTyle);
		render.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
