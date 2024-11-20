package xyz.dylanlogan.ancientwarfare.vehicle.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArmor extends ItemBaseVehicle {
	private String tooltipName;

	public ItemArmor(ResourceLocation registryName) {
		super(registryName.getResourcePath());
		tooltipName = "item." + registryName.getResourcePath() + ".tooltip";
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(I18n.format(tooltipName));
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/armor"), "variant=" + getRegistryName().getResourcePath()));
//	}
}
