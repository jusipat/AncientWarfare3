package xyz.dylanlogan.ancientwarfare.structure.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xyz.dylanlogan.ancientwarfare.core.container.ContainerEntityBase;
import xyz.dylanlogan.ancientwarfare.structure.entity.EntityGate;

public class ContainerGateControl extends ContainerEntityBase<EntityGate> {

    public ContainerGateControl(EntityPlayer player, int x, int y, int z) {
        super(player, x);
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (tag.hasKey("repack")) {
            entity.repackEntity();
        }
    }

    public void repackGate() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("repack", true);
        sendDataToServer(tag);
    }
}
