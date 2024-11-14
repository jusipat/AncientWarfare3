package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.IVehicleArmor;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorIron;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorObsidian;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorStone;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemArmor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArmorRegistry {
	private ArmorRegistry() {}

	public static IVehicleArmor armorStone;
	public static IVehicleArmor armorIron;
	public static IVehicleArmor armorObsidian;

	private static Map<ResourceLocation, IVehicleArmor> armorInstances = new HashMap<>();

	public static void registerArmorTypes(IForgeRegistry<Item> registry) {
		armorStone = registerArmorType(new VehicleArmorStone(), registry);
		armorIron = registerArmorType(new VehicleArmorIron(), registry);
		armorObsidian = registerArmorType(new VehicleArmorObsidian(), registry);
	}

	private static IVehicleArmor registerArmorType(IVehicleArmor armor, IForgeRegistry<Item> registry) {

		armorInstances.put(armor.getRegistryName(), armor);
		ItemArmor item = new ItemArmor(armor.getRegistryName());
		registry.register(item);
		return armor;
	}

	public static Optional<IVehicleArmor> getArmorType(ResourceLocation registryName) {
		return Optional.ofNullable(armorInstances.get(registryName));
	}

	public static Optional<IVehicleArmor> getArmorForStack(ItemStack stack) {
		return Optional.ofNullable(armorInstances.get(stack.getItem().getRegistryName()));
	}
}
