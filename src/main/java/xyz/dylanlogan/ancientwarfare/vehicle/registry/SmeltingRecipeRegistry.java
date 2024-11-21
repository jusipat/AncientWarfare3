package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public final class SmeltingRecipeRegistry {

	private SmeltingRecipeRegistry() {} // No instances!

	public static void registerRecipes() {
		// Add smelting recipes directly
		FurnaceRecipes.smelting().func_151396_a(
				AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot5),
				new ItemStack(Items.iron_ingot, 1),
				0.1f
		);

		FurnaceRecipes.smelting().func_151396_a(
				AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot10),
				new ItemStack(Items.iron_ingot, 2),
				0.1f
		);

		FurnaceRecipes.smelting().func_151396_a(
				AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot15),
				new ItemStack(Items.iron_ingot, 3),
				0.1f
		);

		FurnaceRecipes.smelting().func_151396_a(
				AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot25),
				new ItemStack(Items.iron_ingot, 5),
				0.1f
		);
	}
}
