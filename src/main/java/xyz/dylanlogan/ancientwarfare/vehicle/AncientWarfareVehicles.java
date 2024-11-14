package xyz.dylanlogan.ancientwarfare.vehicle;

import net.minecraftforge.common.config.Configuration;
import xyz.dylanlogan.ancientwarfare.core.api.ModuleStatus;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;
import xyz.dylanlogan.ancientwarfare.vehicle.config.AWVehicleStatics;
import xyz.dylanlogan.ancientwarfare.vehicle.crafting.AWVehicleCrafting;
import xyz.dylanlogan.ancientwarfare.vehicle.entity.AWVehicleEntityLoader;
import xyz.dylanlogan.ancientwarfare.vehicle.proxy.VehicleCommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod
(
name = "Ancient Warfare Vehicles",
modid = AncientWarfareVehicles.MOD_ID,
version = "@VERSION@",
dependencies = "required-after:AncientWarfare"
)

public class AncientWarfareVehicles 
{
  public static final String MOD_ID = "ancientwarfarevehicle";
@Instance(value=MOD_ID)
public static AncientWarfareVehicles instance;

@SidedProxy
(
clientSide = "xyz.dylanlogan.ancientwarfare.vehicle.proxy.VehicleClientProxy",
serverSide = "xyz.dylanlogan.ancientwarfare.vehicle.proxy.VehicleCommonProxy"
)
public static VehicleCommonProxy proxy;

public static Configuration config;

public static AWVehicleStatics statics;

@EventHandler
public void preInit(FMLPreInitializationEvent evt)
  {
  AWLog.log("Ancient Warfare Vehicles Pre-Init started");
  
  ModuleStatus.vehiclesLoaded = true; 
  
  /**
   * setup module-owned config file and config-access class
   */
  config = AWCoreStatics.getConfigFor("AncientWarfareVehicle");
  statics = new AWVehicleStatics(config);
    
  /**
   * load pre-init (items, blocks, entities)
   */  
  proxy.registerClient();
  statics.load();//load config settings
  AWVehicleEntityLoader.load();
      
  /**
   * register tick-handlers
   */
  AWLog.log("Ancient Warfare Vehicles Pre-Init completed");
  }

@EventHandler
public void init(FMLInitializationEvent evt)
  {
  AWLog.log("Ancient Warfare Vehicles Init started"); 
  
  /**
   * construct recipes, load plugins
   */
  AWVehicleCrafting.loadRecipes();
  AWLog.log("Ancient Warfare Vehicles Init completed");
  }

@EventHandler
public void postInit(FMLPostInitializationEvent evt)
  {
  AWLog.log("Ancient Warfare Vehicles Post-Init started"); 
   /**
    * save config for any changes that were made during loading stages
    */
  config.save();
  AWLog.log("Ancient Warfare Vehicles Post-Init completed.  Successfully completed all loading stages.");
  }

}
