package xyz.dylanlogan.ancientwarfare.vehicle.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMisc extends ItemBaseVehicle {
	private String itemTypeTooltip;

	public ItemMisc(VehicleItemType itemType) {
		super();
		itemTypeTooltip = "item." + itemType.getItemTypeString() + ".tooltip";
	}

	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> tooltip, boolean p_77624_4_) {
		tooltip.add(I18n.format(itemTypeTooltip));
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//
//	public void registerClient() {
//		ModelLoaderHelper.registerItem(this, (i, m) -> new ModelResourceLocation(new ResourceLocation(AncientWarfareCore.modID, "vehicle/misc"), "variant=" + getRegistryName().getResourcePath()));
//	}

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
