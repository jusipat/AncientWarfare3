package xyz.dylanlogan.ancientwarfare.vehicle.entity;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public interface ITarget {
	double getX();

	double getY();

	double getZ();

	AxisAlignedBB getBoundingBox();

	boolean exists(World world);
}
