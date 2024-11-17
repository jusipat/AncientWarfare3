package xyz.dylanlogan.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.dylanlogan.ancientwarfare.vehicle.armors.IVehicleArmor;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.ArmourRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.UpgradeRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleAmmoEntry;
import xyz.dylanlogan.ancientwarfare.vehicle.upgrades.IVehicleUpgradeType;

import java.util.ArrayList;
import java.util.List;

public class VehicleInventory {
	private static final String INVENTORY_TAG = "inventory";
	private final VehicleBase vehicle;

	private AmmoStackHandler ammoInventory;
	private ArmourStackHandler armorInventory;
	private UpgradeStackHandler upgradeInventory;
	private StorageStackHandler storageInventory;

	public VehicleInventory(VehicleBase vehicle) {
		this.vehicle = vehicle;
	}

	public void setInventorySizes(int upgrade, int ammo, int armor, int storage) {
		this.ammoInventory = new AmmoStackHandler(vehicle, ammo);
		this.armorInventory = new ArmourStackHandler(vehicle, armor);
		this.storageInventory = new StorageStackHandler(vehicle, storage);
		this.upgradeInventory = new UpgradeStackHandler(vehicle, upgrade);
	}

	public void writeToNBT(NBTTagCompound commonTag) {
		NBTTagCompound tag = new NBTTagCompound();
		if (this.upgradeInventory != null) {
			NBTTagCompound upgradeTag = new NBTTagCompound();
			this.upgradeInventory.saveToNBT(upgradeTag);
			tag.setTag("upgradeInventory", upgradeTag);
		}
		if (this.ammoInventory != null) {
			NBTTagCompound ammoTag = new NBTTagCompound();
			this.ammoInventory.saveToNBT(ammoTag);
			tag.setTag("ammoInventory", ammoTag);
		}
		if (this.storageInventory != null) {
			NBTTagCompound storageTag = new NBTTagCompound();
			this.storageInventory.saveToNBT(storageTag);
			tag.setTag("storageInventory", storageTag);
		}
		if (this.armorInventory != null) {
			NBTTagCompound armorTag = new NBTTagCompound();
			this.armorInventory.saveToNBT(armorTag);
			tag.setTag("armorInventory", armorTag);
		}
		commonTag.setTag(INVENTORY_TAG, tag);
	}

	public void readFromNBT(NBTTagCompound commonTag) {
		if (!commonTag.hasKey(INVENTORY_TAG)) {
			return;
		}
		NBTTagCompound tag = commonTag.getCompoundTag(INVENTORY_TAG);
		if (this.upgradeInventory != null && tag.hasKey("upgradeInventory")) {
			this.upgradeInventory.loadFromNBT(tag.getCompoundTag("upgradeInventory"));
		}
		if (this.ammoInventory != null && tag.hasKey("ammoInventory")) {
			this.ammoInventory.loadFromNBT(tag.getCompoundTag("ammoInventory"));
		}
		if (this.storageInventory != null && tag.hasKey("storageInventory")) {
			this.storageInventory.loadFromNBT(tag.getCompoundTag("storageInventory"));
		}
		if (this.armorInventory != null && tag.hasKey("armorInventory")) {
			this.armorInventory.loadFromNBT(tag.getCompoundTag("armorInventory"));
		}
	}

	public List<IVehicleArmor> getInventoryArmor() {
		List<IVehicleArmor> armors = new ArrayList<>();
		for (int i = 0; i < this.armorInventory.getSize(); i++) {
			ItemStack stack = this.armorInventory.getStackInSlot(i);
			ArmourRegistry.getArmorForStack(stack).ifPresent(armors::add);
		}
		return armors;
	}

	public List<IVehicleUpgradeType> getInventoryUpgrades() {
		List<IVehicleUpgradeType> upgrades = new ArrayList<>();
		for (int i = 0; i < this.upgradeInventory.getSize(); i++) {
			ItemStack stack = this.upgradeInventory.getStackInSlot(i);
			UpgradeRegistry.getUpgrade(stack).ifPresent(upgrades::add);
		}
		return upgrades;
	}

	public StorageStackHandler getStorageInventory() {
		return this.storageInventory;
	}

	public List<VehicleAmmoEntry> getAmmoCounts() {
		List<VehicleAmmoEntry> counts = new ArrayList<>();
		for (int i = 0; i < this.ammoInventory.getSize(); i++) {
			ItemStack stack = this.ammoInventory.getStackInSlot(i);
			AmmoRegistry.getAmmoForStack(stack).ifPresent(ammo -> {
				boolean found = false;
				for (VehicleAmmoEntry ent : counts) {
					if (ent.baseAmmoType == ammo) {
						found = true;
						ent.ammoCount += stack.stackSize; // Note: stackSize in 1.7.10
						break;
					}
				}
				if (!found) {
					VehicleAmmoEntry entry = new VehicleAmmoEntry(ammo);
					entry.ammoCount += stack.stackSize;
					counts.add(entry);
				}
			});
		}
		return counts;
	}
}
