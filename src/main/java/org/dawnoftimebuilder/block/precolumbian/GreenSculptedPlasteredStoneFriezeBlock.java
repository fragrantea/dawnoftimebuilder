package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class GreenSculptedPlasteredStoneFriezeBlock extends PlateBlock {

	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

    public GreenSculptedPlasteredStoneFriezeBlock(Properties properties) {
		super(properties);
    }

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : NW Outer <p/>
	 * 1 : N Default <p/>
	 * 2 : NW Inner <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_qtr_n = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
		VoxelShape vs_spike_n = Block.box(4.5D, 4.0D, 2.0D, 11.5D, 16.0D, 4.0D);
		return new VoxelShape[]{
				Shapes.or(
						Block.box(0.0D, 0.0D, 0.0D, 8.0D, 4.0D, 8.0D),
						Block.box(0.0D, 4.0D, 0.0D, 6.0D, 16.0D, 6.0D)),
				Shapes.or(vs_qtr_n, vs_spike_n),
				Shapes.or(
						vs_qtr_n,
						Block.box(0.0D, 0.0D, 8.0D, 8.0D, 4.0D, 16.0D),
						vs_spike_n,
						Block.box(2.0D, 4.0D, 4.5D, 4.0D, 16.0D, 11.5D)
				)
		};
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		int index = (state.getValue(FACING).get2DDataValue() + 2) % 4;
		index *= 3;
		switch (state.getValue(SHAPE)) {
			default:
			case OUTER_LEFT:
				break;
			case OUTER_RIGHT:
				index += 3;
				break;
			case STRAIGHT:
				index += 1;
				break;
			case INNER_LEFT:
				index += 2;
				break;
			case INNER_RIGHT:
				index += 5;
				break;
		}
		index %= 12;
		return SHAPES[index];
	}
}