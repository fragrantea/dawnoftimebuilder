package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.FencePillar;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;

public class CharredSpruceRailingBlock extends FenceBlock {

	private static final EnumProperty<FencePillar> FENCE_PILLAR = DoTBBlockStateProperties.FENCE_PILLAR;

	public CharredSpruceRailingBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FENCE_PILLAR, FencePillar.PILLAR_SMALL));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FENCE_PILLAR);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState oldState = world.getBlockState(pos);
		if(oldState.getBlock() == this){
			if(this.isSmallPillar(oldState)){
				return oldState.setValue(FENCE_PILLAR, this.getBigPillar(world, pos));
			}
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (super.use(state, worldIn, pos, player, handIn, hit) == InteractionResult.SUCCESS) return InteractionResult.SUCCESS;
		if(player.isCrouching()) {
			switch (state.getValue(FENCE_PILLAR)) {
				case PILLAR_BIG:
				case CAP_PILLAR_BIG:
					if (!player.isCreative()) {
						InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
					}
				case NONE:
					worldIn.setBlock(pos, state.setValue(FENCE_PILLAR, FencePillar.PILLAR_SMALL), 2);
					break;
				case PILLAR_SMALL:
					worldIn.setBlock(pos, state.setValue(FENCE_PILLAR, FencePillar.NONE), 2);
					break;
			}

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		if(useContext.getItemInHand().getItem() == this.asItem()) {
			if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching()){
				return super.canBeReplaced(state, useContext);
			}
			return this.isSmallPillar(state);
		}
		return super.canBeReplaced(state, useContext);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.UP && !isSmallPillar(stateIn)){
			stateIn = stateIn.setValue(FENCE_PILLAR, getBigPillar(worldIn, currentPos));
		}
		return stateIn;
	}

	private boolean isSmallPillar(BlockState state){
		return state.getValue(FENCE_PILLAR) == FencePillar.NONE || state.getValue(FENCE_PILLAR) == FencePillar.PILLAR_SMALL;
	}

	private DoTBBlockStateProperties.FencePillar getBigPillar(Level world, BlockPos pos){
		pos = pos.above();
		return (world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()) ? FencePillar.CAP_PILLAR_BIG : FencePillar.PILLAR_BIG;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<TextComponent> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, this);
	}
}
