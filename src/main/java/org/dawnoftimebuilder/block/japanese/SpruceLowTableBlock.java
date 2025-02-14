package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;

import javax.annotation.Nonnull;

public class SpruceLowTableBlock extends DisplayerBlock {

	private static final VoxelShape X_AXIS_VS = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 8.0D, 14.0D);
	private static final VoxelShape Z_AXIS_VS = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 16.0D);
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	public SpruceLowTableBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? X_AXIS_VS : Z_AXIS_VS;
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? Direction.Axis.X : Direction.Axis.Z);
	}

	@Override
	public double getDisplayerX(BlockState state){
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? 0.1875D : 0.3125D;
	}

	@Override
	public double getDisplayerY(BlockState state){
		return 0.46875D;
	}

	@Override
	public double getDisplayerZ(BlockState state){
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? 0.3125D : 0.1875D;
	}

	@Nonnull
	@Override
	public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rot) {
		if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		else return super.rotate(state, rot);
	}
}