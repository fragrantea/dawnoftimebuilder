package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerBlock extends WaterloggedBlock {

	//TODO Add redstone compatibility : ie emit redstone when dried
	public static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_2;
	public static final VoxelShape VS_SIMPLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape VS_DOUBLE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public DryerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SIZE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(SIZE)) {
			default:
			case 0:
				return VS_SIMPLE;
			case 1:
				return VS_DOUBLE;
			case 2 :
				return Shapes.block();
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		if (state.getBlock() == this) {
			return state.setValue(SIZE, (context.getLevel().getBlockState(pos.above()).getBlock() == this) ? 2 : 1);
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		ItemStack itemstack = useContext.getItemInHand();
		if(state.getValue(SIZE) == 0 && itemstack.getItem() == this.asItem()) {
			return useContext.replacingClickedOnBlock();
		}
		return false;
	}

	@Override
	public void onRemove(BlockState oldState, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof DryerTileEntity) {
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(0));
					InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(1));
				});
			}
		}
		super.onRemove(oldState, worldIn, pos, newState, isMoving);
	}

	@Override
	public boolean canSurvive(BlockState state, BlockGetter worldIn, BlockPos pos) {
		pos = pos.below();
		BlockState stateDown = worldIn.getBlockState(pos);
		if(stateDown.getBlock() == this){
			return stateDown.getValue(SIZE) != 0;
		}
		return canSupportRigidBlock(worldIn, pos);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.DOWN){
			if(!canSurvive(stateIn,worldIn,currentPos)) return Blocks.AIR.defaultBlockState();
		}
		if(facing == Direction.UP){
			return stateIn.setValue(SIZE, (stateIn.getValue(SIZE) != 0 && facingState.getBlock() == this) ? 2 : 1);
		}
		return stateIn;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world){
		return DRYER_TE.get().create();
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if(!worldIn.isClientSide() && handIn == InteractionHand.MAIN_HAND) {
			if(worldIn.getBlockEntity(pos) instanceof DryerTileEntity) {
				DryerTileEntity tileEntity = (DryerTileEntity) worldIn.getBlockEntity(pos);
				if(tileEntity == null) return InteractionResult.PASS;

				if(player.isCrouching()) return tileEntity.dropOneItem(worldIn, pos);

				else {
					return tileEntity.tryInsertItemStack(player.getItemInHand(handIn), state.getValue(SIZE) == 0, worldIn, pos, player);
				}
			}
		}
		return InteractionResult.FAIL;
	}
}
