package xyz.dylanlogan.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcDesertLeader extends NpcFactionLeader {

    public NpcDesertLeader(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "desert.leader";
    }

}
