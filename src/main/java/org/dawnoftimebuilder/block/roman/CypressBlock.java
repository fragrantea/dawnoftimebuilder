package org.dawnoftimebuilder.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.util.DoTBBlockUtils.HIGHEST_Y;
import static org.dawnoftimebuilder.util.DoTBBlockUtils.TOOLTIP_COLUMN;

public class CypressBlock extends BlockDoTB implements IBlockGeneration {

    private static final IntegerProperty SIZE = DoTBBlockStateProperties.SIZE_0_5;
    private static final VoxelShape VS_0 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape VS_1 = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape VS_2 = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_3_4 = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public CypressBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 1));
    }

    @Override
    public boolean canSurvive(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP) || worldIn.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItemStack = player.getItemInHand(handIn);
        if(player.isCrouching()) {
            //We remove the highest CypressBlock
            BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
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
                //We put a CypressBlock on top of the cypress
                BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
                if(topPos.getY() <= HIGHEST_Y){
                    if(!worldIn.isClientSide()) {
                        worldIn.setBlock(topPos, this.defaultBlockState(), 11);
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

    private BlockPos getHighestCypressPos(Level worldIn, BlockPos pos){
        int yOffset;
        for(yOffset = 0; yOffset + pos.getY() <= HIGHEST_Y; yOffset++){
            if(worldIn.getBlockState(pos.above(yOffset)).getBlock() != this) break;
        }
        return pos.above(yOffset - 1);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    public static BlockState setSize(BlockState state, int size){
        return state.setValue(SIZE, size);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(SIZE)) {
            case 0:
                return VS_0;
            default:
            case 1:
                return VS_1;
            case 2:
                return VS_2;
            case 3:
            case 4:
                return VS_3_4;
            case 5:
                return Shapes.block();
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_230335_1_, BlockGetter p_230335_2_, BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState adjacentState = context.getLevel().getBlockState(context.getClickedPos().above());
        int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.getValue(SIZE) + 1, 5) : 1;
        if(size < 3) return this.defaultBlockState().setValue(SIZE, size);
        else {
            adjacentState = context.getLevel().getBlockState(context.getClickedPos().below());
            return this.defaultBlockState().setValue(SIZE, (adjacentState.getBlock() == this) ? size : 0);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Level worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis().isVertical()){
            if(!canSurvive(stateIn, worldIn, currentPos)) return Blocks.AIR.defaultBlockState();
            BlockState adjacentState = worldIn.getBlockState(currentPos.above());
            int size = (adjacentState.getBlock() == this) ? Math.min(adjacentState.getValue(SIZE) + 1, 5) : 1;
            if(size < 3) return this.defaultBlockState().setValue(SIZE, size);
            else {
                adjacentState = worldIn.getBlockState(currentPos.below());
                return this.defaultBlockState().setValue(SIZE, (adjacentState.getBlock() == this) ? size : 0);
            }
        }else return stateIn;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRainingAt(pos.above())) {
            if (rand.nextInt(15) == 1) {
                BlockPos posDown = pos.below();
                BlockState stateDown = worldIn.getBlockState(posDown);
                if (!stateDown.canOcclude() || !stateDown.isFaceSturdy(worldIn, posDown, Direction.UP)) {
                    double x = pos.getX() + rand.nextFloat();
                    double y = pos.getY() - 0.05D;
                    double z = pos.getZ() + rand.nextFloat();
                    worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<TextComponent> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        DoTBBlockUtils.addTooltip(tooltip, TOOLTIP_COLUMN);
    }

    @Override
    public void generateOnPos(Level world, BlockPos pos, BlockState state, Random random) {
        int maxSize = 2 + random.nextInt(5);
        world.setBlock(pos, state.setValue(SIZE, 0), 2);
        int size = 1;
        for (int i = maxSize; i > 0; i--){
            world.setBlock(pos.above(i), state.setValue(SIZE, size), 2);
            if(size < 5) size++;
        }
    }
}
