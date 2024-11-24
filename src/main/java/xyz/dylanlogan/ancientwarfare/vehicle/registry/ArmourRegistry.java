package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.IVehicleArmor;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorIron;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorObsidian;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.VehicleArmorStone;
import xyz.dylanlogan.ancientwarfare.vehicle.item.ItemArmor;

import java.util.HashMap;
import java.util.Map;

public class ArmourRegistry {

	private ArmourRegistry() {}

	public static IVehicleArmor armorStone;
	public static IVehicleArmor armorIron;
	public static IVehicleArmor armorObsidian;

	private static Map<String, IVehicleArmor> armorInstances = new HashMap<>();

	/**
	 * Registers all armor types as items.
	 */
	public static void registerArmorTypes() {
		armorStone = registerArmorType(new VehicleArmorStone(), "armor_stone");
		armorIron = registerArmorType(new VehicleArmorIron(), "armor_iron");
		armorObsidian = registerArmorType(new VehicleArmorObsidian(), "armor_obsidian");
	}

	/**
	 * Registers a single armor type and its corresponding item.
	 */
	private static IVehicleArmor registerArmorType(IVehicleArmor armor, String registryName) {
		armorInstances.put(registryName, armor);
		ResourceLocation loc = new ResourceLocation(registryName);
		ItemArmor item = new ItemArmor(loc);
		item.setUnlocalizedName(registryName);
		GameRegistry.registerItem(item, registryName);

		return armor;
	}

	/**
	 * Retrieves an armor type by its registry name.
	 */
	public static IVehicleArmor getArmorType(String registryName) {
		return armorInstances.get(registryName);
	}

	/**
	 * Retrieves an armor type based on the item stack.
	 */
	public static IVehicleArmor getArmorForStack(ItemStack stack) {
		if (stack == null || stack.getItem() == null) {
			return null;
		}
		return armorInstances.get(stack.getUnlocalizedName());
	}
}
