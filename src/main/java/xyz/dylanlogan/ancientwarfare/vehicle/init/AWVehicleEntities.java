package xyz.dylanlogan.ancientwarfare.vehicle.init;

import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry.EntityDeclaration;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.MissileBase;

public class AWVehicleEntities {

	public static final AWVehicleEntities INSTANCE = new AWVehicleEntities();
	private static int nextID = 0;

	public static void load() {
		EntityDeclaration reg = new VehicleDeclaration(VehicleBase.class, AWEntityRegistry.VEHICLE);
		AWEntityRegistry.registerEntity(reg);

		reg = new VehicleDeclaration(MissileBase.class, AWEntityRegistry.MISSILE);
		AWEntityRegistry.registerEntity(reg);
	}

	private static class VehicleDeclaration extends EntityDeclaration {

		public VehicleDeclaration(Class<? extends Entity> entityClass, String entityName) {
			super(entityClass, entityName, nextID++);
		}

		@Override
		public Object mod() {
			return AncientWarfareVehicles.instance;
		}

		@Override
		public int trackingRange() {
			return 120;
		}

		@Override
		public int updateFrequency() {
			return 3;
		}

		@Override
		public boolean sendsVelocityUpdates() {
			return true;
		}
	}
}
