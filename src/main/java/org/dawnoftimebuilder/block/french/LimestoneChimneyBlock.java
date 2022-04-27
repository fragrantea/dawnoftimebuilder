package org.dawnoftimebuilder.block.french;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import java.util.Random;

public class LimestoneChimneyBlock extends ColumnConnectibleBlock {
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final VoxelShape[] SHAPES = makeShapes();

	public LimestoneChimneyBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) return SHAPES[0];
		return SHAPES[state.getValue(VERTICAL_CONNECTION).getIndex() - 1];
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state.setValue(HORIZONTAL_AXIS, (context.getHorizontalDirection().getAxis() == Direction.Axis.X)? Direction.Axis.Z : Direction.Axis.X);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : None or Under <p/>
	 * 1 : Above
	 * 2 : Both
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Shapes.or(
						Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
						Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
				),
				Shapes.or(
						Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
						Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)
				),
				Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
		};
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if(stateIn.getValue(WATERLOGGED)) return;
		if(stateIn.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (double) pos.getX() + rand.nextDouble() * 0.5D + 0.25D, (double) pos.getY() + rand.nextDouble() * 0.5D + 0.3D, (double) pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
		}
	}
}
