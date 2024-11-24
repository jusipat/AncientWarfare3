package xyz.dylanlogan.ancientwarfare.vehicle;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.dylanlogan.ancientwarfare.core.api.AWItems;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.network.PacketBase;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.container.ContainerVehicle;
import xyz.dylanlogan.ancientwarfare.vehicle.container.ContainerVehicleInventory;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleEntities;
import xyz.dylanlogan.ancientwarfare.vehicle.init.AWVehicleItems;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAimUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAmmoSelect;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketAmmoUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketFireUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketPackCommand;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketSingleAmmoUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketTurretAnglesUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketUpgradeUpdate;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketVehicleInput;
import xyz.dylanlogan.ancientwarfare.vehicle.network.PacketVehicleMove;
import xyz.dylanlogan.ancientwarfare.vehicle.proxy.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = "Ancient Warfare Vehicles", modid = AncientWarfareVehicles.MOD_ID, version = "@VERSION@", dependencies = "required-after:ancientwarfare")

public class AncientWarfareVehicles {
	public static final String MOD_ID = "ancientwarfarevehicle";

	public static final CreativeTabs TAB = new CreativeTabs("tabs.awcore") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return AWItems.roughWood;
		}
	};

	@Mod.Instance(value = MOD_ID)
	public static AncientWarfareVehicles instance;

	@SidedProxy(clientSide = "xyz.dylanlogan.ancientwarfare.vehicle.proxy.ClientProxy", serverSide = "xyz.dylanlogan.ancientwarfare.vehicle.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static AWVehicleStatics statics;

	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		statics = new AWVehicleStatics("ancientwarfarevehicle");

		AWVehicleItems.INSTANCE.load();
		AWVehicleEntities.INSTANCE.load();

//		PacketBase.registerPacketType(NetworkHandler.PACKET_AIM_UPDATE, PacketAimUpdate.class, PacketAimUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_AMMO_SELECT, PacketAmmoSelect.class, PacketAmmoSelect::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_AMMO_UPDATE, PacketAmmoUpdate.class, PacketAmmoUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_FIRE_UPDATE, PacketFireUpdate.class, PacketFireUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_PACK_COMMAND, PacketPackCommand.class, PacketPackCommand::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_SINGLE_AMMO_UPDATE, PacketSingleAmmoUpdate.class, PacketSingleAmmoUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_TURRET_ANGLES_UPDATE, PacketTurretAnglesUpdate.class, PacketTurretAnglesUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_UPGRADE_UPDATE, PacketUpgradeUpdate.class, PacketUpgradeUpdate::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_VEHICLE_INPUT, PacketVehicleInput.class, PacketVehicleInput::new);
//		PacketBase.registerPacketType(NetworkHandler.PACKET_VEHICLE_MOVE, PacketVehicleMove.class, PacketVehicleMove::new);
//
//		NetworkHandler.registerContainer(NetworkHandler.GUI_VEHICLE_INVENTORY, ContainerVehicleInventory.class);
//		NetworkHandler.registerContainer(NetworkHandler.GUI_VEHICLE_AMMO_SELECTION, ContainerVehicle.class);

		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		proxy.init();

		statics.save();
	}
}
