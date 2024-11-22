package xyz.dylanlogan.ancientwarfare.vehicle.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;
import xyz.dylanlogan.ancientwarfare.npc.entity.faction.NpcFaction;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.missiles.IAmmo;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAmmoSelect;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAmmoUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketSingleAmmoUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.AmmoRegistry;
import xyz.dylanlogan.ancientwarfare.vehicle.registry.VehicleAmmoEntry;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

public class VehicleAmmoHelper implements IExtendedEntityProperties {

	private static final String CURRENT_AMMO_TYPE_TAG = "currentAmmoType";
	private VehicleBase vehicle;

	private ResourceLocation currentAmmoType = null;

	private NavigableMap<ResourceLocation, VehicleAmmoEntry> ammoEntries = new TreeMap<>();

	public VehicleAmmoHelper(VehicleBase vehicle) {
		this.vehicle = vehicle;
	}

	public int getCountOf(IAmmo type) {
		VehicleAmmoEntry ammo = ammoEntries.get(type.getRegistryName());
		return ammo == null ? 0 : ammo.ammoCount;
	}

	/**
	 * SERVER ONLY relays changes to clients to update a single ammo type, also handles updating underlying inventory...
	 */
	void decreaseCurrentAmmo() throws IOException {
		if (vehicle.worldObj.isRemote) {
			return;
		}
		if (currentAmmoType != null && ammoEntries.containsKey(currentAmmoType)) {
			int removed = Objects.requireNonNull(InventoryTools.removeItems(vehicle.inventory, 0, new ItemStack(AmmoRegistry.getItem(currentAmmoType)), 1)).stackSize;
			VehicleAmmoEntry entry = this.ammoEntries.get(this.currentAmmoType);
			int origCount = entry.ammoCount;
			entry.ammoCount -= removed;
			if (entry.ammoCount < 0) {
				entry.ammoCount = 0;
			}
			if (entry.ammoCount != origCount) {
				NetworkHandler.sendToAllTracking(vehicle, new PacketSingleAmmoUpdate(vehicle, currentAmmoType.toString(), entry.ammoCount));
			}
		}
	}

	/**
	 * get the current EFFECTIVE ammo count (not actual).  Soldiers use this to fool
	 * the vehicle into firing infinite rounds.
	 */
//	public int getCurrentAmmoCount() {
//		if (vehicle.getControllingPassenger() instanceof NpcFaction || AWVehicleStatics.generalSettings.ownedSoldiersUseAmmo && vehicle.getControllingPassenger() instanceof NpcSiegeEngineer) {
//			return 64;
//		}
//		if (currentAmmoType != null && ammoEntries.containsKey(currentAmmoType)) {
//			return this.ammoEntries.get(currentAmmoType).ammoCount;
//		}
//		return 0;
//	}

	public int getCurrentAmmoCount() {
	if (vehicle.ridingEntity instanceof NpcFaction) {
		return 64;
	}
	if (currentAmmoType != null && ammoEntries.containsKey(currentAmmoType)) {
		return this.ammoEntries.get(currentAmmoType).ammoCount;
	}
	return 0;
}

	boolean doesntUseAmmo() {
		return vehicle.vehicleType.getValidAmmoTypes().isEmpty();
	}

	public void addUseableAmmo(IAmmo ammo) {
		VehicleAmmoEntry ent = new VehicleAmmoEntry(ammo);
		this.ammoEntries.put(ammo.getRegistryName(), ent);
	}

	public void setNextAmmo() {
		getAvailable((map, key) -> getHigherWrapped(map, key)).ifPresent(this::setCurrentAmmo);
	}


	private <K, V> Map.Entry<K, V> getHigherWrapped(NavigableMap<K, V> map, K key) {
		Map.Entry<K, V> entry = map.higherEntry(key);
		return entry == null ? map.firstEntry() : entry;
	}

	private void setCurrentAmmo(ResourceLocation registryName) {
		currentAmmoType = registryName;
		handleClientAmmoSelection(currentAmmoType);
	}

	private Optional<ResourceLocation> getAvailable(
			BiFunction<NavigableMap<ResourceLocation, VehicleAmmoEntry>, ResourceLocation, Map.Entry<ResourceLocation, VehicleAmmoEntry>> getNextEntry) {
		if (currentAmmoType == null) {
			return Optional.empty();
		}
		Map.Entry<ResourceLocation, VehicleAmmoEntry> entry = getNextEntry.apply(ammoEntries, currentAmmoType);
		while (entry.getValue().ammoCount <= 0 && !currentAmmoType.equals(entry.getKey())) {
			entry = getNextEntry.apply(ammoEntries, entry.getKey());
		}
		return Optional.of(entry.getKey());
	}

	private <K, V> Map.Entry<K, V> getLowerWrapped(NavigableMap<K, V> map, K key) {
		Map.Entry<K, V> entry = map.lowerEntry(key);
		return entry == null ? map.lastEntry() : entry;
	}

	public void setPreviousAmmo() {
		getAvailable(this::getLowerWrapped).ifPresent(this::setCurrentAmmo);
	}

	public void handleClientAmmoSelection(ResourceLocation ammoRegistryName) {
		NetworkHandler.sendToServer(new PacketAmmoSelect(vehicle, ammoRegistryName.toString()));
	}

	public void updateSelectedAmmo(String ammoRegistryName) throws IOException {
		if (!ammoRegistryName.equals(currentAmmoType == null ? "" : currentAmmoType.toString())) {
			this.currentAmmoType = new ResourceLocation(ammoRegistryName);
			if (!vehicle.worldObj.isRemote) {
				NetworkHandler.sendToAllTracking(vehicle, new PacketAmmoSelect(vehicle, ammoRegistryName));
			}
			float maxPower = vehicle.firingHelper.getAdjustedMaxMissileVelocity();
			if (!vehicle.canAimPower()) {
				vehicle.localLaunchPower = maxPower;
			} else if (vehicle.localLaunchPower > maxPower) {
				vehicle.localLaunchPower = maxPower;
				if (vehicle.worldObj.isRemote && vehicle.firingHelper.clientLaunchSpeed > maxPower) {
					vehicle.firingHelper.clientLaunchSpeed = maxPower;
				}
			}
		}
	}

	public void updateAmmoCount(String registryName, int count) {
		ResourceLocation rl = new ResourceLocation(registryName);
		updateAmmoCount(rl, count);
	}

	private void updateAmmoCount(ResourceLocation registryNameLocation, int count) {
		if (ammoEntries.containsKey(registryNameLocation)) {
			this.ammoEntries.get(registryNameLocation).ammoCount = count;
		}
	}

	public void updateAmmoCounts() throws IOException {
		if (vehicle.worldObj.isRemote) {
			return;
		}

		for (VehicleAmmoEntry ent : this.ammoEntries.values()) {
			ent.ammoCount = 0;
		}
		List<VehicleAmmoEntry> counts = vehicle.inventory.getAmmoCounts();

		for (VehicleAmmoEntry count : counts) {
			updateAmmoCount(count.baseAmmoType.getRegistryName(), count.ammoCount);
			if (currentAmmoType == null && count.ammoCount > 0) {
				updateSelectedAmmo(count.baseAmmoType.getRegistryName().toString());
			}
		}
		PacketAmmoUpdate pkt = new PacketAmmoUpdate(vehicle, serializeAmmo(new NBTTagCompound()));
		NetworkHandler.sendToAllTracking(vehicle, pkt);
	}

//	@Nullable //TODO replace with optional
//	public IAmmo getCurrentAmmoType() {
//		if (currentAmmoType != null && ammoEntries.containsKey(currentAmmoType)) {
//			VehicleAmmoEntry entry = this.ammoEntries.get(currentAmmoType);
//			if (entry != null && (!(vehicle.getControllingPassenger() instanceof NpcBase) || entry.ammoCount > 0)) {
//				return entry.baseAmmoType;
//			}
//		}
//		if (vehicle.getControllingPassenger() instanceof NpcFaction || !AWVehicleStatics.generalSettings.ownedSoldiersUseAmmo && vehicle.getControllingPassenger() instanceof NpcSiegeEngineer) {
//			NpcBase npc = (NpcBase) vehicle.getControllingPassenger();
//			return vehicle.vehicleType.getAmmoForSoldierRank(npc.getLevelingStats().getLevel());
//		}
//		return null;
//	}

	@Nullable //TODO replace with optional
	public IAmmo getCurrentAmmoType() {
		if (currentAmmoType != null && ammoEntries.containsKey(currentAmmoType)) {
			VehicleAmmoEntry entry = this.ammoEntries.get(currentAmmoType);
			if (entry != null && (!(vehicle.ridingEntity instanceof NpcBase) || entry.ammoCount > 0)) {
				return entry.baseAmmoType;
			}
		}
		if (vehicle.ridingEntity instanceof NpcFaction || !AWVehicleStatics.generalSettings.ownedSoldiersUseAmmo) {
			NpcBase npc = (NpcBase) vehicle.ridingEntity;
			return vehicle.vehicleType.getAmmoForSoldierRank(npc.getLevelingStats().getLevel());
		}
		return null;
	}

	private NBTTagCompound serializeAmmo(NBTTagCompound tag) {
		NBTTagList tagList = new NBTTagList();
		for (VehicleAmmoEntry ent : this.ammoEntries.values()) {
			NBTTagCompound entryTag = new NBTTagCompound();
			entryTag.setString("type", ent.baseAmmoType.getRegistryName().toString());
			entryTag.setInteger("count", ent.ammoCount);
			tagList.appendTag(entryTag);
		}
		tag.setTag("list", tagList);
		return tag;
	}

	public void updateAmmo(NBTTagCompound tag) {
		for (VehicleAmmoEntry ent : this.ammoEntries.values()) {
			ent.ammoCount = 0;
		}
		deserializeAmmo(tag);
	}

	private void deserializeAmmo(NBTTagCompound tag) {
		NBTTagList ammo = tag.getTagList("list", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < ammo.tagCount(); i++) {
			NBTTagCompound entryTag = ammo.getCompoundTagAt(i);
			String type = entryTag.getString("type");
			int count = entryTag.getInteger("count");
			updateAmmoCount(type, count);
		}
	}

	@Override
	public void saveNBTData(NBTTagCompound tag) {
		if (currentAmmoType != null) {
			tag.setString(CURRENT_AMMO_TYPE_TAG, currentAmmoType.toString());
		}
		serializeAmmo(tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		for (VehicleAmmoEntry ent : this.ammoEntries.values()) {
			ent.ammoCount = 0;
		}
		if (tag.hasKey(CURRENT_AMMO_TYPE_TAG)) {
			currentAmmoType = new ResourceLocation(tag.getString(CURRENT_AMMO_TYPE_TAG));
		}
		deserializeAmmo(tag);
	}

	@Override
	public void init(Entity entity, World world) {

	}
}
