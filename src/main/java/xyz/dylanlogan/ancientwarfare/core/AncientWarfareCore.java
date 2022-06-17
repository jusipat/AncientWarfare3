package xyz.dylanlogan.ancientwarfare.core;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import xyz.dylanlogan.ancientwarfare.core.block.AWCoreBlockLoader;
import xyz.dylanlogan.ancientwarfare.core.command.CommandResearch;
import xyz.dylanlogan.ancientwarfare.core.config.AWCoreStatics;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBackpack;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerEngineeringStation;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerResearchBook;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerResearchStation;
import xyz.dylanlogan.ancientwarfare.core.crafting.AWCoreCrafting;
import xyz.dylanlogan.ancientwarfare.core.item.AWCoreItemLoader;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.proxy.CommonProxyBase;
import xyz.dylanlogan.ancientwarfare.core.research.ResearchGoal;
import xyz.dylanlogan.ancientwarfare.core.research.ResearchTracker;

@Mod
        (
                name = "Ancient Warfare Core",
                modid = AncientWarfareCore.modID,
                version = "@VERSION@",
                guiFactory = "xyz.dylanlogan.ancientwarfare.core.gui.options.OptionsGuiFactory"
        )
public class AncientWarfareCore {

    public static final String modID = "AncientWarfare";

    @Instance(value = AncientWarfareCore.modID)
    public static AncientWarfareCore instance;

    @SidedProxy
            (
                    clientSide = "xyz.dylanlogan.ancientwarfare.core.proxy.ClientProxy",
                    serverSide = "xyz.dylanlogan.ancientwarfare.core.proxy.CommonProxyBase"
            )
    public static CommonProxyBase proxy;

    public static org.apache.logging.log4j.Logger log;

    public static AWCoreStatics statics;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        /**
         * setup config file and logger
         */
        log = evt.getModLog();
        statics = new AWCoreStatics("AncientWarfare");

        /**
         * register blocks, items, tile entities, and entities
         */
        AWCoreBlockLoader.INSTANCE.load();
        AWCoreItemLoader.INSTANCE.load();

        /**
         * register server-side network handler and anything that needs loaded on the event busses
         */
        NetworkHandler.INSTANCE.registerNetwork();//register network handler, server side
        FMLCommonHandler.instance().bus().register(ResearchTracker.INSTANCE);
        FMLCommonHandler.instance().bus().register(this);


        /**
         * register GUIs, containers, client-side network handler, renderers
         */
        proxy.registerClient();
        NetworkHandler.registerContainer(NetworkHandler.GUI_CRAFTING, ContainerEngineeringStation.class);
        NetworkHandler.registerContainer(NetworkHandler.GUI_RESEARCH_STATION, ContainerResearchStation.class);
        NetworkHandler.registerContainer(NetworkHandler.GUI_BACKPACK, ContainerBackpack.class);
        NetworkHandler.registerContainer(NetworkHandler.GUI_RESEARCH_BOOK, ContainerResearchBook.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        /**
         * initialize any other core module information
         */
        ResearchGoal.initializeResearch();
        /**
         * register recipes
         */
        AWCoreCrafting.loadRecipes();
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
        statics.save();
    }

    @EventHandler
    public void serverStartingEvent(FMLServerStartingEvent evt) {
        evt.registerServerCommand(new CommandResearch());
    }

    @SubscribeEvent
    public void configChangedEvent(OnConfigChangedEvent evt) {
        if (modID.equals(evt.modID)) {
            statics.save();
            proxy.onConfigChanged();
        }
    }
}
