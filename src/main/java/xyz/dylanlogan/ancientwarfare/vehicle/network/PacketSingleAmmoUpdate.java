package xyz.dylanlogan.ancientwarfare.vehicle.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import java.io.IOException;

public class PacketSingleAmmoUpdate extends PacketVehicleBase {
	private String ammoRegistryName;
	private int count;

	public PacketSingleAmmoUpdate() {}

	public PacketSingleAmmoUpdate(VehicleBase vehicle, String ammoRegistryName, int count) {
		super(vehicle);
		this.ammoRegistryName = ammoRegistryName;
		this.count = count;
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		super.writeToStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ByteBufUtils.writeUTF8String(pb, ammoRegistryName);
		pb.writeInt(count);
	}

	@Override
	protected void readFromStream(ByteBuf data) {
		super.readFromStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ammoRegistryName = ByteBufUtils.readUTF8String(pb);
		count = pb.readInt();
	}

	@Override
	public void execute() {
		vehicle.ammoHelper.updateAmmoCount(ammoRegistryName, count);
	}
}
