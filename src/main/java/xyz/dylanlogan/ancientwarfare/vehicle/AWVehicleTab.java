package xyz.dylanlogan.ancientwarfare.vehicle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AWVehicleTab extends CreativeTabs {
    public AWVehicleTab(String lable) {
        super("tabs.vehicles");
    }

    @Override
    public Item getTabIconItem() {
        return new ItemStack(Items.apple).getItem();
    }
}
