package xyz.dylanlogan.ancientwarfare.npc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.npc.orders.UpkeepOrder;

import java.util.ArrayList;
import java.util.Collection;

public class ItemUpkeepOrder extends ItemOrders {

    @Override
    public Collection<? extends BlockPosition> getPositionsForRender(ItemStack stack) {
        Collection<BlockPosition> positionList = new ArrayList<BlockPosition>();
        UpkeepOrder order = UpkeepOrder.getUpkeepOrder(stack);
        if (order != null && order.getUpkeepPosition() != null)
            positionList.add(order.getUpkeepPosition());
        return positionList;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote)
            NetworkHandler.INSTANCE.openGui(player, NetworkHandler.GUI_NPC_UPKEEP_ORDER, 0, 0, 0);
        return stack;
    }

    @Override
    public void onKeyAction(EntityPlayer player, ItemStack stack, ItemKey key) {
        UpkeepOrder upkeepOrder = UpkeepOrder.getUpkeepOrder(stack);
        if (upkeepOrder != null) {
            BlockPosition hit = BlockTools.getBlockClickedOn(player, player.worldObj, false);
            if (upkeepOrder.addUpkeepPosition(player.worldObj, hit)) {
                upkeepOrder.write(stack);
                player.addChatComponentMessage(new ChatComponentTranslation("guistrings.npc.upkeep_point_set"));
            } else 
                NetworkHandler.INSTANCE.openGui(player, NetworkHandler.GUI_NPC_UPKEEP_ORDER, 0, 0, 0);
        }
    }

}
