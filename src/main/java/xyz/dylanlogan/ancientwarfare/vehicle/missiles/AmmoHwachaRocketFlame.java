package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;

public class AmmoHwachaRocketFlame extends Ammo {

	public AmmoHwachaRocketFlame() {
		super();
		this.entityDamage = AWVehicleStatics.vehicleStats.ammoHwachaRocketFlameDamage;
		this.vehicleDamage = AWVehicleStatics.vehicleStats.ammoHwachaRocketFlameDamage;
		this.isArrow = true;
		this.isPersistent = true;
		this.isRocket = true;
		this.isFlaming = true;
		this.ammoWeight = 1.1f;
		this.renderScale = 0.2f;
		this.renderScale = 0.2f;
		this.configName = "hwacha_rocket_flame";
		this.modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/arrow_wood.png");
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		if (!world.isRemote) {
			igniteBlock(world, (int) x, (int) y + 2, (int) z, 5);
		}
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, true, false), this.getEntityDamage());
			ent.setFire(2);
		}
	}
}
