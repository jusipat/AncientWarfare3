package xyz.dylanlogan.ancientwarfare.automation.gui;

import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;

public class GuiWorksiteCropFarm extends GuiWorksiteBase {

    public GuiWorksiteCropFarm(ContainerBase par1Container) {
        super(par1Container);
    }

    @Override
    public void initElements() {
        addLabels();
        addSideSelectButton();
        addBoundsAdjustButton();
    }

    @Override
    public void setupElements() {

    }

}
