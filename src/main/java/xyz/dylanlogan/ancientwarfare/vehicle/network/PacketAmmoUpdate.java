package xyz.dylanlogan.ancientwarfare.vehicle.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import java.io.IOException;

public class PacketAmmoUpdate extends PacketVehicleBase {
	private NBTTagCompound ammoTag;

	public PacketAmmoUpdate() {}

	public PacketAmmoUpdate(VehicleBase vehicle, NBTTagCompound ammoTag) {
		super(vehicle);
		this.ammoTag = ammoTag;
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		super.writeToStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ByteBufUtils.writeTag(pb, ammoTag);
	}

	@Override
	protected void readFromStream(ByteBuf data) {
		super.readFromStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ammoTag = ByteBufUtils.readTag(pb);
	}

	@Override
	public void execute() {
		vehicle.ammoHelper.updateAmmo(ammoTag);
	}
}
