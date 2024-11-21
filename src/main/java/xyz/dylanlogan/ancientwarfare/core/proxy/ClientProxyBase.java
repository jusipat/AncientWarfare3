package xyz.dylanlogan.ancientwarfare.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.input.InputHandler;

public class ClientProxyBase extends CommonProxyBase {

    @Override
    public void registerClient() {

    }

    @Override
    public final EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public final boolean isKeyPressed(String keyName) {
        KeyBinding kb = InputHandler.getKeybind(keyName);
        return kb != null && kb.isPressed();
    }

    @Override
    public final World getWorld(int dimension) {
        if(Minecraft.getMinecraft().theWorld.provider.dimensionId == dimension){
            return Minecraft.getMinecraft().theWorld;
        }
        return null;
    }
}
