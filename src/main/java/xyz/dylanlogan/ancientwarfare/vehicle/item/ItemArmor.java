package xyz.dylanlogan.ancientwarfare.vehicle.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemArmor extends ItemBaseVehicle {
	private String tooltipName;
	public ItemArmor(ResourceLocation registryName) {
		super();
		tooltipName = "item." + registryName.getResourcePath() + ".tooltip";
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean flagIn) {
		tooltip.add(I18n.format(tooltipName));
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/armor"), "variant=" + getRegistryName().getResourcePath()));
//	}
}
