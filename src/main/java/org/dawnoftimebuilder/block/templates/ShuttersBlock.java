package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.Half;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

public class ShuttersBlock extends SmallShuttersBlock {

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public ShuttersBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        Direction direction = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        if(!world.getBlockState(pos.above()).canBeReplaced(context))
            return null;
        int x = direction.getStepX();
        int z = direction.getStepZ();
        double onX = context.getClickLocation().x - pos.getX();
        double onZ = context.getClickLocation().z - pos.getZ();
        boolean hingeLeft = (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
        return super.getStateForPlacement(context).setValue(HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(FACING, direction).setValue(POWERED, world.hasNeighborSignal(pos)).setValue(HALF, Half.BOTTOM);
    }

    @Override
    public boolean canSurvive(BlockState state, BlockGetter worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP){
            BlockState bottomState = worldIn.getBlockState(pos.below());
            if(bottomState.getBlock() == this){
                return bottomState.getValue(HALF) == Half.BOTTOM
                        && bottomState.getValue(FACING) == state.getValue(FACING)
                        && bottomState.getValue(HINGE) == state.getValue(HINGE);
            }
        }else return true;
        return false;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.getValue(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this)
                return Blocks.AIR.defaultBlockState();
            if(facingState.getValue(HALF) == stateIn.getValue(HALF)
                    || facingState.getValue(FACING) != stateIn.getValue(FACING)
                    || facingState.getValue(HINGE) != stateIn.getValue(HINGE))
                return Blocks.AIR.defaultBlockState();
            stateIn = stateIn.setValue(OPEN_POSITION, facingState.getValue(OPEN_POSITION));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected DoTBBlockStateProperties.OpenPosition getOpenState(BlockState stateIn, Level worldIn, BlockPos pos) {
        BlockPos secondPos = pos.relative(stateIn.getValue(HALF) == Half.TOP ? Direction.DOWN : Direction.UP);
        if (!worldIn.getBlockState(secondPos).getCollisionShape(worldIn, pos).isEmpty() || !worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty())
            return DoTBBlockStateProperties.OpenPosition.HALF;
        return DoTBBlockStateProperties.OpenPosition.FULL;
    }
}
