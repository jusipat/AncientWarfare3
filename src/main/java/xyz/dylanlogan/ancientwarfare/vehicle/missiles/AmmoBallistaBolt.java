package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleSounds;

public class AmmoBallistaBolt extends Ammo {

	public AmmoBallistaBolt() {
		super();
		ammoWeight = 2.f;
		renderScale = 0.3f;
		vehicleDamage = AWVehicleStatics.vehicleStats.ammoBallistaBoltDamage;
		entityDamage = AWVehicleStatics.vehicleStats.ammoBallistaBoltDamage;
		isArrow = true;
		isRocket = false;
		isPersistent = true;
		configName = "ballist_bolt";
		modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/arrow_wood.png");
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		if (!world.isRemote) {
			//world.playSound(null, x, y, z, AWVehicleSounds.BALLISTA_BOLT_HIT_GROUND, SoundCategory.AMBIENT, 2, 1);
		}
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			// using World.playSound instead of Entity.playSound, because Entity.playSound plays the sound to everyone nearby except(!) this player
			//world.playSound(x, y, z, AWVehicleSounds.BALLISTA_BOLT_HIT_ENTITY, 2, 1);
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, false, false), getEntityDamage());
		}
	}

}
