package xyz.dylanlogan.ancientwarfare.vehicle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.dylanlogan.ancientwarfare.core.api.AWItems;

public class AWVehicleTab extends CreativeTabs {
	public AWVehicleTab() {
		super("tabs.vehicles");
	}

	@Override
	public Item getTabIconItem() {
		return AWItems.roughWood;
	}
}
