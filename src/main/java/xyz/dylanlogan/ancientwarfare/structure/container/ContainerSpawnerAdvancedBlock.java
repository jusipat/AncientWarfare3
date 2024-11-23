package xyz.dylanlogan.ancientwarfare.structure.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.structure.tile.TileAdvancedSpawner;

import java.io.IOException;

public class ContainerSpawnerAdvancedBlock extends ContainerSpawnerAdvancedBase {

    private final TileAdvancedSpawner spawner;

    public ContainerSpawnerAdvancedBlock(EntityPlayer player, int x, int y, int z) {
        super(player);
        TileEntity te = player.worldObj.getTileEntity(x, y, z);
        if (te instanceof TileAdvancedSpawner) {
            spawner = (TileAdvancedSpawner) te;
            settings = spawner.getSettings();
        } else {
            throw new IllegalArgumentException("Spawner not found");
        }
    }

    @Override
    public void sendInitData() {
        if (!spawner.getWorldObj().isRemote) {
            NetworkHandler.sendToPlayer((EntityPlayerMP) player, getSettingPacket());
        }
    }

    @Override
    public void handlePacketData(NBTTagCompound tag) {
        if (tag.hasKey("spawnerSettings")) {
            if (spawner.getWorldObj().isRemote) {
                settings.readFromNBT(tag.getCompoundTag("spawnerSettings"));
                this.refreshGui();
            } else {
                spawner.getSettings().readFromNBT(tag.getCompoundTag("spawnerSettings"));
                spawner.markDirty();
                spawner.getWorldObj().markBlockForUpdate(spawner.xCoord, spawner.yCoord, spawner.zCoord);
            }
        }
    }
}
