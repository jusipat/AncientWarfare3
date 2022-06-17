package xyz.dylanlogan.ancientwarfare.automation.gui;

import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;

public class GuiWorksiteAnimalFarm extends GuiWorksiteBase {

    public GuiWorksiteAnimalFarm(ContainerBase par1Container) {
        super(par1Container);

    }

    @Override
    public void initElements() {
        addLabels();
        addSideSelectButton();
        addBoundsAdjustButton();
        addAltControlsButton();
    }

    @Override
    public void setupElements() {

    }

}
