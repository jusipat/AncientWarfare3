package xyz.dylanlogan.ancientwarfare.structure.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import xyz.dylanlogan.ancientwarfare.core.api.AWBlocks;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.proxy.ClientProxyBase;
import xyz.dylanlogan.ancientwarfare.core.render.TileCraftingTableRender;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;
import xyz.dylanlogan.ancientwarfare.structure.event.StructureBoundingBoxRenderer;
import xyz.dylanlogan.ancientwarfare.structure.gui.*;
import xyz.dylanlogan.ancientwarfare.structure.model.ModelDraftingStation;
import xyz.dylanlogan.ancientwarfare.structure.render.RenderGateHelper;
import xyz.dylanlogan.ancientwarfare.structure.render.RenderStructureBuilder;
import xyz.dylanlogan.ancientwarfare.structure.tile.TileDraftingStation;
import xyz.dylanlogan.ancientwarfare.structure.tile.TileStructureBuilder;

public class ClientProxyStructures extends ClientProxyBase {

    @Override
    public void registerClient() {
        NetworkHandler.registerGui(NetworkHandler.GUI_SCANNER, GuiStructureScanner.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_BUILDER, GuiStructureSelection.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SPAWNER, GuiSpawnerPlacer.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SPAWNER_ADVANCED, GuiSpawnerAdvanced.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SPAWNER_ADVANCED_BLOCK, GuiSpawnerAdvanced.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SPAWNER_ADVANCED_INVENTORY, GuiSpawnerAdvancedInventory.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SPAWNER_ADVANCED_BLOCK_INVENTORY, GuiSpawnerAdvancedInventory.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_GATE_CONTROL, GuiGateControl.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_DRAFTING_STATION, GuiDraftingStation.class);
        NetworkHandler.registerGui(NetworkHandler.GUI_SOUND_BLOCK, GuiSoundBlock.class);
        MinecraftForge.EVENT_BUS.register(StructureBoundingBoxRenderer.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityGate.class, new RenderGateHelper());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStructureBuilder.class, new RenderStructureBuilder());
        TileCraftingTableRender render = new TileCraftingTableRender(new ModelDraftingStation(), "textures/model/structure/tile_drafting_station.png");
        ClientRegistry.bindTileEntitySpecialRenderer(TileDraftingStation.class, render);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AWBlocks.draftingStation), render);
    }
}
