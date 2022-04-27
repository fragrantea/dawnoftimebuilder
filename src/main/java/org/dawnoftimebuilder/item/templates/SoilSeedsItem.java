package org.dawnoftimebuilder.item.templates;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class SoilSeedsItem extends BlockItem {
    private final SoilCropsBlock crops;

    public SoilSeedsItem(SoilCropsBlock crops, @Nullable Food food) {
        super(crops, food != null ? new Item.Properties().tab(DOTB_TAB).food(food) : new Item.Properties().tab(DOTB_TAB));
        this.crops = crops;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if(context.getClickedFace() != Direction.UP) return InteractionResult.FAIL;

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if(!this.crops.canSurvive(this.crops.defaultBlockState(), world, pos)) return InteractionResult.FAIL;
        if(!world.getBlockState(pos).canBeReplaced(context)) return InteractionResult.FAIL;
        BlockPlaceContext blockitemusecontext = this.updatePlacementContext(context);
        if (blockitemusecontext == null) {
            return InteractionResult.FAIL;
        } else {
            BlockState madeState = this.getPlacementState(blockitemusecontext);
            if (madeState == null) {
                return InteractionResult.FAIL;
            } else if (!world.setBlock(pos, madeState, 11)) {
                return InteractionResult.FAIL;
            } else {
                Player playerentity = blockitemusecontext.getPlayer();
                ItemStack itemstack = blockitemusecontext.getItemInHand();
                BlockState newState = world.getBlockState(pos);
                Block block = newState.getBlock();
                if (block == madeState.getBlock()) {
                    this.updateCustomBlockEntityTag(pos, world, playerentity, itemstack, newState);
                    block.setPlacedBy(world, pos, newState, playerentity, itemstack);
                    if (playerentity instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerentity, pos, itemstack);
                    }
                }
                SoundType soundtype = newState.getSoundType(world, pos, context.getPlayer());
                world.playSound(playerentity, pos, this.getPlaceSound(newState, world, pos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
    }
}