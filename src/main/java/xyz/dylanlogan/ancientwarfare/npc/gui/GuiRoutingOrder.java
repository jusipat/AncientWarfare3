package xyz.dylanlogan.ancientwarfare.npc.gui;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import xyz.dylanlogan.ancientwarfare.core.block.Direction;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.GuiContainerBase;
import xyz.dylanlogan.ancientwarfare.core.gui.elements.*;
import xyz.dylanlogan.ancientwarfare.core.interfaces.ITooltipRenderer;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;
import xyz.dylanlogan.ancientwarfare.npc.container.ContainerRoutingOrder;
import xyz.dylanlogan.ancientwarfare.npc.orders.RoutingOrder.RoutePoint;
import org.lwjgl.input.Mouse;

import java.util.List;

public class GuiRoutingOrder extends GuiContainerBase<ContainerRoutingOrder> {

    private boolean hasChanged = false;
    private CompositeScrolled area;

    public GuiRoutingOrder(ContainerBase container) {
        super(container);
    }

    @Override
    public void initElements() {
        area = new CompositeScrolled(this, 0, 0, xSize, ySize - 4 * 18 - 8 - 4 - 8);
        addGuiElement(area);
    }

    @Override
    public void setupElements() {
        area.clearElements();
        List<RoutePoint> entries = getContainer().routingOrder.getEntries();
        ItemSlot slot;
        Label label;
        Button button;
        int totalHeight = 8;
        int index = 0;

        Block block;
        BlockPosition pos;

        String labelString;
        for (RoutePoint point : entries) {
            pos = point.getTarget();
            block = player.worldObj.getBlock(pos.x, pos.y, pos.z);
            label = new Label(8, totalHeight, block == null ? "" : block.getLocalizedName());
            area.addGuiElement(label);

            label = new Label(120, totalHeight, pos.toString());
            area.addGuiElement(label);

            labelString = Direction.getDirectionFor(point.getBlockSide()).getTranslationKey();
            button = new IndexedButton(8, totalHeight + 10, 55, 12, labelString, index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.changeBlockSide(index);
                    refreshGui();
                    hasChanged = true;
                }
            };
            area.addGuiElement(button);

            labelString = point.getRouteType().getTranslationKey();
            button = new IndexedButton(8 + 55 + 2, totalHeight + 10, 80, 12, labelString, index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.changeRouteType(index);
                    refreshGui();
                    hasChanged = true;
                }
            };
            area.addGuiElement(button);

            button = new IndexedButton(8 + 55 + 80 + 4, totalHeight + 10, 12, 12, "+", index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.increment(index);
                    refreshGui();
                    hasChanged = true;
                }
            };
            area.addGuiElement(button);

            button = new IndexedButton(8 + 55 + 80 + 12 + 6, totalHeight + 10, 12, 12, "-", index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.decrement(index);
                    refreshGui();
                    hasChanged = true;
                }
            };
            area.addGuiElement(button);

            button = new IndexedButton(8 + 55 + 80 + 12 + 12 + 8, totalHeight + 10, 55, 12, "guistrings.npc.remove_point", index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.remove(index);
                    refreshGui();
                    hasChanged = true;
                }
            };
            area.addGuiElement(button);

            for (int i = 0; i < point.getFilterSize(); i++) {
                slot = new IndexedRoutePointItemSlot(8 + i * 18, totalHeight + 10 + 12 + 2, point.getFilterInSlot(i), this, point, i) {
                    @Override
                    public void onSlotClicked(ItemStack stack) {
                        onFilterSlotClicked(this, point, index, stack);
                    }
                };
                area.addGuiElement(slot);
            }

            labelString = point.getIgnoreDamage() ? EnumChatFormatting.RED.toString() + EnumChatFormatting.STRIKETHROUGH.toString() : "";
            labelString += StatCollector.translateToLocal("guistrings.dmg");
            button = new IndexedButton(8 + 8 * 18, totalHeight + 10 + 12 + 2, 40, 12, labelString, index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.toggleIgnoreDamage(index);
                    hasChanged = true;
                    refreshGui();
                }
            };
            area.addGuiElement(button);

            labelString = point.getIgnoreTag() ? EnumChatFormatting.RED.toString() + EnumChatFormatting.STRIKETHROUGH.toString() : "";
            labelString += StatCollector.translateToLocal("guistrings.tag");
            button = new IndexedButton(8 + 8 * 18 + 40, totalHeight + 10 + 12 + 2, 40, 12, labelString, index) {
                @Override
                protected void onPressed() {
                    getContainer().routingOrder.toggleIgnoreTag(index);
                    hasChanged = true;
                    refreshGui();
                }
            };
            area.addGuiElement(button);

            totalHeight += 18 + 10 + 12 + 4;

            area.addGuiElement(new Line(0, totalHeight - 1, xSize - 13, totalHeight - 1, 1, 0x000000ff));

            index++;
        }
        area.setAreaSize(totalHeight);
    }

    private void onFilterSlotClicked(ItemSlot slot, RoutePoint point, int index, ItemStack stack) {
        //TODO move this functionality in as default for item-slots, or toggleable to enable?
        if (slot.getStack() != null && isShiftKeyDown()) {
            if (Mouse.getEventButton() == 0)//left
            {
                slot.getStack().stackSize += 32;
                point.setFilter(index, slot.getStack());
            } else if (Mouse.getEventButton() == 1)//right
            {
                slot.getStack().stackSize -= 32;
                if (slot.getStack().stackSize < 1) {
                    slot.getStack().stackSize = 1;
                }
                point.setFilter(index, slot.getStack());
            }
        } else if (slot.getStack() != null && isCtrlKeyDown()) {
            if (Mouse.getEventButton() == 0)//left
            {
                slot.getStack().stackSize += 1;
                point.setFilter(index, slot.getStack());
            } else if (Mouse.getEventButton() == 1)//right
            {
                slot.getStack().stackSize -= 1;
                if (slot.getStack().stackSize < 1) {
                    slot.getStack().stackSize = 1;
                }
                point.setFilter(index, slot.getStack());
            }
        } else {
            if (stack == null) {
                point.setFilter(index, null);
                slot.setItem(null);
            } else {
                if (InventoryTools.doItemStacksMatch(stack, slot.getStack())) {
                    if (Mouse.getEventButton() == 0)//left
                    {
                        slot.getStack().stackSize += stack.stackSize;
                        point.setFilter(index, slot.getStack());
                    } else if (Mouse.getEventButton() == 1)//right
                    {
                        slot.getStack().stackSize -= stack.stackSize;
                        if (slot.getStack().stackSize < 1) {
                            slot.getStack().stackSize = 1;
                        }
                        point.setFilter(index, slot.getStack());
                    }
                } else {
                    stack = stack.copy();
                    point.setFilter(index, stack);
                    slot.setItem(stack);
                }
            }
        }
        hasChanged = true;
    }

    @Override
    protected boolean onGuiCloseRequested() {
        if (hasChanged) {
            getContainer().onClose();
        }
        return super.onGuiCloseRequested();
    }

    private class IndexedRoutePointItemSlot extends ItemSlot {
        final RoutePoint point;
        final int index;

        public IndexedRoutePointItemSlot(int topLeftX, int topLeftY, ItemStack item,
                                         ITooltipRenderer render, RoutePoint point, int index) {
            super(topLeftX, topLeftY, item, render);
            this.point = point;
            this.index = index;
        }
    }

    private class IndexedButton extends Button {
        final int index;

        public IndexedButton(int topLeftX, int topLeftY, int width, int height, String text, int index) {
            super(topLeftX, topLeftY, width, height, text);
            this.index = index;
        }
    }

}
