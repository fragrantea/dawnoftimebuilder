package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DoubleChairBlock extends ChairBlock {

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DoubleChairBlock(Properties properties, float offsetY) {
        super(properties, offsetY);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.getValue(HALF) == Half.TOP) return ActionResultType.PASS;
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.getValue(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this) return Blocks.AIR.defaultBlockState();
            if(facingState.getValue(HALF) == stateIn.getValue(HALF) || facingState.getValue(FACING) != stateIn.getValue(FACING)) return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if(state.getValue(HALF) == Half.TOP){
            BlockState bottomState = worldIn.getBlockState(pos.below());
            if(bottomState.getBlock() == this){
                return bottomState.getValue(HALF) == Half.BOTTOM && bottomState.getValue(FACING) == state.getValue(FACING);
            }
        }else return true;
        return false;
    }
}
