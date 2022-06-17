package xyz.dylanlogan.ancientwarfare.nei_plugin;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import xyz.dylanlogan.ancientwarfare.automation.gui.GuiWarehouseCraftingStation;
import xyz.dylanlogan.ancientwarfare.automation.gui.GuiWorksiteAutoCrafting;
import xyz.dylanlogan.ancientwarfare.core.api.AWBlocks;
import xyz.dylanlogan.ancientwarfare.core.api.ModuleStatus;
import xyz.dylanlogan.ancientwarfare.core.crafting.AWCraftingManager;
import xyz.dylanlogan.ancientwarfare.core.gui.crafting.GuiEngineeringStation;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IResearchRecipe;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AWNeiRecipeHandler extends TemplateRecipeHandler {

    public AWNeiRecipeHandler() {
        API.registerRecipeHandler(this);
        API.registerUsageHandler(this);
        register(GuiEngineeringStation.class, new DefaultOverlayHandler(37, 2));
        if(ModuleStatus.automationLoaded){
            register(GuiWarehouseCraftingStation.class, new DefaultOverlayHandler(37, 2));
            register(GuiWorksiteAutoCrafting.class, new DefaultOverlayHandler(37, 2));
        }
        if(ModuleStatus.structuresLoaded){
            API.hideItem(new ItemStack(AWBlocks.gateProxy));
        }
    }

    private void register(Class<? extends GuiContainer> cl, DefaultOverlayHandler handler){
        API.registerGuiOverlay(cl, "awcrafting", handler.offsetx, handler.offsety);
        API.registerGuiOverlayHandler(cl, handler, "awcrafting");
        API.registerGuiOverlayHandler(cl, handler, "crafting");
    }

    @Override
    public String getRecipeName() {
        return "AW Crafting";
    }

    @Override
    public String getGuiTexture() {
        return "textures/gui/container/crafting_table.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return "awcrafting";
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        arecipes.clear();
        return this;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(-31, 7, 18, 18), "awcrafting"));
    }

    @Override
    public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
        LinkedList<Class<? extends GuiContainer>> list = new LinkedList<Class<? extends GuiContainer>>();
        list.add(GuiEngineeringStation.class);
        if(ModuleStatus.automationLoaded){
            list.add(GuiWarehouseCraftingStation.class);
            list.add(GuiWorksiteAutoCrafting.class);
        }
        return list;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<IResearchRecipe> allrecipes = AWCraftingManager.INSTANCE.getRecipes();
        for (IResearchRecipe irecipe : allrecipes) {
            if (InventoryTools.doItemStacksMatch(irecipe.getRecipeOutput(), result)) {
                arecipes.add(new AWCachedRecipe(irecipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<IResearchRecipe> allrecipes = AWCraftingManager.INSTANCE.getRecipes();
        for (IResearchRecipe irecipe : allrecipes) {
            for (Object target : irecipe.getInputs()) {
                if (target == null) {
                    continue;
                }
                if (target instanceof ItemStack) {
                    if (!OreDictionary.itemMatches((ItemStack) target, ingredient, false)) {
                        continue;
                    }
                } else if (target instanceof Iterable) {
                    boolean matched = false;
                    Iterator<?> itr = ((Iterable) target).iterator();
                    while (itr.hasNext() && !matched) {
                        matched = OreDictionary.itemMatches((ItemStack) itr.next(), ingredient, false);
                    }
                    if (!matched) {
                        continue;
                    }
                }
                arecipes.add(new AWCachedRecipe(irecipe));
            }
        }
    }

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
        return super.hasOverlay(gui, container, recipe) || RecipeInfo.hasDefaultOverlay(gui, "crafting");
    }

    public class AWCachedRecipe extends CachedRecipe {

        private final ArrayList<PositionedStack> ingredients;
        private final PositionedStack result;

        public AWCachedRecipe(IResearchRecipe recipe) {
            result = new PositionedStack(recipe.getRecipeOutput().copy(), 119, 24);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getInputs());
        }

        private void setIngredients(int width, int height, Object[] items) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null) {
                        continue;
                    }
                    PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }
    }
}
