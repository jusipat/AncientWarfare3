package xyz.dylanlogan.ancientwarfare.modeler.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerBase;
import xyz.dylanlogan.ancientwarfare.modeler.gui.GuiModelEditor;

public class ClientProxyModeler extends CommonProxyModeler {

    public ClientProxyModeler() {

    }

    @Override
    public void openGui(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiModelEditor(new ContainerBase(player)));
    }

}
