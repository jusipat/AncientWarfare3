package xyz.dylanlogan.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcPirateTrader extends NpcFactionTrader {

    public NpcPirateTrader(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "pirate.trader";
    }

}
