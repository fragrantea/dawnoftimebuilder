package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.state.properties.Half;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class DoubleChairBlock extends ChairBlock{

    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DoubleChairBlock(Properties properties, float offsetY) {
        super(properties, offsetY);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 10);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(state.getValue(HALF) == Half.TOP) return InteractionResult.PASS;
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction halfDirection = (stateIn.getValue(HALF) == Half.TOP) ? Direction.DOWN : Direction.UP;
        if(facing == halfDirection){
            if(facingState.getBlock() != this) return Blocks.AIR.defaultBlockState();
            if(facingState.getValue(HALF) == stateIn.getValue(HALF) || facingState.getValue(FACING) != stateIn.getValue(FACING)) return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        BlockPos blockpos = (state.getValue(HALF) == Half.TOP) ? pos.below() : pos.above();
        BlockState otherState = worldIn.getBlockState(blockpos);
        if(otherState.getBlock() == this && otherState.getValue(HALF) != state.getValue(HALF)) {
            worldIn.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(player, 2001, blockpos, Block.getId(otherState));
            ItemStack itemstack = player.getMainHandItem();
            if(!worldIn.isClientSide() && !player.isCreative()) {
                Block.dropResources(state, worldIn, pos, null, player, itemstack);
                Block.dropResources(otherState, worldIn, blockpos, null, player, itemstack);
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }
}
