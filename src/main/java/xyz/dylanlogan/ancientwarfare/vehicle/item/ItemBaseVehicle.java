package xyz.dylanlogan.ancientwarfare.vehicle.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import xyz.dylanlogan.ancientwarfare.core.item.ItemBase;
import xyz.dylanlogan.ancientwarfare.npc.AncientWarfareNPC;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;

public abstract class ItemBaseVehicle extends ItemBase implements IClientRegister {
	public ItemBaseVehicle(String regName) {
		super();
		setCreativeTab(AncientWarfareVehicles.TAB);

		AncientWarfareNPC.proxy.addClientRegister(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this, "vehicle");
	}
}
