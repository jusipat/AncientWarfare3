package xyz.dylanlogan.ancientwarfare.structure.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.structure.tile.TEGateProxy;

import java.util.ArrayList;
import java.util.Random;

public final class BlockGateProxy extends BlockContainer {

    public BlockGateProxy() {
        super(Material.rock);
        this.setBlockTextureName("ancientwarfare:structure/gate_proxy");
        this.setCreativeTab(null);
        this.setResistance(2000.f);
        this.setHardness(5.f);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TEGateProxy();
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune){

    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z){
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileEntity proxy = world.getTileEntity(x, y, z);
        if(proxy instanceof TEGateProxy){
            return ((TEGateProxy) proxy).onBlockPicked(target);
        }
        return null;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float vecX, float vecY, float vecZ) {
        TileEntity proxy = world.getTileEntity(x, y, z);
        return proxy instanceof TEGateProxy && ((TEGateProxy) proxy).onBlockClicked(player);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        TileEntity proxy = world.getTileEntity(x, y, z);
        if(proxy instanceof TEGateProxy){
            ((TEGateProxy) proxy).onBlockAttacked(player);
        }else if(player != null && player.capabilities.isCreativeMode){
            return super.removedByPlayer(world, player, x, y, z);
        }
        return false;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        player.addExhaustion(0.025F);
    }

    @Override
    public void dropXpOnBlockBreak(World world, int x, int y, int z, int amount) {

    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return false;
    }

    //Actually "can go through", for mob pathing
    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z){
        TileEntity proxy = world.getTileEntity(x, y, z);
        if(proxy instanceof TEGateProxy && ((TEGateProxy)proxy).isGateClosed()){
            return false;
        }
        //Gate is probably open, Search identical neighbour
        if(world.getBlock(x - 1, y, z) == this) {
            return world.getBlock(x + 1, y, z) == this;
        }else if(world.getBlock(x, y, z - 1) == this) {
            return world.getBlock(x, y, z + 1) == this;
        }else if(world.getBlock(x + 1 , y, z) == this) {
            return world.getBlock(x - 1, y, z) == this;
        }else if(world.getBlock(x, y, z + 1) == this){
            return world.getBlock(x, y, z - 1) == this;
        }
        return true;
    }
}
