package xyz.dylanlogan.ancientwarfare.structure.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;

import java.util.List;

public class ItemSpawnerPlacer extends Item {

    public ItemSpawnerPlacer(String itemName) {
        this.setUnlocalizedName(itemName);
        this.setCreativeTab(AWStructuresItemLoader.structureTab);
        this.setTextureName("ancientwarfare:structure/" + itemName);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("guistrings.selected_mob") + ":");
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("spawnerData")) {
            NBTTagCompound tag = stack.getTagCompound().getCompoundTag("spawnerData");
            String mobID = tag.getString("EntityId");
            if (mobID.isEmpty()) {
                list.add(StatCollector.translateToLocal("guistrings.no_selection"));
            } else {
                list.add(StatCollector.translateToLocal("entity." + mobID + ".name"));
            }
        } else {
            list.add(StatCollector.translateToLocal("guistrings.no_selection"));
        }
        list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("guistrings.spawner.warning_1"));
        list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("guistrings.spawner.warning_2"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote || stack == null) {
            return stack;
        }
        MovingObjectPosition mophit = getMovingObjectPositionFromPlayer(player.worldObj, player, false);
        if (player.capabilities.isCreativeMode && player.isSneaking()) {
            NetworkHandler.INSTANCE.openGui(player, NetworkHandler.GUI_SPAWNER, 0, 0, 0);
        } else if (mophit != null && mophit.typeOfHit == MovingObjectType.BLOCK) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("spawnerData")) {
                BlockPosition hit = new BlockPosition(mophit);
                if (player.worldObj.setBlock(hit.x, hit.y, hit.z, Blocks.mob_spawner)) {
                    NBTTagCompound tag = stack.getTagCompound().getCompoundTag("spawnerData");
                    tag.setInteger("x", hit.x);
                    tag.setInteger("y", hit.y);
                    tag.setInteger("z", hit.z);
                    TileEntity te = player.worldObj.getTileEntity(hit.x, hit.y, hit.z);
                    te.readFromNBT(tag);

                    if (!player.capabilities.isCreativeMode) {
                        stack.stackSize--;
                    }
                }
            } else {
                player.addChatComponentMessage(new ChatComponentTranslation("guistrings.spawner.nodata"));
            }
        } else {
            player.addChatComponentMessage(new ChatComponentTranslation("guistrings.spawner.noblock"));
        }
        return stack;
    }

}
