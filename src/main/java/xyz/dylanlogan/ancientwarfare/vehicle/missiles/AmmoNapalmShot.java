package xyz.dylanlogan.ancientwarfare.vehicle.missiles;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

public class AmmoNapalmShot extends Ammo {

	public AmmoNapalmShot(int weight) {
		super();
		this.ammoWeight = weight;
		this.entityDamage = weight;
		this.vehicleDamage = weight;
		float scaleFactor = weight + 45.f;
		this.renderScale = (weight / scaleFactor) * 2;
		this.configName = "napalm_shot_" + weight;
		this.modelTexture = new ResourceLocation(AncientWarfareCore.modID, "textures/model/vehicle/ammo/ammo_stone_shot.png");
		this.isFlaming = true;
	}

	@Override
	public void onImpactWorld(World world, float x, float y, float z, MissileBase missile, MovingObjectPosition hit) {
		int bx = MathHelper.floor_double(x);
		int by = MathHelper.floor_double(y);
		int bz = MathHelper.floor_double(z);
		setBlockToLava(world, bx, by, bz, 5);
		double dx = missile.motionX;
		double dz = missile.motionZ;
		if (Math.abs(dx) > Math.abs(dz)) {
			dz = 0;
		} else {
			dx = 0;
		}
		dx = dx < 0 ? -1 : dx > 0 ? 1 : dx;
		dz = dz < 0 ? -1 : dz > 0 ? 1 : dz;
		if (ammoWeight >= 15)//set the 'forward' block to lava as well
		{
			setBlockToLava(world, bx + (int) dx, by, bz + (int) dz, 5);
		}
		if (ammoWeight >= 30)//set the 'rear' block to lava as well
		{
			setBlockToLava(world, bx - (int) dx, by, bz - (int) dz, 5);
		}
		if (ammoWeight >= 45) {
			if (dx == 0)//have already done Z's
			{
				setBlockToLava(world, bx + 1, by, bz, 5);
				setBlockToLava(world, bx - 1, by, bz, 5);
			} else {
				setBlockToLava(world, bx, by, bz + 1, 5);
				setBlockToLava(world, bx, by, bz - 1, 5);
			}
		}
	}

	@Override
	public void onImpactEntity(World world, Entity ent, float x, float y, float z, MissileBase missile) {
		if (!world.isRemote) {
			ent.attackEntityFrom(DamageType.causeEntityMissileDamage(missile.shooterLiving, true, false), this.getEntityDamage());
			ent.setFire(3);
			onImpactWorld(world, x, (float) ent.posY, z, missile, null);
		}
	}

}
