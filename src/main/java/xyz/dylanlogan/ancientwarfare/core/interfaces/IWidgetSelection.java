package xyz.dylanlogan.ancientwarfare.core.interfaces;

import xyz.dylanlogan.ancientwarfare.core.gui.elements.GuiElement;

public interface IWidgetSelection {

    public void onWidgetSelected(GuiElement element);

    public void onWidgetDeselected(GuiElement element);

}
