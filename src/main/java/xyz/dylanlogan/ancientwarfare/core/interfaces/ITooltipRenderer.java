package xyz.dylanlogan.ancientwarfare.core.interfaces;

import net.minecraft.item.ItemStack;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.Tooltip;

public interface ITooltipRenderer {

    public void handleItemStackTooltipRender(ItemStack stack);

    public void handleElementTooltipRender(Tooltip o);

}
