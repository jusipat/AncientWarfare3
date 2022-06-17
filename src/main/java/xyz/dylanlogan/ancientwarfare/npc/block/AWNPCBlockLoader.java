package xyz.dylanlogan.ancientwarfare.npc.block;

import net.minecraft.block.Block;
import xyz.dylanlogan.ancientwarfare.core.block.AWCoreBlockLoader;
import xyz.dylanlogan.ancientwarfare.core.item.ItemBlockOwnedRotatable;
import xyz.dylanlogan.ancientwarfare.npc.tile.TileTownHall;

public class AWNPCBlockLoader {

    public static Block townHall;

    public static void load() {
        townHall = AWCoreBlockLoader.INSTANCE.register(new BlockTownHall(), "town_hall", ItemBlockOwnedRotatable.class, TileTownHall.class);
    }
}
