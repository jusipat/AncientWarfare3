package xyz.dylanlogan.ancientwarfare.nei_plugin;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;

@Mod
        (
                name = "Ancient Warfare NEI Plugin",
                modid = "AncientWarfareNEIPlugin",
                version = "@VERSION@",
                dependencies = "required-after:AncientWarfare;after:NotEnoughItems"
        )
public class AncientWarfareNEIPlugin {

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            if (evt.getSide().isClient()) {
                AWLog.log("NEI Detected, attempting load of NEI Plugin");
                try {
                    Class.forName("xyz.dylanlogan.ancientwarfare.nei_plugin.AWNeiRecipeHandler").newInstance();
                    AWLog.log("NEI Plugin loaded successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else
            AWLog.log("Skipping loading of NEI plugin, NEI not found!");
    }
}
