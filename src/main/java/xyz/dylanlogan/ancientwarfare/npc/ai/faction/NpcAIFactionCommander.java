package xyz.dylanlogan.ancientwarfare.npc.ai.faction;

import net.minecraft.potion.PotionEffect;
import xyz.dylanlogan.ancientwarfare.npc.ai.owned.NpcAIPlayerOwnedCommander;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;
import xyz.dylanlogan.ancientwarfare.npc.entity.faction.NpcFaction;
import xyz.dylanlogan.ancientwarfare.npc.entity.faction.NpcFactionLeader;

import java.util.Locale;

public class NpcAIFactionCommander extends NpcAIPlayerOwnedCommander {

    public NpcAIFactionCommander(NpcFaction npc) {
        super(npc);
        if(npc.getNpcType().toLowerCase(Locale.ENGLISH).endsWith(".elite"))
            effect = new PotionEffect(effect.getPotionID(), effect.getDuration(), 1);
    }

    @Override
    protected boolean isCommander(NpcBase npc) {
        return npc instanceof NpcFactionLeader;
    }

    @Override
    protected boolean canBeCommanded(NpcBase npc){
        return npc instanceof NpcFaction && ((NpcFaction) npc).getFaction().equals(((NpcFaction) this.npc).getFaction());
    }
}
