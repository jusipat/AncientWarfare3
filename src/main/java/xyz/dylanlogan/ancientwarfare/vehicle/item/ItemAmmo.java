package xyz.dylanlogan.ancientwarfare.vehicle.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;

import java.util.List;

public class ItemAmmo extends Item {
	private String tooltipName;
	private String tooltipVehicleList;

	public ItemAmmo(ResourceLocation registryName) {
		System.err.println("Initialising new ItemAmmo called: "+ registryName);
        tooltipName = "item." + registryName.getResourcePath() + ".tooltip";
		tooltipVehicleList = "item." + registryName.getResourcePath() + ".tooltipVehicleList";
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean flagIn) {
		tooltip.add(I18n.format(tooltipName));
		tooltip.add(I18n.format(tooltipVehicleList));
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/ammo"), "variant=" + getRegistryName().getResourcePath()));
//	}
}
