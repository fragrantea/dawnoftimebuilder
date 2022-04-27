package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_COLUMN;

public abstract class ColumnConnectibleBlock extends WaterloggedBlock {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;

	public ColumnConnectibleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(VERTICAL_CONNECTION);
	}

	@Override
	public abstract VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context);

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return (facing.getAxis().isVertical()) ? stateIn.setValue(VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos, stateIn)) : stateIn;
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(Level worldIn, BlockPos pos, BlockState stateIn){
		if(isConnectible(worldIn, pos.above(), stateIn)){
			return (isConnectible(worldIn, pos.below(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (isConnectible(worldIn, pos.below(), stateIn)) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	public boolean isConnectible(Level worldIn, BlockPos pos, BlockState stateIn){
		return worldIn.getBlockState(pos).getBlock() == this;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack heldItemStack = player.getItemInHand(handIn);
		if(player.isCrouching()) {
			//We remove the highest ColumnBlock
			if(state.getValue(VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE)
				return super.use(state, worldIn, pos, player, handIn, hit);
			BlockPos topPos = this.getHighestColumnPos(worldIn, pos);
			if(topPos != pos){
				if(!worldIn.isClientSide()) {
					worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
					if (!player.isCreative()) {
						Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}else{
			if(!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem()){
				//We put a ColumnBlock on top of the column
				BlockPos topPos = this.getHighestColumnPos(worldIn, pos).above();
				if(topPos.getY() <= HIGHEST_Y){
					if(!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir(worldIn, topPos)) {
						worldIn.setBlock(topPos, state, 11);
						if(!player.isCreative()) {
							heldItemStack.shrink(1);
						}
					}
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	private BlockPos getHighestColumnPos(Level worldIn, BlockPos pos){
		int yOffset;
		for(yOffset = 0; yOffset + pos.getY() <= HIGHEST_Y; yOffset++){
			if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) break;
		}
		return pos.above(yOffset - 1);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<TextComponent> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_COLUMN);
	}
}