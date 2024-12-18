package xyz.dylanlogan.ancientwarfare.npc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.block.BlockRotationHandler.IRotatableBlock;
import xyz.dylanlogan.ancientwarfare.core.block.BlockRotationHandler.RelativeSide;
import xyz.dylanlogan.ancientwarfare.core.block.BlockRotationHandler.RotationType;
import xyz.dylanlogan.ancientwarfare.core.block.IconRotationMap;
import xyz.dylanlogan.ancientwarfare.core.interfaces.IInteractableTile;
import xyz.dylanlogan.ancientwarfare.core.util.InventoryTools;
import xyz.dylanlogan.ancientwarfare.npc.item.AWNpcItemLoader;
import xyz.dylanlogan.ancientwarfare.npc.tile.TileTownHall;

public class BlockTownHall extends Block implements IRotatableBlock {

    private final IconRotationMap iconMap = new IconRotationMap();

    public BlockTownHall() {
        super(Material.rock);
        this.setCreativeTab(AWNpcItemLoader.npcTab);
        setHardness(2.f);
        setIcon(RelativeSide.TOP, "ancientwarfare:npc/town_hall_top");
        setIcon(RelativeSide.BOTTOM, "ancientwarfare:npc/town_hall_bottom");
        setIcon(RelativeSide.LEFT, "ancientwarfare:npc/town_hall_side");
        setIcon(RelativeSide.RIGHT, "ancientwarfare:npc/town_hall_side");
        setIcon(RelativeSide.FRONT, "ancientwarfare:npc/town_hall_side");
        setIcon(RelativeSide.REAR, "ancientwarfare:npc/town_hall_side");
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        IInventory tile = (IInventory) world.getTileEntity(x, y, z);
        if (tile != null) {
            InventoryTools.dropInventoryInWorld(world, tile, x, y, z);
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public boolean invertFacing() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        iconMap.registerIcons(register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return iconMap.getIcon(this, meta, side);
    }

    @Override
    public BlockTownHall setIcon(RelativeSide side, String texName) {
        iconMap.setIcon(this, side, texName);
        return this;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileTownHall();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int sideHit, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te instanceof IInteractableTile && ((IInteractableTile) te).onBlockClicked(player);
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {
            TileTownHall tileTownHall = (TileTownHall) world.getTileEntity(x, y, z);
            if (world.isBlockIndirectlyGettingPowered(x, y, z))
                tileTownHall.alarmActive = true;
            else
                tileTownHall.alarmActive = false;
        }
    }
}
