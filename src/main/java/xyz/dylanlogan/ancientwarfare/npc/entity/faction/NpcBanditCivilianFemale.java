package xyz.dylanlogan.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcBanditCivilianFemale extends NpcFactionCivilian {

    public NpcBanditCivilianFemale(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "bandit.civilian.female";
    }

}
