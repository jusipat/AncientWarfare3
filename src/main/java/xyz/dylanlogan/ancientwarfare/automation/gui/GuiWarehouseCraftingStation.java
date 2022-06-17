package xyz.dylanlogan.ancientwarfare.automation.gui;

import net.minecraft.item.ItemStack;
import xyz.dylanlogan.ancientwarfare.core.api.AWItems;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.GuiContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.ItemSlot;

public class GuiWarehouseCraftingStation extends GuiContainerBase {

    public GuiWarehouseCraftingStation(ContainerBase par1Container) {
        super(par1Container, 176, 154);
    }

    @Override
    public void initElements() {
        ItemSlot bookSlotIcon = new ItemSlot(8, 8, new ItemStack(AWItems.researchBook), this);
        bookSlotIcon.setRenderTooltip(false).setHighlightOnMouseOver(false).setRenderSlotBackground(false).setRenderItemQuantity(false);
        addGuiElement(bookSlotIcon);
    }

    @Override
    public void setupElements() {

    }

}
