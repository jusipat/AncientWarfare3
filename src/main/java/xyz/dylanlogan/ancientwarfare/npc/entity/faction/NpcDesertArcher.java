package xyz.dylanlogan.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcDesertArcher extends NpcFactionArcher {

    public NpcDesertArcher(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "desert.archer";
    }

}
