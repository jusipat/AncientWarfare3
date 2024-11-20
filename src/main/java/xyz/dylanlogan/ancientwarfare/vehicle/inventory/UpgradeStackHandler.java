package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;

public class UpgradeStackHandler {
	private final VehicleBase vehicle;
	private final ItemStack[] stacks;

	public UpgradeStackHandler(VehicleBase vehicle, int size) {
		this.vehicle = vehicle;
		this.stacks = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			stacks[i] = null; // Initialize slots as empty
		}
	}

	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot < 0 || slot >= stacks.length || !isItemValid(stack)) {
			return stack; // Return the stack unchanged if the slot is invalid or item is not valid
		}

		if (stacks[slot] == null) {
			if (!simulate) {
				stacks[slot] = stack.copy();
				stacks[slot].stackSize = Math.min(stack.stackSize, getSlotLimit(slot));
				onContentsChanged(slot);
			}
			return stack.stackSize > getSlotLimit(slot) ? stack.splitStack(stack.stackSize - getSlotLimit(slot)) : null;
		} else {
			// Merging logic if necessary (can be skipped for 1 stack limit per slot)
			return stack;
		}
	}

	public boolean isItemValid(ItemStack stack) {
		return UpgradeRegistry.getUpgrade(stack)
				.map(upgrade -> vehicle.vehicleType.isUpgradeValid(upgrade))
				.orElse(false);
	}

	public int getSlotLimit(int slot) {
		return 1;
	}

	public int getSlots() {
		return stacks.length;
	}

	public ItemStack getStackInSlot(int slot) {
		return (slot < 0 || slot >= stacks.length) ? null : stacks[slot];
	}

	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot < 0 || slot >= stacks.length || stacks[slot] == null) {
			return null;
		}

		ItemStack existingStack = stacks[slot];
		int extractAmount = Math.min(amount, existingStack.stackSize);
		ItemStack extracted = existingStack.copy();
		extracted.stackSize = extractAmount;

		if (!simulate) {
			existingStack.stackSize -= extractAmount;
			if (existingStack.stackSize <= 0) {
				stacks[slot] = null;
			}
			onContentsChanged(slot);
		}
		return extracted;
	}

	public int getSize() {
		return stacks.length;
	}

	public void saveToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setInteger("Slot", i);
				stacks[i].writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);
	}

	public void loadFromNBT(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("Items", 10); // 10 indicates NBTTagCompound
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getInteger("Slot");
			if (slot >= 0 && slot < stacks.length) {
				stacks[slot] = ItemStack.loadItemStackFromNBT(stackTag);
			}
		}
	}

	protected void onContentsChanged(int slot) {
		if (!vehicle.worldObj.isRemote) { // Use `worldObj` in 1.7.10
			vehicle.upgradeHelper.updateUpgrades();
		}
	}
}
