package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;

public class ArmourStackHandler {
	private final ItemStack[] stacks; // Array to hold armor slots
	private final VehicleBase vehicle;

	public ArmourStackHandler(VehicleBase vehicle, int size) {
		this.stacks = new ItemStack[size];
		this.vehicle = vehicle;
	}

	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}

	public void setStackInSlot(int slot, ItemStack stack) {
		stacks[slot] = stack;
		onContentsChanged(slot);
	}

	public int getSlots() {
		return stacks.length;
	}

	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!isItemValid(stack)) {
			return stack;
		}

		if (simulate) {
			return null;
		}

		if (stacks[slot] == null) {
			stacks[slot] = stack.copy();
			stack.stackSize = 0;
		} else {
			return stack;
		}

		onContentsChanged(slot);
		return null;
	}

	public boolean isItemValid(ItemStack stack) {
		return ArmourRegistry.getArmorForStack(stack)
				.map(armor -> vehicle.vehicleType.isArmorValid(armor))
				.orElse(false);
	}

	public int getSlotLimit(int slot) {
		return 1;
	}

	// Callback for when inventory changes
	protected void onContentsChanged(int slot) {
		if (!vehicle.worldObj.isRemote) {
			vehicle.upgradeHelper.updateUpgrades();
		}
	}

	public void saveToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setInteger("Slot", i);
				stacks[i].writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		compound.setTag("ArmorItems", list);
	}

	public void loadFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("ArmorItems", 10); // 10 = NBTTagCompound
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getInteger("Slot");
			if (slot >= 0 && slot < stacks.length) {
				stacks[slot] = ItemStack.loadItemStackFromNBT(stackTag);
			}
		}
	}
}
