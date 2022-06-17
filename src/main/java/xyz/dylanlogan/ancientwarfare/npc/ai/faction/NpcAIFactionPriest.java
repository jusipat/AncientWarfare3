package xyz.dylanlogan.ancientwarfare.npc.ai.faction;

import xyz.dylanlogan.ancientwarfare.npc.ai.NpcAIMedicBase;
import xyz.dylanlogan.ancientwarfare.npc.entity.NpcBase;

public class NpcAIFactionPriest extends NpcAIMedicBase {

    public NpcAIFactionPriest(NpcBase npc) {
        super(npc);
    }

    @Override
    protected boolean isProperSubtype() {
        return true;
    }

}
