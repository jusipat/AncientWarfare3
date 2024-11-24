package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

public class AmmoIronBallShot extends Ammo {

	public AmmoIronBallShot() {
		super();
		renderScale = 0.05f;
		ammoWeight = 1.f;
		entityDamage = 8;
		vehicleDamage = 8;
		isCraftable = false;
		configName = "iron_ball_shot";
		modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/ammo_stone_shot.png");
		isCraftable = false;
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		//NOOP
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, false, false), getEntityDamage());
		}
	}
}
