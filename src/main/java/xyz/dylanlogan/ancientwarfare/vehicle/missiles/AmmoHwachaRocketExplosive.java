package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;

public class AmmoHwachaRocketExplosive extends Ammo {

	public AmmoHwachaRocketExplosive() {
		super();
		this.entityDamage = AWVehicleStatics.vehicleStats.ammoHwachaRocketExplosiveDamage;
		this.vehicleDamage = AWVehicleStatics.vehicleStats.ammoHwachaRocketExplosiveDamage;
		this.isArrow = true;
		this.isPersistent = false;
		this.isRocket = true;
		this.ammoWeight = 1.3f;
		this.renderScale = 0.2f;
		this.configName = "hwacha_rocket_explosive";
		this.modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/arrow_wood.png");
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		if (!world.isRemote) {
			Vec3 dirVec = hit.hitVec;
			Vec3 hitVec = hit.hitVec.addVector(dirVec.xCoord * 0.2d, dirVec.yCoord * 0.2d, dirVec.zCoord * 0.2d);
			createExplosion(world, missile, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord, 0.6f);
		}
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, false, true), this.getEntityDamage());
			ent.setFire(3);
			createExplosion(world, missile, x, y, z, 0.8f);
		}
	}
}
