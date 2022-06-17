package xyz.dylanlogan.ancientwarfare.structure.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.structure.item.ItemStructureSettings;

public class ContainerStructureSelection extends ContainerStructureSelectionBase {

    private ItemStructureSettings buildSettings;

    public ContainerStructureSelection(EntityPlayer player, int x, int y, int z) {
        super(player);
        buildSettings = ItemStructureSettings.getSettingsFor(player.getHeldItem());
        structureName = buildSettings.hasName() ? buildSettings.name() : null;
        addPlayerSlots();
        removeSlots();
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (!player.worldObj.isRemote && tag.hasKey("structName")) {
            buildSettings = ItemStructureSettings.getSettingsFor(player.getHeldItem());
            buildSettings.setName(tag.getString("structName"));
            ItemStructureSettings.setSettingsFor(player.getHeldItem(), buildSettings);
        }
    }

}
