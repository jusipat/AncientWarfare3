package xyz.dylanlogan.ancientwarfare.vehicle.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends ItemBaseVehicle {
	//private String tooltipName;
	//private String vehicleUpgradeTooltipName;
	private final String registryName;

	public ItemUpgrade(String registryName) {
        super();
        this.setUnlocalizedName(registryName); // Set unlocalized name
		this.registryName = registryName; // Store the registry name
		//tooltipName = "item." + registryName + ".tooltip"; // Set tooltip localization key
		//vehicleUpgradeTooltipName = "item.vehicle_upgrade_tooltip"; // General tooltip localization key
	}


	public String getRegistryName() {
		return registryName;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(I18n.format(tooltipName));
//		tooltip.add(I18n.format(vehicleUpgradeTooltipName));
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.MOD_ID, "vehicle/upgrade"), "variant=" + getRegistryName().getResourcePath()));
//	}
}
