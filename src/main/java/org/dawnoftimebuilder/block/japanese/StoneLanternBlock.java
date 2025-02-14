package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;

public class StoneLanternBlock extends WaterloggedBlock implements IBlockChain, IBlockSpecialDisplay {

    private static final VoxelShape VS_CENTER = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape[] VS_SIDE = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 14.0D)});
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public StoneLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.DOWN));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.getValue(FACING);
        if(facing.getAxis() == Direction.Axis.Y){
            return VS_CENTER;
        }else return VS_SIDE[facing.get2DDataValue()];
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return false;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }

    @Override
    public float getDisplayScale() {
        return 0.6F;
    }
}
