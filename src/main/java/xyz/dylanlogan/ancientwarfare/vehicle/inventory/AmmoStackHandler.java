package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;

public class AmmoStackHandler implements IInventory {
	private final ItemStack[] stacks;
	private final VehicleBase vehicle;

	public AmmoStackHandler(VehicleBase vehicle, int size) {
		this.stacks = new ItemStack[size]; // Initialize empty inventory
		this.vehicle = vehicle;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	// Get the stack in a specific slot
	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	public int getSlots() {
		return stacks.length;
	}

	public void setStackInSlot(int slot, ItemStack stack) {
		stacks[slot] = stack;
		onContentsChanged(slot); // Trigger update logic
	}

	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!isValidAmmo(stack)) {
			return stack;
		}

		if (simulate) {
			return null;
		}

		ItemStack current = stacks[slot];
		if (current == null) {
			stacks[slot] = stack.copy();
			stack.stackSize = 0;
		} else if (ItemStack.areItemStackTagsEqual(current, stack)) {
			int transferable = Math.min(stack.stackSize, current.getMaxStackSize() - current.stackSize);
			current.stackSize += transferable;
			stack.stackSize -= transferable;
		}

		onContentsChanged(slot);
		return stack.stackSize > 0 ? stack : null;
	}

	private boolean isValidAmmo(ItemStack stack) {
		return AmmoRegistry.getAmmoForStack(stack)
				.map(ammo -> vehicle.vehicleType.isAmmoValidForInventory(ammo))
				.orElse(false);
	}

	protected void onContentsChanged(int slot) {
		if (!vehicle.worldObj.isRemote) {
			vehicle.ammoHelper.updateAmmoCounts();
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
		compound.setTag("Items", list);
	}

	// Load inventory from NBT
	public void loadFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("Items", 10); // 10 = NBTTagCompound
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getInteger("Slot");
			if (slot >= 0 && slot < stacks.length) {
				stacks[slot] = ItemStack.loadItemStackFromNBT(stackTag);
			}
		}
	}
}
