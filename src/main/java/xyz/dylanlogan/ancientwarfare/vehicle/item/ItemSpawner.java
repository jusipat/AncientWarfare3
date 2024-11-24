package xyz.dylanlogan.ancientwarfare.vehicle.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.vehicle.AncientWarfareVehicles;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.IVehicleType;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.types.VehicleType;

import java.util.List;
import java.util.Optional;

public class ItemSpawner extends ItemBaseVehicle {
	private static final String LEVEL_TAG = "level";
	private static final String HEALTH_TAG = "health";
	private static final String SPAWN_DATA_TAG = "spawnData";

	public ItemSpawner() {
		super();
		setHasSubtypes(true);
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack == null || !itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey(SPAWN_DATA_TAG)) {
			AncientWarfareVehicles.LOG.error("Vehicle spawner item was missing NBT data");
			return itemStack;
		}
		if (!world.isRemote) {
			boolean success = rayTraceAndSpawnVehicle(world, player, itemStack);
			if (!success) {
				AncientWarfareVehicles.LOG.warn("Failed to spawn vehicle.");
			}
		}
		return itemStack;
	}

	private boolean rayTraceAndSpawnVehicle(World world, EntityPlayer player, ItemStack stack) {
		//noinspection ConstantConditions
		NBTTagCompound tag = stack.stackTagCompound.getCompoundTag(SPAWN_DATA_TAG);
		int level = tag.getInteger(LEVEL_TAG);
		Optional<VehicleBase> v = VehicleType.getVehicleForType(world, stack.getItemDamage(), level);
		if (!v.isPresent()) {
			return false;
		}
		VehicleBase vehicle = v.get();
		if (tag.hasKey(HEALTH_TAG)) {
			vehicle.setHealth(tag.getFloat(HEALTH_TAG));
		}
		MovingObjectPosition rayTrace = player.rayTrace(200.0,1.0F);
		//noinspection ConstantConditions
		if (rayTrace == null || rayTrace.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
			return true;
		}
		spawnVehicle(world, player, vehicle, rayTrace);
		updateSpawnerStackCount(player, stack);
		return false;
	}

	private void updateSpawnerStackCount(EntityPlayer player, ItemStack stack) {
		if (!player.capabilities.isCreativeMode) {
			stack.stackSize--;
			if (stack.stackSize <= 0) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		}
	}

	private void spawnVehicle(World world, EntityPlayer player, VehicleBase vehicle, MovingObjectPosition rayTrace) {
		Vec3 hitVec = rayTrace.hitVec;
		int sideHit = rayTrace.sideHit;
		if (sideHit == 4 || sideHit == 5 || sideHit == 2 || sideHit == 3) { // WEST, EAST, NORTH, SOUTH
			float halfWidth = vehicle.width / 2f;
			double offsetX = (sideHit == 4 ? -halfWidth : (sideHit == 5 ? halfWidth : 0));
			double offsetZ = (sideHit == 2 ? -halfWidth : (sideHit == 3 ? halfWidth : 0));
			hitVec = Vec3.createVectorHelper(hitVec.xCoord + offsetX, hitVec.yCoord, hitVec.zCoord + offsetZ);
		}

		vehicle.setPosition(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
		vehicle.prevRotationYaw = vehicle.rotationYaw = -player.rotationYaw + 180;
		vehicle.localTurretDestRot = vehicle.localTurretRotation = vehicle.localTurretRotationHome = vehicle.rotationYaw;
		if (AWVehicleStatics.generalSettings.useVehicleSetupTime) {
			vehicle.setSetupState(true, 100);
		}
		world.spawnEntityInWorld(vehicle);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
		super.addInformation(stack, player, list, advanced);

		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey(SPAWN_DATA_TAG)) {
			NBTTagCompound tag = stack.stackTagCompound.getCompoundTag(SPAWN_DATA_TAG);

			int level = tag.getInteger(LEVEL_TAG);
			list.add("Material Level: " + level); // TODO: Add translations for localization

			if (tag.hasKey(HEALTH_TAG)) {
				list.add("Vehicle Health: " + tag.getFloat(HEALTH_TAG));
			}

			Optional<VehicleBase> vehicle = VehicleType.getVehicleForType(null, stack.getItemDamage(), level);
			if (vehicle.isPresent()) {
				List<String> tooltip = vehicle.get().vehicleType.getDisplayTooltip();
				for (String line : tooltip) {
					list.add(StatCollector.translateToLocal(line));
				}
			}
		}
	}



	//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
//		super.addInformation(stack, world, tooltip, flagIn);
//		//noinspection ConstantConditions
//		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(SPAWN_DATA_TAG)) {
//			NBTTagCompound tag = stack.getTagCompound().getCompoundTag(SPAWN_DATA_TAG);
//			int level = tag.getInteger(LEVEL_TAG);
//			tooltip.add("Material Level: " + level);//TODO additional translations
//			if (tag.hasKey(HEALTH_TAG)) {
//				tooltip.add("Vehicle Health: " + tag.getFloat(HEALTH_TAG));
//			}
//
//			Optional<VehicleBase> v = VehicleType.getVehicleForType(world, stack.getItemDamage(), level);
//			if (!v.isPresent()) {
//				return;
//			}
//			tooltip.addAll(v.get().vehicleType.getDisplayTooltip().stream().map(I18n::format).collect(Collectors.toSet()));
//		}
//	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		IVehicleType vehicle = VehicleType.vehicleTypes[stack.getItemDamage()];
		return vehicle == null ? "" : vehicle.getDisplayName();
	}

//	@Override
//	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
//		if (!isInCreativeTab(tab)) {
//			return;
//		}
//		items.addAll(VehicleType.getCreativeDisplayItems());
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerClient() {
//		ResourceLocation baseLocation = new ResourceLocation(AncientWarfareCore.modID, "vehicle/" + getRegistryName().getResourcePath());
//		String modelPropString = "variant=%s";
//
//		ModelLoader.setCustomMeshDefinition(this, stack -> {
//			if (stack.hasTagCompound()) {
//				//noinspection ConstantConditions
//				int level = stack.getTagCompound().getCompoundTag(SPAWN_DATA_TAG).getInteger(LEVEL_TAG);
//				return new ModelResourceLocation(baseLocation, String.format(modelPropString, VehicleType.getVehicleType(stack.getMetadata()).getConfigName() + "_" + level));
//			}
//			return new ModelResourceLocation(baseLocation, String.format(modelPropString, "catapult_stand_0"));
//		});
//
//		for (IVehicleType type : VehicleType.vehicleTypes) {
//			if (type == null || type.getMaterialType() == null || !type.isEnabled()) {
//				continue;
//			}
//			for (int level = 0; level < type.getMaterialType().getNumOfLevels(); level++) {
//				ModelLoader.registerItemVariants(this,
//						new ModelResourceLocation(baseLocation, String.format(modelPropString, type.getConfigName() + "_" + level)));
//			}
//		}
//	}
}
