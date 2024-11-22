package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.joml.Vector3i;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

public class AmmoExplosiveShot extends Ammo {

	private boolean bigExplosion;

	public AmmoExplosiveShot(int weight, boolean bigExplosion) {
		super("ammo_explosive_shot_" + weight + (bigExplosion ? "_big" : ""));
		this.ammoWeight = weight;
		this.bigExplosion = bigExplosion;
		this.entityDamage = weight;
		this.vehicleDamage = weight;
		float scaleFactor = weight + 45.f;
		this.renderScale = (weight / scaleFactor) * 2;

		if (bigExplosion) {
			this.configName = "high_explosive_" + weight;
		} else {
			this.configName = "explosive_" + weight;
		}
		this.modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/ammo_stone_shot.png");
	}

//	@Override
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
			explode(world, x, y, z, missile);
		}
	}

	private void explode(World world, float x, float y, float z, MissileBase missile) {
		float maxPower = bigExplosion ? 7.f : 3.5f;
		float powerPercent = ammoWeight / 45.f;
		float power = maxPower * powerPercent;
		createExplosion(world, missile, x, y, z, power);
	}
}
