package xyz.dylanlogan.ancientwarfare.core.crafting;

import net.minecraftforge.oredict.RecipeSorter;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;

public class AWCoreCrafting {

    /**
     * load any recipes for CORE module (research book, engineering station, research station)
     */
    public static void loadRecipes() {
        RecipeSorter.register("ancientwarfare:researched", RecipeResearched.class, RecipeSorter.Category.SHAPED, "after:forge:shapedore");
        RecipeSorter.register("ancientwarfare:shapelessresearched", UnformedRecipeResearched.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
        AWCraftingManager.INSTANCE.parseRecipes(AWCoreStatics.resourcePath + "research_crafts.csv");
    }
}
