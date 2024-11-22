package xyz.dylanlogan.ancientwarfare.npc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import xyz.dylanlogan.ancientwarfare.core.input.InputHandler;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IItemKeyInterface;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;

import java.util.Collection;
import java.util.List;

public abstract class ItemOrders extends Item implements IItemKeyInterface {

    public ItemOrders() {
        this.setCreativeTab(AWNpcItemLoader.npcTab);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("guistrings.npc.orders.open_gui"));
        String key = InputHandler.instance.getKeybindBinding(InputHandler.KEY_ALT_ITEM_USE_0);
        list.add(StatCollector.translateToLocalFormatted("guistrings.npc.orders.add_position", key));
    }

    @Override
    public boolean onKeyActionClient(EntityPlayer player, ItemStack stack, ItemKey key) {
        return key == ItemKey.KEY_0;
    }

    public abstract Collection<? extends BlockPosition> getPositionsForRender(ItemStack stack);

    public void addMessage(EntityPlayer player){
        player.addChatComponentMessage(new ChatComponentTranslation("guistrings.npc.orders.position_added"));
    }
}
