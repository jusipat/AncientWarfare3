package xyz.dylanlogan.ancientwarfare.vehicle.registry;

import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;

/**
 * Class responsible for defining and registering the smelting recipes, as these are non-JSON recipes.
 * Also handles dynamic recipe display and usage.
 */
@EventBusSubscriber
public final class SmeltingRecipeRegistry {

	private SmeltingRecipeRegistry() {} // No instances!

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot5)), new ItemStack(Items.iron_ingot, 1), 0.1f);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot10)), new ItemStack(Items.iron_ingot, 2), 0.1f);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot15)), new ItemStack(Items.iron_ingot, 3), 0.1f);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AmmoRegistry.getItemForAmmo(AmmoRegistry.ammoIronShot25)), new ItemStack(Items.iron_ingot, 5), 0.1f);
	}
}
