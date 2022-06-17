package xyz.dylanlogan.ancientwarfare.structure.gui;

import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.GuiContainerBase;
import xyz.dylanlogan.ancientwarfare.structure.container.ContainerSpawnerAdvancedInventoryBase;

public class GuiSpawnerAdvancedInventory extends GuiContainerBase {

    public GuiSpawnerAdvancedInventory(ContainerBase par1Container) {
        super(par1Container);
    }

    @Override
    public void initElements() {

    }

    @Override
    public void setupElements() {

    }

    @Override
    protected boolean onGuiCloseRequested() {
        ((ContainerSpawnerAdvancedInventoryBase) inventorySlots).sendSettingsToServer();
        return true;
    }

}
