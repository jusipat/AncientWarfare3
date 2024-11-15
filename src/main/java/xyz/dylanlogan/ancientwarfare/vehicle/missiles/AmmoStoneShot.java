package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

public class AmmoStoneShot extends Ammo {

	public AmmoStoneShot(int weight) {
		super("ammo_stone_shot_" + weight);
		isPersistent = false;
		isArrow = false;
		isRocket = false;
		ammoWeight = weight;
		configName = "stone_shot_" + weight;
		entityDamage = weight;
		vehicleDamage = weight;
		float scaleFactor = weight + 45.f;
		renderScale = (weight / scaleFactor) * 2;
		modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/ammo_stone_shot.png");
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		BlockPos origin = new BlockPos((int) x, (int) y, (int) z);

		float maxHardness = 5 + (ammoWeight * 0.2f + ammoWeight * 0.8f * world.rand.nextFloat()) * 0.6f;

		breakAroundOnLevel(world, origin, origin, maxHardness);
		breakAroundOnLevel(world, origin, (BlockPos) origin.up(), maxHardness);
		breakAroundOnLevel(world, origin, (BlockPos) origin.down(), maxHardness);
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, false, false), getEntityDamage());
		}
	}

}
