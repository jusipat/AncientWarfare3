package xyz.dylanlogan.ancientwarfare.npc.orders;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.interfaces.INBTSerialable;
import xyz.dylanlogan.ancientwarfare.npc.item.ItemTradeOrder;
import xyz.dylanlogan.ancientwarfare.npc.trade.POTradeRestockData;
import xyz.dylanlogan.ancientwarfare.npc.trade.POTradeRoute;
import xyz.dylanlogan.ancientwarfare.npc.trade.TradeList;

public class TradeOrder implements INBTSerialable {

    private POTradeRoute tradeRoute = new POTradeRoute();
    private POTradeRestockData restockEntry = new POTradeRestockData();
    private TradeList tradeList = new TradeList();

    public TradeOrder() {
    }

    public static TradeOrder getTradeOrder(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemTradeOrder) {
            TradeOrder order = new TradeOrder();
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("orders")) {
                order.readFromNBT(stack.getTagCompound().getCompoundTag("orders"));
            }
            return order;
        }
        return null;
    }

    public void write(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemTradeOrder) {
            stack.setTagInfo("orders", writeToNBT(new NBTTagCompound()));
        }
    }

    public TradeList getTradeList() {
        return tradeList;
    }

    public POTradeRoute getRoute() {
        return tradeRoute;
    }

    public POTradeRestockData getRestockData() {
        return restockEntry;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        tradeList = new TradeList();
        tradeRoute = new POTradeRoute();
        restockEntry = new POTradeRestockData();
        tradeList.readFromNBT(tag.getCompoundTag("tradeList"));
        tradeRoute.readFromNBT(tag.getCompoundTag("tradeRoute"));
        restockEntry.readFromNBT(tag.getCompoundTag("restockEntry"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setTag("tradeList", tradeList.writeToNBT(new NBTTagCompound()));
        tag.setTag("tradeRoute", tradeRoute.writeToNBT(new NBTTagCompound()));
        tag.setTag("restockEntry", restockEntry.writeToNBT(new NBTTagCompound()));
        return tag;
    }

}
