package xyz.dylanlogan.ancientwarfare.automation.gui;

import net.minecraft.client.Minecraft;
import xyz.dylanlogan.ancientwarfare.automation.container.ContainerMailbox;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.GuiContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.Button;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.Checkbox;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.Label;
import org.lwjgl.input.Mouse;

public class GuiMailboxInventory extends GuiContainerBase<ContainerMailbox> {

    private Label inputName, outputName;

    public GuiMailboxInventory(ContainerBase par1Container) {
        super(par1Container, 178, 240);
        ySize = getContainer().guiHeight;
    }

    @Override
    public void initElements() {
        Button inputNameSelect = new Button(178 - 8 - 75, 8, 75, 12, "guistrings.automation.mailbox_name_select") {
            @Override
            protected void onPressed() {
                getContainer().removeSlots();
                int x = Mouse.getX();
                int y = Mouse.getY();
                Minecraft.getMinecraft().displayGuiScreen(new GuiMailboxNameSelect(GuiMailboxInventory.this, true));
                Mouse.setCursorPosition(x, y);
            }
        };
        addGuiElement(inputNameSelect);

        Button outputNameSelect = new Button(178 - 8 - 75, 8 + 12 + 2 * 18, 75, 12, "guistrings.automation.mailbox_target_select") {
            @Override
            protected void onPressed() {
                getContainer().removeSlots();
                int x = Mouse.getX();
                int y = Mouse.getY();
                Minecraft.getMinecraft().displayGuiScreen(new GuiMailboxNameSelect(GuiMailboxInventory.this, false));
                Mouse.setCursorPosition(x, y);
            }
        };
        addGuiElement(outputNameSelect);

        Button sideSelectButton = new Button(178 - 8 - 75, ySize - 8 - 12 - 12, 75, 12, "guistrings.inventory.setsides") {
            @Override
            protected void onPressed() {
                getContainer().removeSlots();
                int x = Mouse.getX();
                int y = Mouse.getY();
                Minecraft.getMinecraft().displayGuiScreen(new GuiMailboxInventorySideSetup(GuiMailboxInventory.this));
                Mouse.setCursorPosition(x, y);
            }
        };
        addGuiElement(sideSelectButton);

        Checkbox export = new Checkbox(8, ySize - 12 - 12 - 8, 12, 12, "guistrings.automation.auto_output") {
            @Override
            public void onToggled() {
                getContainer().handleAutoExportToggle(checked());
            }
        };
        export.setChecked(getContainer().autoExport);
        addGuiElement(export);

        Checkbox privateBox = new Checkbox(8, ySize - 12 - 8, 12, 12, "guistrings.automation.private_mailbox") {
            @Override
            public void onToggled() {
                getContainer().handlePrivateBoxToggle(checked());
            }
        };
        privateBox.setChecked(getContainer().privateBox);
        addGuiElement(privateBox);

        inputName = new Label(8, 8, "");
        addGuiElement(inputName);

        outputName = new Label(8, 8 + 12 + 2 * 18, "");
        addGuiElement(outputName);
    }

    @Override
    public void setupElements() {
        inputName.setText(getContainer().mailboxName);
        outputName.setText(getContainer().targetName);
    }

}
