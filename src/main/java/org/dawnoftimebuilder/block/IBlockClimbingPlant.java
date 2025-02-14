package org.dawnoftimebuilder.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.List;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.PERSISTENT;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.AGE_0_6;
import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.CLIMBING_PLANT;

public interface IBlockClimbingPlant {

	/**
	 * Tries to increase the AGE of the Climbing Plant.
	 * Else, tries to spread on an adjacent Block.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param random Used to determine if the Block must grow.
	 */
	default void tickPlant(BlockState stateIn, World worldIn, BlockPos pos, Random random){
		if (!worldIn.isClientSide()) {
			if (stateIn.getValue(CLIMBING_PLANT).hasNoPlant() || stateIn.getValue(PERSISTENT)) return;
			if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

			if (worldIn.getRawBrightness(pos, 0) >= 8) {
				int age = stateIn.getValue(AGE_0_6);
				if (ForgeHooks.onCropsGrowPre(worldIn, pos, stateIn, random.nextInt(DoTBConfig.CLIMBING_PLANT_GROWTH_CHANCE.get()) == 0)) {//Probability "can grow"
					if(age < 2){
						worldIn.setBlock(pos, stateIn.setValue(AGE_0_6, age + 1), 2);
						ForgeHooks.onCropsGrowPost(worldIn, pos, stateIn);
						return;
					}else{
						if(stateIn.getValue(CLIMBING_PLANT).canGrow(worldIn, age)){
							worldIn.setBlock(pos, stateIn.setValue(AGE_0_6, 2 + ((age - 1) % 5)), 2);
							ForgeHooks.onCropsGrowPost(worldIn, pos, stateIn);
							return;
						}
					}
				}
				if(age < 2 || random.nextInt(DoTBConfig.CLIMBING_PLANT_SPREAD_CHANCE.get()) != 0) return;//Probability "can spread"
				BlockPos[] positions = new BlockPos[]{
						pos.north(),
						pos.east(),
						pos.south(),
						pos.west(),
						pos.above()
				};
				int index = random.nextInt(5);//Probability "chose the adjacent block to grow on"
				BlockState newState = worldIn.getBlockState(positions[index]);
				if(newState.getBlock() instanceof IBlockClimbingPlant){
					IBlockClimbingPlant newBlock = (IBlockClimbingPlant) newState.getBlock();
					if(newBlock.canHavePlant(newState) && newState.getValue(CLIMBING_PLANT).hasNoPlant()){
						worldIn.setBlock(positions[index], newState.setValue(CLIMBING_PLANT, stateIn.getValue(CLIMBING_PLANT)), 2);
					}
				}
			}
		}
	}

	/**
	 * When a Player right-clicks the Block with a Climbing Plant seed, it tries to set the corresponding Climbing Plant on the Block.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that right-clicked the Block.
	 * @param handIn Active hand.
	 * @return True if a Climbing Plant was successfully put on the Block.
	 */
	default boolean tryPlacingPlant(BlockState stateIn, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		if(player.isCrouching()) return false;
		ItemStack heldItemStack = player.getItemInHand(handIn);
		if(this.canHavePlant(stateIn) && stateIn.getValue(CLIMBING_PLANT).hasNoPlant()){
			DoTBBlockStateProperties.ClimbingPlant plant = DoTBBlockStateProperties.ClimbingPlant.getFromItem(heldItemStack.getItem());
			if(!plant.hasNoPlant()){
				stateIn = stateIn.setValue(CLIMBING_PLANT, plant);
				if (!player.isCreative()) {
					heldItemStack.shrink(1);
				}
				worldIn.setBlock(pos, stateIn, 10);
				return true;
			}
		}
		return false;
	}

	/**
	 * If the player is in Creative, he can control plant's age with right-click and shift-right-click
	 * Else, if the Climbing Plant is older than AGE 2 and has a loot_table, it drops its loots.
	 * Else, if the player is sneaking, the plant is removed and its loots dropped.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that right-clicked the Block.
	 * @param handIn Active hand.
	 * @return True if the Climbing Plant was modified.
	 */
	default ActionResultType harvestPlant(BlockState stateIn, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		if(player.isCreative() && stateIn.getValue(PERSISTENT) && !stateIn.getValue(CLIMBING_PLANT).hasNoPlant()){
			if(player.isCrouching()){
				if(stateIn.getValue(AGE_0_6) > 0){
					worldIn.setBlock(pos, stateIn.setValue(AGE_0_6, stateIn.getValue(AGE_0_6) - 1), 10);
				}else{
					worldIn.setBlock(pos, stateIn.setValue(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).setValue(AGE_0_6, 0), 10);
				}
				return ActionResultType.SUCCESS;
			}else{
				if(stateIn.getValue(AGE_0_6) < stateIn.getValue(CLIMBING_PLANT).maxAge()){
					worldIn.setBlock(pos, stateIn.setValue(AGE_0_6, stateIn.getValue(AGE_0_6) + 1), 10);
					return ActionResultType.SUCCESS;
				}
				return ActionResultType.PASS;
			}
		}else{
			if(stateIn.getValue(AGE_0_6) > 2){
				if(this.dropPlant(stateIn, worldIn, pos, player.getItemInHand(handIn))){
					stateIn = stateIn.setValue(AGE_0_6, 2);
					worldIn.setBlock(pos, stateIn, 10);
					worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return ActionResultType.SUCCESS;
				}
			}
			if(player.isCrouching()){
				return tryRemovingPlant(stateIn, worldIn, pos, player.getItemInHand(handIn));
			}
			return ActionResultType.PASS;
		}
	}

	/**
	 * If the state in input contains a Climbing Plant, it removes it and drops its loots.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
	 * @return True if a Climbing Plant was removed.
	 */
	default ActionResultType tryRemovingPlant(BlockState stateIn, World worldIn, BlockPos pos, ItemStack heldItemStack){
		if(!stateIn.getValue(CLIMBING_PLANT).hasNoPlant()){
			stateIn = this.removePlant(stateIn, worldIn, pos, heldItemStack);
			worldIn.setBlock(pos, stateIn, 10);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	/**
	 * Removes the Climbing Plant, drop its LootTable, and play breaking sound.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions in the LootTable.
	 * @return The State in input with no Climbing Plant, and AGE back to 0.
	 */
	default BlockState removePlant(BlockState stateIn, IWorld worldIn, BlockPos pos, ItemStack heldItemStack){
		this.dropPlant(stateIn, worldIn, pos, heldItemStack);
		worldIn.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		stateIn = stateIn.setValue(CLIMBING_PLANT, DoTBBlockStateProperties.ClimbingPlant.NONE).setValue(AGE_0_6, 0);
		return stateIn;
	}

	/**
	 * Drops one by one each item contained in the LootTable of the Climbing Plant at the corresponding age.
	 * For example, if the Block contains a plant called "plant" at age "3", it will drop each Item in the LootTable named "plant_3" in Blocks folder.
	 * @param stateIn Current State of the Block.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param heldItemStack Item in active hand to apply tool conditions.
	 * @return True if some loot is dropped. False if there were no loot_table found or item dropped.
	 */
	default boolean dropPlant(BlockState stateIn, IWorld worldIn, BlockPos pos, ItemStack heldItemStack){
		if(worldIn.isClientSide()) return false;
		DoTBBlockStateProperties.ClimbingPlant plant = stateIn.getValue(CLIMBING_PLANT);
		if(plant.hasNoPlant()) return false;
		List<ItemStack> drops = DoTBBlockUtils.getLootList((ServerWorld)worldIn, stateIn, heldItemStack, plant.getSerializedName() + "_" + stateIn.getValue(AGE_0_6));
		return DoTBBlockUtils.dropLootFromList(worldIn, pos, drops, 1.0F);
	}

	/**
	 * @param state Current state of the Block.
	 * @return True if the state of the Block allows Climbing Plants.
	 */
	default boolean canHavePlant(BlockState state) {
		if(state.getBlock() instanceof IWaterLoggable){
			return !state.getValue(WATERLOGGED);
		}
		return true;
	}
}
