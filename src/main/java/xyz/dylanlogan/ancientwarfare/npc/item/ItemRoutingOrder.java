package xyz.dylanlogan.ancientwarfare.npc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.RayTraceUtils;
import xyz.dylanlogan.ancientwarfare.npc.orders.RoutingOrder;

import java.util.ArrayList;
import java.util.Collection;

public class ItemRoutingOrder extends ItemOrders {

    @Override
    public Collection<? extends BlockPosition> getPositionsForRender(ItemStack stack) {
        Collection<BlockPosition> positionList = new ArrayList<BlockPosition>();
        RoutingOrder order = RoutingOrder.getRoutingOrder(stack);
        if (order != null && !order.isEmpty()) {
            for (RoutingOrder.RoutePoint e : order.getEntries()) {
                positionList.add(e.getTarget());
            }
        }
        return positionList;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
            NetworkHandler.INSTANCE.openGui(player, NetworkHandler.GUI_NPC_ROUTING_ORDER, 0, 0, 0);
        return stack;
    }

    @Override
    public void onKeyAction(EntityPlayer player, ItemStack stack, ItemKey key) {
        RoutingOrder order = RoutingOrder.getRoutingOrder(stack);
        if (order != null) {
            MovingObjectPosition hit = RayTraceUtils.getPlayerTarget(player, 5, 0);
            if (hit != null && hit.typeOfHit == MovingObjectType.BLOCK) {
                order.addRoutePoint(hit.sideHit, hit.blockX, hit.blockY, hit.blockZ);
                order.write(stack);
                addMessage(player);
            }
        }
    }

}
