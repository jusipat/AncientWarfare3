package xyz.dylanlogan.ancientwarfare.structure.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IItemKeyInterface;
import xyz.dylanlogan.ancientwarfare.core.network.NetworkHandler;
import xyz.dylanlogan.ancientwarfare.core.util.BlockPosition;
import xyz.dylanlogan.ancientwarfare.core.util.BlockTools;
import xyz.dylanlogan.ancientwarfare.structure.event.IBoxRenderer;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplate;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplateClient;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplateManager;
import xyz.dylanlogan.ancientwarfare.structure.template.StructureTemplateManagerClient;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBB;
import xyz.dylanlogan.ancientwarfare.structure.template.build.StructureBuilder;

import java.util.List;


public class ItemStructureBuilder extends Item implements IItemKeyInterface, IBoxRenderer {

    public ItemStructureBuilder(String itemName) {
        this.setUnlocalizedName(itemName);
        this.setCreativeTab(AWStructuresItemLoader.structureTab);
        this.setMaxStackSize(1);
        this.setTextureName("ancientwarfare:structure/" + itemName);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        String structure = "guistrings.structure.no_selection";
        ItemStructureSettings viewSettings = ItemStructureSettings.getSettingsFor(stack);
        if (viewSettings.hasName()) {
            structure = viewSettings.name;
        }
        list.add(StatCollector.translateToLocal("guistrings.current_structure") + " " + StatCollector.translateToLocal(structure));
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean onKeyActionClient(EntityPlayer player, ItemStack stack, ItemKey key) {
        return key == ItemKey.KEY_0;
    }

    @Override
    public void onKeyAction(EntityPlayer player, ItemStack stack, ItemKey key) {
        if (player == null || player.worldObj.isRemote) {
            return;
        }
        ItemStructureSettings buildSettings = ItemStructureSettings.getSettingsFor(stack);
        if (buildSettings.hasName()) {
            StructureTemplate template = StructureTemplateManager.INSTANCE.getTemplate(buildSettings.name);
            if (template == null) {
                player.addChatComponentMessage(new ChatComponentTranslation("guistrings.template.not_found"));
                return;
            }
            BlockPosition bpHit = BlockTools.getBlockClickedOn(player, player.worldObj, true);
            if (bpHit == null) {
                return;
            }//no hit position, clicked on air
            StructureBuilder builder = new StructureBuilder(player.worldObj, template, BlockTools.getPlayerFacingFromYaw(player.rotationYaw), bpHit.x, bpHit.y, bpHit.z);
            builder.instantConstruction();
            if (!player.capabilities.isCreativeMode) {
                int slot = player.inventory.currentItem;
                if (stack.stackSize == 1) {
                    player.inventory.setInventorySlotContents(slot, null);
                } else {
                    stack.stackSize--;
                }
            }
        } else {
            player.addChatComponentMessage(new ChatComponentTranslation("guistrings.structure.no_selection"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.worldObj.isRemote && !player.isSneaking() && player.capabilities.isCreativeMode) {
            NetworkHandler.INSTANCE.openGui(player, NetworkHandler.GUI_BUILDER, 0, 0, 0);
        }
        return stack;
    }

    @Override
    public void renderBox(EntityPlayer player, ItemStack stack, float delta) {
        ItemStructureSettings settings = ItemStructureSettings.getSettingsFor(stack);
        if (!settings.hasName()) {
            return;
        }
        String name = settings.name();
        StructureTemplateClient structure = StructureTemplateManagerClient.instance().getClientTemplate(name);
        if (structure == null) {
            return;
        }
        BlockPosition hit = BlockTools.getBlockClickedOn(player, player.worldObj, true);
        int face = BlockTools.getPlayerFacingFromYaw(player.rotationYaw);
        if (hit == null) {
            return;
        }
        StructureBB bb = new StructureBB(hit.x, hit.y, hit.z, face, structure.xSize, structure.ySize, structure.zSize, structure.xOffset, structure.yOffset, structure.zOffset);
        Util.renderBoundingBox(player, bb.min, bb.max, delta);
    }
}
