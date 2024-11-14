package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;

public class AmmoCanisterShot extends Ammo {

	public AmmoCanisterShot(int weight) {
		super("ammo_canister_shot_" + weight);
		this.ammoWeight = weight;
		float scaleFactor = weight + 45.f;
		this.renderScale = (weight / scaleFactor) * 2;
		this.configName = "canister_shot_" + weight;
		this.modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/ammo_stone_shot.png");
		this.entityDamage = AWVehicleStatics.vehicleStats.ammoCanisterDamage;
		this.vehicleDamage = AWVehicleStatics.vehicleStats.ammoCanisterDamage;
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		if (!world.isRemote) {
			spawnGroundBurst(world, hit, 10, AmmoRegistry.ammoBallIronShot, (int) ammoWeight, 35, missile.shooterLiving);
		}
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			spawnAirBurst(world, (float) ent.posX, (float) ent.posY + ent.height, (float) ent.posZ, 10, AmmoRegistry.ammoBallIronShot, (int) ammoWeight, missile.shooterLiving);
		}
	}

}
