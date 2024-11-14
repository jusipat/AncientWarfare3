package xyz.dylanlogan.ancientwarfare.vehicle.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;

import javax.annotation.Nullable;
import java.util.List;

public class ItemAmmo extends ItemBaseVehicle {
	private String tooltipName;
	private String tooltipVehicleList;

	public ItemAmmo(ResourceLocation registryName, IAmmo ammo) {
		super(registryName.getResourcePath());
		if (!ammo.isAvailableAsItem())
			setCreativeTab(null);
		tooltipName = "item." + registryName.getResourcePath() + ".tooltip";
		tooltipVehicleList = "item." + registryName.getResourcePath() + ".tooltipVehicleList";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(tooltipName));
		tooltip.add(I18n.format(tooltipVehicleList));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/ammo"), "variant=" + getRegistryName().getResourcePath()));
	}
}
