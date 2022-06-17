package xyz.dylanlogan.ancientwarfare.structure.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class StructureBoundingBoxRenderer {

    public static StructureBoundingBoxRenderer INSTANCE = new StructureBoundingBoxRenderer();

    private StructureBoundingBoxRenderer() {
    }

    @SubscribeEvent
    public void handleRenderLastEvent(RenderWorldLastEvent evt) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) {
            return;
        }
        EntityPlayer player = mc.thePlayer;
        if (player == null) {
            return;
        }
        ItemStack stack = player.getCurrentEquippedItem();
        Item item;
        if (stack == null || (item = stack.getItem()) == null) {
            return;
        }
        if (item instanceof IBoxRenderer) {
            ((IBoxRenderer) item).renderBox(player, stack, evt.partialTicks);
        }
    }
}
