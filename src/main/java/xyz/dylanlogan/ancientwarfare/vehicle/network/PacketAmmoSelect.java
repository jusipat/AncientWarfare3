package xyz.dylanlogan.ancientwarfare.vehicle.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import java.io.IOException;

public class PacketAmmoSelect extends PacketVehicleBase {
	private String ammoRegistryName;

	public PacketAmmoSelect() {}

	public PacketAmmoSelect(VehicleBase vehicle, String ammoRegistryName) {
		super(vehicle);
		this.ammoRegistryName = ammoRegistryName;
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		super.writeToStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ByteBufUtils.writeUTF8String(pb, ammoRegistryName);
	}

	@Override
	protected void readFromStream(ByteBuf data) {
		super.readFromStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		ammoRegistryName = ByteBufUtils.readUTF8String(pb);

	}

	@Override
	public void execute() {
		vehicle.ammoHelper.updateSelectedAmmo(ammoRegistryName);
		super.execute();
	}
}
