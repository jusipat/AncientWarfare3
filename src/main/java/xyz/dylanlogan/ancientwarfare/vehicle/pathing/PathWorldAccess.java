package xyz.dylanlogan.ancientwarfare.vehicle.pathing;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.dylanlogan.ancientwarfare.core.util.WorldTools;
import xyz.dylanlogan.ancientwarfare.structure.tile.TEGateProxy;

public class PathWorldAccess {

	public boolean canOpenDoors;
	public boolean canSwim;
	public boolean canDrop;
	public boolean canUseLaders;
	private boolean canGoOnLand = true;

	private World world;

	public PathWorldAccess(World world) {
		this.world = world;
	}

	public void setCanGoOnLand(boolean val) {
		this.canGoOnLand = val;
		if (!val) {
			this.canSwim = true;
		}
	}

	public Block getBlock(BlockPos pos) {
		return world.getBlockState(pos).getBlock();
	}

	public int getTravelCost(BlockPos pos) {
		Block block = getBlock(pos);
		if (block == Blocks.water || block == Blocks.flowing_water) {//can't swim check
			if (!canGoOnLand) {
				return 10;
			}
			return 30;
		}
		if (!canGoOnLand) {
			return 30;
		}
		return 10;
	}

	/**
	 * checks the collision bounds of the block at x,y,z to make sure it is <= 0.5 tall (pathable)
	 *
	 * @return true if it is a pathable block, false if it fails bounds checks
	 */
	public boolean checkBlockBounds(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == Blocks.water || block == Blocks.flowing_water) {
			return true;
		} else if (block == Blocks.trapdoor) {
			return state.getValue(BlockTrapDoor.OPEN);
		}
		if (block != Blocks.air) {
			AxisAlignedBB bb = block.getCollisionBoundingBox(state, world, pos);
			if (bb == null) {
				return true;
			}
			if (bb.maxY >= 0.5d) {
				return false;
			}
		}
		return true;
	}

	private boolean isWalkable2(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		Block block = getBlock(pos);
		Block blockDown = getBlock(pos.down());
		Block blockUp = getBlock(pos.up());
		boolean cube = !checkBlockBounds(x, y, z);
		boolean cube2 = !checkBlockBounds(x, y - 1, z);
		boolean cube3 = !checkBlockBounds(x, y + 1, z);
		if (isFence(blockDown) || (isDoor(pos.down()) && isDoor(pos)) || (block == Blocks.cactus || blockDown == Blocks.cactus || blockUp == Blocks.cactus)) {
			return false;
		}
		if (canGoOnLand) {
			if (canUseLaders && isLadder(block)) {
				return true;
			}
			if (canOpenDoors && isDoor(pos) && cube2) {
				return true;
			}
			if (!cube && !cube3 && (cube2 || canSupport(block, pos)))//finally, check if block and blockY+1 are clear and blockY-1 is solid
			{
				return true;
			}
		}
		return canSwim && isWater(block) && blockUp == Blocks.air;
	}

	public boolean isPartialBlock(BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != Blocks.air) {
			AxisAlignedBB bb = state.getCollisionBoundingBox(world, pos);
			if (bb == null) {
				return false;
			}
			if (bb.maxY <= 0.75d && bb.minX < 0.35 && bb.maxX > 0.65 && bb.minZ < 0.35 && bb.maxZ > 0.65) {
				return true;
			}
		}
		return false;
	}

	private boolean canSupport(Block block, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (block == Blocks.trapdoor) {
			return !state.getValue(BlockTrapDoor.OPEN) && state.getValue(BlockTrapDoor.HALF) == BlockTrapDoor.DoorHalf.BOTTOM;
		}
		AxisAlignedBB bb = block.getCollisionBoundingBox(state, world, pos);
		return bb != null && bb.maxY <= 0.5d && bb.minX < 0.35 && bb.maxX > 0.65 && bb.minZ < 0.35 && bb.maxZ > 0.65;
	}

	private boolean isFence(Block block) {
		return block instanceof BlockFence || block instanceof BlockFenceGate || block == Blocks.cobblestone_wall;
	}

	public boolean isWalkable(int x, int y, int z) {
		return isWalkable2(x, y, z);
	}

	private boolean isWater(Block block) {
		return block == Blocks.water || block == Blocks.flowing_water;
	}

	public boolean isDoor(BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == AWStructureBlocks.GATE_PROXY) {
			return WorldTools.getTile(world, pos, TEGateProxy.class)
					.map(proxy -> proxy.getOwner().map(p -> p.getGateType().canSoldierActivate()).orElse(false)).orElse(true);
		}
		return (block instanceof BlockDoor && state.getMaterial() == Material.wood) || block instanceof BlockFenceGate;
	}

	private boolean isLadder(Block block) {
		return block == Blocks.ladder || block == Blocks.vine;
	}

	protected boolean isLadder(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return isLadder(block);
	}

	public boolean isWalkable(int x, int y, int z, Node src) {
		return this.isWalkable(x, y, z);
	}

	public boolean isRemote() {
		return false;
	}

}
