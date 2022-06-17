package xyz.dylanlogan.ancientwarfare.npc.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.inventory.InventoryBackpack;
import xyz.dylanlogan.ancientwarfare.core.item.ItemBackpack;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcTrader;
import xyz.dylanlogan.ancientwarfare.npc.trade.TradeList;

public class ContainerNpcPlayerOwnedTrade extends ContainerNpcBase<NpcTrader> {

    public TradeList tradeList;
    public final InventoryBackpack storage;

    public ContainerNpcPlayerOwnedTrade(EntityPlayer player, int x, int y, int z) {
        super(player, x);
        this.tradeList = entity.getTradeList();
        this.entity.startTrade(player);

        addPlayerSlots();

        storage = ItemBackpack.getInventoryFor(entity.getHeldItem());
        if (storage != null) {
            for (int i = 0; i < storage.getSizeInventory(); i++) {
                /**
                 * add backpack items to slots in container so that they are synchronized to client side inventory/container
                 * --will be used to validate trades on client-side
                 */
                addSlotToContainer(new Slot(storage, i, 100000, 100000));
            }
        }
    }

    @Override
    public void sendInitData() {
        if (tradeList != null) {
            NBTTagCompound tag = new NBTTagCompound();
            tradeList.writeToNBT(tag);

            NBTTagCompound packetTag = new NBTTagCompound();
            packetTag.setTag("tradeData", tag);
            sendDataToClient(packetTag);
        }
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (tag.hasKey("tradeData")) {
            tradeList = new TradeList();
            tradeList.readFromNBT(tag.getCompoundTag("tradeData"));
        }
        else if (tag.hasKey("doTrade")) {
            tradeList.performTrade(player, storage, tag.getInteger("doTrade"));
        }
        refreshGui();
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        this.entity.closeTrade();
        if (storage != null) {
            ItemBackpack.writeBackpackToItem(storage, this.entity.getHeldItem());
        }
        super.onContainerClosed(player);
    }

    public void doTrade(int tradeIndex) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("doTrade", tradeIndex);
        sendDataToServer(tag);
    }
}
