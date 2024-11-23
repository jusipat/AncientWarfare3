package xyz.dylanlogan.ancientwarfare.vehicle.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.PacketBuffer;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.VehicleBase;

import java.io.IOException;

public class PacketUpgradeUpdate extends PacketVehicleBase {
	private String[] upgradeRegistryNames;
	private String[] armorRegistryNames;

	public PacketUpgradeUpdate() {}

	public PacketUpgradeUpdate(VehicleBase vehicle) {
		super(vehicle);
		upgradeRegistryNames = vehicle.upgradeHelper.serializeUpgrades();
		armorRegistryNames = vehicle.upgradeHelper.serializeInstalledArmors();
	}

	@Override
	protected void writeToStream(ByteBuf data) {
		super.writeToStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		pb.writeInt(upgradeRegistryNames.length);
		for (String upgrade : upgradeRegistryNames) {
			ByteBufUtils.writeUTF8String(pb, upgrade);
		}
		pb.writeInt(armorRegistryNames.length);
		for (String armor : armorRegistryNames) {
			ByteBufUtils.writeUTF8String(pb, armor);
		}
	}

	@Override
	protected void readFromStream(ByteBuf data) {
		super.readFromStream(data);
		PacketBuffer pb = new PacketBuffer(data);
		upgradeRegistryNames = new String[pb.readInt()];
		for (int i = 0; i < upgradeRegistryNames.length; i++) {
			upgradeRegistryNames[i] = ByteBufUtils.readUTF8String(pb);
		}
		armorRegistryNames = new String[pb.readInt()];
		for (int i = 0; i < armorRegistryNames.length; i++) {
			armorRegistryNames[i] = ByteBufUtils.readUTF8String(pb);
		}
	}

	@Override
	public void execute() {
		vehicle.upgradeHelper.updateUpgrades(armorRegistryNames, upgradeRegistryNames);
	}
}
