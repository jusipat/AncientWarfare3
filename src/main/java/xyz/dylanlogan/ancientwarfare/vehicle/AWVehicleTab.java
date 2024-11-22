package xyz.dylanlogan.ancientwarfare.vehicle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleItems;

public class AWVehicleTab extends CreativeTabs {
	public AWVehicleTab() {
		super("tabs.vehicles");
	}

	@Override
	public Item getTabIconItem() {
		return AWVehicleItems.SPAWNER;
	}
}
