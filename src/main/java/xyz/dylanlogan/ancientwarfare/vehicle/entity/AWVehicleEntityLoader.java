package xyz.dylanlogan.ancientwarfare.vehicle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry;
import xyz.dylanlogan.ancientwarfare.core.entity.AWEntityRegistry.EntityDeclaration;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;

public class AWVehicleEntityLoader
{

private static int nextID = 0;

public static void load()
  {
//  EntityDeclaration reg = new EntityDeclaration(VehicleBase.class, AWEntityRegistry.VEHICLE_TEST, nextID++, AncientWarfareVehicles.instance, 120, 3, true)
//    {
//    @Override
//    public Entity createEntity(World world)
//      {
//      return new VehicleBase(world);
//      }
//    };
//  AWEntityRegistry.registerEntity(reg);
  }

}
