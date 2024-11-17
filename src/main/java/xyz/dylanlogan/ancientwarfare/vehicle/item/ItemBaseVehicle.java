package xyz.dylanlogan.ancientwarfare.vehicle.item;


import cpw.mods.fml.common.registry.GameRegistry;
import xyz.dylanlogan.ancientwarfare.core.item.ItemBase;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;

public abstract class ItemBaseVehicle extends ItemBase {
	public ItemBaseVehicle(String regName) {
		super();
		setUnlocalizedName(regName); // Set the item's internal name
		setCreativeTab(AncientWarfareVehicles.TAB); // Set the creative tab

		// Register item with GameRegistry
		GameRegistry.registerItem(this, regName);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, "vehicle");
//	}
}
