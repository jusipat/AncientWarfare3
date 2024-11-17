package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

public class StorageStackHandler {
    private final ItemStack[] stacks;
    private VehicleBase vehicle;

    public StorageStackHandler(VehicleBase vehicle, int size) {
        this.vehicle = vehicle;
        this.stacks = new ItemStack[size];
        for (int i = 0; i < size; i++) {
            stacks[i] = null; // Initialize slots as empty
        }
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot < 0 || slot >= stacks.length || stack == null) {
            return stack; // Invalid slot or null stack
        }

        ItemStack existing = stacks[slot];
        int maxStackSize = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));

        if (existing == null) {
            if (!simulate) {
                stacks[slot] = stack.copy();
                stacks[slot].stackSize = Math.min(stack.stackSize, maxStackSize);
                onContentsChanged(slot);
            }
            return stack.stackSize > maxStackSize ? stack.splitStack(stack.stackSize - maxStackSize) : null;
        } else if (ItemStack.areItemStacksEqual(existing, stack) && ItemStack.areItemStackTagsEqual(existing, stack)) {
            int combinedSize = existing.stackSize + stack.stackSize;
            int remaining = combinedSize > maxStackSize ? combinedSize - maxStackSize : 0;

            if (!simulate) {
                existing.stackSize = Math.min(combinedSize, maxStackSize);
                onContentsChanged(slot);
            }
            return remaining > 0 ? stack.splitStack(remaining) : null;
        }

        return stack;
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < 0 || slot >= stacks.length || stacks[slot] == null) {
            return null;
        }

        ItemStack existing = stacks[slot];
        int extractAmount = Math.min(amount, existing.stackSize);
        ItemStack extracted = existing.copy();
        extracted.stackSize = extractAmount;

        if (!simulate) {
            existing.stackSize -= extractAmount;
            if (existing.stackSize <= 0) {
                stacks[slot] = null;
            }
            onContentsChanged(slot);
        }
        return extracted;
    }

    public ItemStack getStackInSlot(int slot) {
        return (slot < 0 || slot >= stacks.length) ? null : stacks[slot];
    }

    public int getSlots() {
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
    }

    public int getSlotLimit(int slot) {
        return 64;
    }
}
