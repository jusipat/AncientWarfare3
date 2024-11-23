package xyz.dylanlogan.ancientwarfare.core.proxy;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

public class CommonProxyBase {

    public void registerClient() {
        //NOOP for commonProxy
    }

    public EntityPlayer getClientPlayer() {
        //NOOP for commonProxy
        return null;
    }

    public EntityPlayer getFakePlayer(World world, String name, UUID id) {
        EntityPlayer player;
        if(id!=null) {
            player = world.func_152378_a(id);
            if(player!=null)
                return player;
        }
        if(name!=null) {
            player = world.getPlayerEntityByName(name);
            if(player!=null){
                return player;
            }
            return FakePlayerFactory.get((WorldServer) world, new GameProfile(id, name));
        }
        return FakePlayerFactory.get((WorldServer) world, new GameProfile(id, "ancientwarfare"));
    }

    public boolean isKeyPressed(String keyName) {
        return false;
    }

    public void onConfigChanged() {

    }

    public World getWorld(int dimension) {
        return MinecraftServer.getServer().worldServerForDimension(dimension);
    }
}
