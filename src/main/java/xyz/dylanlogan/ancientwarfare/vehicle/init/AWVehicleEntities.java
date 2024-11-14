package xyz.dylanlogan.ancientwarfare.vehicle.init;

import net.minecraft.entity.Entity;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry.EntityDeclaration;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

public class AWVehicleEntities {

	private static int nextID = 0;

	public static void load() {
		EntityDeclaration reg = new VehiculeDeclaration(VehicleBase.class, AWEntityRegistry.VEHICLE);
		AWEntityRegistry.registerEntity(reg);

		reg = new VehiculeDeclaration(MissileBase.class, AWEntityRegistry.MISSILE);
		AWEntityRegistry.registerEntity(reg);
	}

	private static class VehiculeDeclaration extends EntityDeclaration {

		public VehiculeDeclaration(Class<? extends Entity> entityClass, String entityName) {
			super(entityClass, entityName, nextID++, AncientWarfareVehicles.MOD_ID);
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
