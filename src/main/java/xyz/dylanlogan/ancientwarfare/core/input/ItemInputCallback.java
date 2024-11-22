package xyz.dylanlogan.ancientwarfare.core.input;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.network.PacketItemInteraction;

@SideOnly(Side.CLIENT)
class ItemInputCallback implements IInputCallback {
	private final IItemKeyInterface.ItemAltFunction altFunction;

	ItemInputCallback(IItemKeyInterface.ItemAltFunction altFunction) {
		this.altFunction = altFunction;
	}

	@Override
	public void onKeyPressed() {
		Minecraft minecraft = Minecraft.getMinecraft();
		if (minecraft.currentScreen != null) {
			return; // useless !
		}
	}

	private boolean runAction(Minecraft minecraft) {
		ItemStack stack = minecraft.thePlayer.getHeldItem();
		if (stack.getItem() instanceof IItemKeyInterface && ((IItemKeyInterface) stack.getItem()).onKeyActionClient(minecraft.thePlayer, stack, altFunction)) {
			PacketItemInteraction pkt = new PacketItemInteraction(altFunction.ordinal()); // this probably breaks things
			NetworkHandler.sendToServer(pkt);
			return true;
		}
		return false;
	}
}
