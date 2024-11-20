package xyz.dylanlogan.ancientwarfare.vehicle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class AWVehicleTab extends CreativeTabs {
	public AWVehicleTab() {
		super("tabs.vehicles");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(AWVehicleItems.SPAWNER);
	}
}
