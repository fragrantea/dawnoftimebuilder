package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.dawnoftimebuilder.block.IBlockClimbingPlant;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_BEAM;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_CLIMBING_PLANT;

public class BeamBlock extends WaterloggedBlock implements IBlockPillar, IBlockClimbingPlant {

	public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
	public static final BooleanProperty AXIS_X = DoTBBlockStateProperties.AXIS_X;
	public static final BooleanProperty AXIS_Y = DoTBBlockStateProperties.AXIS_Y;
	public static final BooleanProperty AXIS_Z = DoTBBlockStateProperties.AXIS_Z;
	public static final EnumProperty<DoTBBlockStateProperties.ClimbingPlant> CLIMBING_PLANT = DoTBBlockStateProperties.CLIMBING_PLANT;
	private static final IntegerProperty AGE = DoTBBlockStateProperties.AGE_0_6;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	private static final VoxelShape[] SHAPES = makeShapes();

	public BeamBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, false).setValue(AXIS_Y, false).setValue(AXIS_X, false).setValue(AXIS_Z, false).setValue(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).setValue(AGE, 0).setValue(WATERLOGGED, false).setValue(PERSISTENT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BOTTOM, AXIS_Y, AXIS_X, AXIS_Z, CLIMBING_PLANT, AGE, PERSISTENT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[getShapeIndex(state)];
	}

	protected int getShapeIndex(BlockState state){
		if (state.getValue(AXIS_Y)){
			int index = 3;
			if (state.getValue(BOTTOM)) index += 1;
			if (state.getValue(AXIS_X)) index += 2;
			if (state.getValue(AXIS_Z)) index += 4;
			return index;
		}else{
			int index = state.getValue(AXIS_Z) ? 1 : 0;
			return state.getValue(AXIS_X) ? index * 2 : index;
		}
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Axis X <p/>
	 * 1 : Axis Z <p/>
	 * 2 : Axis X + Z <p/>
	 * 3 : Axis Y <p/>
	 * 4 : Axis Y + Bottom <p/>
	 * 5 : Axis Y + X <p/>
	 * 6 : Axis Y + X + Bottom <p/>
	 * 7 : Axis Y + Z <p/>
	 * 8 : Axis Y + Z + Bottom <p/>
	 * 9 : Axis Y + X + Z <p/>
	 * 10 : Axis Y + X + Z + Bottom
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_axis_x = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
		VoxelShape vs_axis_z = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
		VoxelShape vs_axis_x_z = Shapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
		VoxelShape vs_axis_y_bottom = Shapes.or(vs_axis_y, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D));
		return new VoxelShape[]{
				vs_axis_x,
				vs_axis_z,
				vs_axis_x_z,
				vs_axis_y,
				vs_axis_y_bottom,
				Shapes.or(vs_axis_y, vs_axis_x),
				Shapes.or(vs_axis_y_bottom, vs_axis_x),
				Shapes.or(vs_axis_y, vs_axis_z),
				Shapes.or(vs_axis_y_bottom, vs_axis_z),
				Shapes.or(vs_axis_y, vs_axis_x_z),
				Shapes.or(vs_axis_y_bottom, vs_axis_x_z)
		};
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		if (state.getBlock() != this)
			state = super.getStateForPlacement(context);
		switch (context.getClickedFace().getAxis()) {
			case X:
				state = state.setValue(AXIS_X, true);
				break;
			case Y:
				state = state.setValue(AXIS_Y, true);
				break;
			case Z:
				state = state.setValue(AXIS_Z, true);
				break;
		}
		return this.getCurrentState(state, context.getLevel(), context.getClickedPos());
	}

	public BlockState getCurrentState(BlockState stateIn, Level worldIn, BlockPos pos){
		return stateIn.getValue(AXIS_Y) ? stateIn.setValue(BOTTOM, canNotConnectUnder(worldIn.getBlockState(pos.below()))) : stateIn;
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		ItemStack itemstack = useContext.getItemInHand();
		if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) return false;
		if (itemstack.getItem() == this.asItem()) {
			if (useContext.replacingClickedOnBlock()) {
				switch (useContext.getClickedFace().getAxis()) {
					case X:
						return !state.getValue(AXIS_X);
					case Y:
						return !state.getValue(AXIS_Y);
					case Z:
						return !state.getValue(AXIS_Z);
				}
			}
		}
		return false;
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAfterBreak(state, worldIn, pos, stack);
		//Be careful, climbing plants are not dropping from block's loot_table, but from their own loot_table
		this.dropPlant(state, worldIn, pos, stack);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		stateIn = this.getCurrentState(stateIn, worldIn, currentPos);
		if(!this.canHavePlant(stateIn) && !stateIn.getValue(CLIMBING_PLANT).hasNoPlant()){
			return this.removePlant(stateIn, worldIn, currentPos, ItemStack.EMPTY);
		}
		return stateIn;
	}

	public boolean canNotConnectUnder(BlockState state) {
		if (state.getBlock() instanceof BeamBlock)
			return !state.getValue(AXIS_Y);
		else return true;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return !state.getValue(CLIMBING_PLANT).hasNoPlant();
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		this.tickPlant(state, worldIn, pos, random);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
		if(!state.getValue(PERSISTENT)){
			if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
				Random rand = new Random();
				for(int i = 0; i < 5; i++){
					worldIn.addAlwaysVisibleParticle(ParticleTypes.SMOKE, (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 0.5D + rand.nextDouble() / 2, (double)pos.getZ() + rand.nextDouble(), 0.0D, 0.07D, 0.0D);
				}
				worldIn.setBlock(pos, state.setValue(PERSISTENT, true), 10);
				return InteractionResult.SUCCESS;
			}
		}
		if(player.isCreative()){
			if(this.tryPlacingPlant(state, worldIn, pos, player, handIn)) return InteractionResult.SUCCESS;
		}
		if(this.harvestPlant(state, worldIn, pos, player, handIn) == InteractionResult.SUCCESS){
			return InteractionResult.SUCCESS;
		}
		if(player.isCrouching() && state.getValue(BOTTOM)){
			worldIn.setBlock(pos, state.setValue(BOTTOM, false), 10);
		}
		return InteractionResult.PASS;
	}

	@Override
	public boolean canHavePlant(BlockState state) {
		return !state.getValue(WATERLOGGED) && !state.getValue(BOTTOM);
	}

	@Nonnull
	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
		return state.getValue(AXIS_Y) ? DoTBBlockStateProperties.PillarConnection.TEN_PX : DoTBBlockStateProperties.PillarConnection.NOTHING;
	}

	@Override
	public boolean isLadder(BlockState state, BlockGetter world, BlockPos pos, LivingEntity entity) {
		return !state.getValue(CLIMBING_PLANT).hasNoPlant();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<TextComponent> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_BEAM, TOOLTIP_CLIMBING_PLANT);
	}
}
