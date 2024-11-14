package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

public class UpgradeStackHandler extends ItemStackHandler {
	private VehicleBase vehicle;

	public UpgradeStackHandler(VehicleBase vehicle, int size) {
		super(size);
		this.vehicle = vehicle;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return isItemValid(stack) ? super.insertItem(slot, stack, simulate) : stack;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return UpgradeRegistry.getUpgrade(par1ItemStack).map(upgrade -> vehicle.vehicleType.isUpgradeValid(upgrade)).orElse(false);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (!vehicle.world.isRemote) {
			vehicle.upgradeHelper.updateUpgrades();
		}
	}
}
