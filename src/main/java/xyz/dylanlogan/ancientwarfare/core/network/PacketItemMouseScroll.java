package xyz.dylanlogan.ancientwarfare.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PacketItemMouseScroll extends PacketBase {
	private boolean scrollUp;

	public PacketItemMouseScroll() {}

	public PacketItemMouseScroll(boolean scrollUp) {
		this.scrollUp = scrollUp;
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		data.writeBoolean(scrollUp);
	}

	@Override
	protected void readFromStream(ByteBuf data) {
		scrollUp = data.readBoolean();
	}

	@Override
	protected void execute(EntityPlayer player) {
		ItemStack stack = player.getHeldItem();
		Item item = stack.getItem();
		if (item instanceof IScrollableItem){
			if (scrollUp) {
				((IScrollableItem) item).onScrollUp(player.worldObj, player, stack);
			} else {
				((IScrollableItem) item).onScrollDown(player.worldObj, player, stack);
			}
		}
	}
}
