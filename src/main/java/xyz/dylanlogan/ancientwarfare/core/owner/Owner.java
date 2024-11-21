package xyz.dylanlogan.ancientwarfare.core.owner;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.UUID;

@Immutable
public class Owner {
	public static final Owner EMPTY = new Owner();

	private static final String OWNER_NAME_TAG = "ownerName";
	private static final String OWNER_ID_TAG = "ownerId";
	private final UUID uuid;
	private final String name;

	private Owner() {
		this(new UUID(0, 0), "");
	}

	private Owner(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	public Owner(EntityPlayer player) {
		this(player.getUniqueID(), player.getDisplayName());
	}

	public Owner(ByteBuf buffer) {
		this(new UUID(buffer.readLong(), buffer.readLong()), ByteBufUtils.readUTF8String(buffer));
	}

	public Owner(World world, String name) {
		EntityPlayer player = world.getPlayerEntityByName(name);
		uuid = player != null ? player.getUniqueID() : new UUID(0, 0);
		this.name = name;
	}

	public boolean isOwnerOrSameTeamOrFriend(@Nullable Entity entity) {
		// check our own implementation of the ownable entities
		if (entity instanceof IOwnable) {
			Owner owner = ((IOwnable) entity).getOwner();
			return isOwnerOrSameTeamOrFriend(entity.worldObj, owner.getUUID(), owner.getName());
		}
		// check if entity implements vanilla interface if the entity is ownable & player is the owner
		if (entity instanceof IEntityOwnable && ((IEntityOwnable) entity).getOwner() != null) {
			Entity owner = ((IEntityOwnable) entity).getOwner();
			return isOwnerOrSameTeamOrFriend(entity.worldObj, owner.getUniqueID(), owner.getCommandSenderName());
		}
		return entity != null && isOwnerOrSameTeamOrFriend(entity.worldObj, entity.getUniqueID(), entity.getCommandSenderName());
	}

	public boolean isOwnerOrSameTeamOrFriend(World world, @Nullable UUID playerId, String playerName) {
		return TeamViewerRegistry.areFriendly(world, uuid, playerId, name, playerName);
	}

	public String getName() {
		return name;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void serializeToBuffer(ByteBuf buffer) {
		buffer.writeLong(uuid.getMostSignificantBits());
		buffer.writeLong(uuid.getLeastSignificantBits());
		ByteBufUtils.writeUTF8String(buffer, name);
	}

	public NBTTagCompound serializeToNBT(NBTTagCompound tag) {
		if (this == EMPTY) {
			return tag;
		}
		tag.setString(OWNER_NAME_TAG, name);
		tag.setLong(OWNER_ID_TAG + "_most", uuid.getMostSignificantBits());
		tag.setLong(OWNER_ID_TAG + "_least", uuid.getLeastSignificantBits());
		return tag;
	}

	public static Owner deserializeFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(OWNER_NAME_TAG)) {
			long mostSigBits = tag.getLong(OWNER_ID_TAG + "_most");
			long leastSigBits = tag.getLong(OWNER_ID_TAG + "_least");
			return new Owner(new UUID(mostSigBits, leastSigBits), tag.getString(OWNER_NAME_TAG));
		}
		return Owner.EMPTY;
	}

	public boolean playerHasCommandPermissions(World world, UUID playerId, String playerName) {
		return this != Owner.EMPTY && TeamViewerRegistry.areTeamMates(world, uuid, playerId, name, playerName);
	}

}
