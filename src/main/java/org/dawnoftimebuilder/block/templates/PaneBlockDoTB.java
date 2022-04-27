package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.PAPER_DOOR;

public class PaneBlockDoTB extends IronBarsBlock {

	public PaneBlockDoTB(Properties properties) {
        super(properties);
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		//Must be overridden to allow connection to paper_door
		BlockGetter world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Fluid fluid = context.getLevel().getFluidState(context.getClickedPos()).getType();
		BlockPos posNorth = pos.north();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockPos posEast = pos.east();
		BlockState stateNorth = world.getBlockState(posNorth);
		BlockState stateSouth = world.getBlockState(posSouth);
		BlockState stateWest = world.getBlockState(posWest);
		BlockState stateEast = world.getBlockState(posEast);
		return this.defaultBlockState()
				.setValue(NORTH, this.canAttachPane(stateNorth, stateNorth.isFaceSturdy(world, posNorth, Direction.SOUTH)))
				.setValue(SOUTH, this.canAttachPane(stateSouth, stateSouth.isFaceSturdy(world, posSouth, Direction.NORTH)))
				.setValue(WEST, this.canAttachPane(stateWest, stateWest.isFaceSturdy(world, posWest, Direction.EAST)))
				.setValue(EAST, this.canAttachPane(stateEast, stateEast.isFaceSturdy(world, posEast, Direction.WEST)))
				.setValue(WATERLOGGED, fluid == Fluids.WATER);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
		//Must be overridden to allow connection to paper_door
		if (stateIn.getValue(WATERLOGGED)) worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		return facing.getAxis().isHorizontal() ? stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()))) : stateIn;
	}

	public boolean canAttachPane(BlockState adjacentState, boolean hasSolidSide) {
		Block block = adjacentState.getBlock();
		return (!isExceptionForConnection(block) && hasSolidSide) || block instanceof IronBarsBlock || block == PAPER_DOOR.get();
	}
}
