package xyz.dylanlogan.ancientwarfare.modeler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.config.Configuration;
import xyz.dylanlogan.ancientwarfare.core.AncientWarfareCore;
import xyz.dylanlogan.ancientwarfare.core.api.ModuleStatus;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;
import xyz.dylanlogan.ancientwarfare.core.config.AWLog;
import xyz.dylanlogan.ancientwarfare.modeler.item.ItemModelEditor;
import xyz.dylanlogan.ancientwarfare.modeler.proxy.CommonProxyModeler;

@Mod
        (
                name = "Ancient Warfare Model Editor",
                modid = "ancientwarfaremodeler",
                version = "@VERSION@",
                dependencies = "required-after:ancientwarfare"
        )

public class AncientWarfareModeler {

    @Instance(value = "ancientwarfaremodeler")
    public static AncientWarfareModeler instance;

    @SidedProxy
            (
                    clientSide = "xyz.dylanlogan.ancientwarfare.modeler.proxy.ClientProxyModeler",
                    serverSide = "xyz.dylanlogan.ancientwarfare.modeler.proxy.CommonProxyModeler"
            )
    public static CommonProxyModeler proxy;

    public static Configuration config;

    public static org.apache.logging.log4j.Logger log;

    public static ItemModelEditor editorOpener;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        ModuleStatus.modelerLoaded = true;
        log = AncientWarfareCore.log;
        config = AWCoreStatics.getConfigFor("ancientwarfaremodeler");

        /**
         * internal registry
         */
        editorOpener = (ItemModelEditor) new ItemModelEditor("editor_opener").setTextureName("ancientwarfare:modeler/editor_opener");
        GameRegistry.registerItem(editorOpener, "editor_opener");
        if (config.hasChanged())
            config.save();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        AWLog.log("Ancient Warfare Modeler Post-Init completed.  Successfully completed all loading stages.");
    }

}
