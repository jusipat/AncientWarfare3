package xyz.dylanlogan.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcNativeArcherElite extends NpcFactionArcher {

    public NpcNativeArcherElite(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "native.archer.elite";
    }

}
