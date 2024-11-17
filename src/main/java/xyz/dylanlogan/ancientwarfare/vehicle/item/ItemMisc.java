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

public class ItemMisc extends ItemBaseVehicle {
	private String itemTypeTooltip;

	public ItemMisc(String regName, VehicleItemType itemType) {
		super(regName);
		itemTypeTooltip = "item." + itemType.getItemTypeString() + ".tooltip";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(itemTypeTooltip));
	}

	@Override
	@SideOnly(Side.CLIENT)

	public void registerClient() {
		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/misc"), "variant=" + getRegistryName().getResourcePath()));
	}

	public enum VehicleItemType {
		AMMO_MATERIAL("ammo_material"),
		VEHICLE_COMPONENT("vehicle_component");

		private String itemType;

		VehicleItemType(String itemType) {
			this.itemType = itemType;
		}

		public String getItemTypeString() {
			return itemType;
		}
	}
}
