package org.dawnoftimebuilder.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockUtils {

	//General
	private static final String FORGE_ID = "forge";
	public static final int HIGHEST_Y = 255;

	//Tooltip translation text
	public static final ITextComponent TOOLTIP_HOLD_SHIFT = new TranslationTextComponent("tooltip." + MOD_ID + ".hold_key").withStyle(TextFormatting.GRAY).append(new TranslationTextComponent("tooltip." + MOD_ID + ".shift").withStyle(TextFormatting.AQUA));
	public static final String TOOLTIP_COLUMN = "column";
	public static final String TOOLTIP_CLIMBING_PLANT = "climbing_plant";
	public static final String TOOLTIP_BEAM = "beam";
	public static final String TOOLTIP_CROP = "crop";
	public static final String TOOLTIP_SIDED_WINDOW = "sided_window";

	//Item tags
	public static final Tags.IOptionalNamedTag<Item> LIGHTERS = ItemTags.createOptional(new ResourceLocation(MOD_ID, "lighters"));

	//Block tags
	public static final Tags.IOptionalNamedTag<Block> COVERED_BLOCKS = BlockTags.createOptional(new ResourceLocation(MOD_ID, "covered_blocks"));

	/** Fills a table with VS rotated in each horizontal directions following the horizontal index order :<p/>
	 * south - west - north - east
	 *
	 * @param shapes Contains the VoxelShapes oriented toward south.
	 * @return A table filled with the previous VS and new ones rotated in each 3 horizontal directions.
	 */
	public static VoxelShape[] GenerateHorizontalShapes(VoxelShape[] shapes){
		VoxelShape[] newShape = {VoxelShapes.empty()};
		VoxelShape[] newShapes = new VoxelShape[shapes.length * 4];
		int i = 0;
		for(VoxelShape shape : shapes){
			newShapes[i] = shape;
			i++;
		}
		for(int rotation = 1; rotation < 4; rotation++){
			int j = 0;
			for(VoxelShape shape : shapes){
				shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = VoxelShapes.or(newShape[0], VoxelShapes.box(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
				shapes[j] = newShape[0];
				newShapes[i] = newShape[0];
				newShape[0] = VoxelShapes.empty();
				i++;
				j++;
			}
		}
		return newShapes;
	}

	/**
	 * @param serverWorld World can be cast to ServerWorld.
	 * @param stateIn Current state of the Block.
	 * @param itemStackHand ItemStack in player's hand, allow tools conditions.
	 * @param name Used to define the name of the LootTable.
	 * @return the List of ItemStack found in the corresponding LootTable.
	 */
	public static List<ItemStack> getLootList(ServerWorld serverWorld, BlockState stateIn, ItemStack itemStackHand, String name){
		LootTable table = serverWorld.getServer().getLootTables().get(new ResourceLocation(MOD_ID + ":blocks/" + name));
		LootContext.Builder builder = (new LootContext.Builder(serverWorld))
				.withRandom(serverWorld.random)
				.withParameter(LootParameters.BLOCK_STATE, stateIn)
				.withParameter(LootParameters.TOOL, itemStackHand)
				.withParameter(LootParameters.ORIGIN, new Vector3d(0, 0, 0));
		LootContext lootcontext = builder.create(LootParameterSets.BLOCK);
		return table.getRandomItems(lootcontext);
	}

	/**
	 * Drops each item in the List of ItemStack one by one.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param drops ItemStack list that will be dropped.
	 * @param multiplier Multiply the quantity of item (round down) per ItemStack (use 1.0F to keep the same number).
	 * @return True if some items are dropped, False otherwise.
	 */
	public static boolean dropLootFromList(IWorld worldIn, BlockPos pos, List<ItemStack> drops, float multiplier){
		if(drops.isEmpty() || !(worldIn instanceof World)) return false;
		for(ItemStack drop : drops){
			int quantity = (int) Math.floor(drop.getCount() * multiplier);
			for (int i = 0; i < quantity; i++){
				Block.popResource((World) worldIn, pos, new ItemStack(drop.getItem(), 1));
			}
		}
		return true;
	}

	/**
	 * Checks if the player can light the block. If yes, damages the item used and display the sound.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that clicks on the Block.
	 * @param handIn Player's hand.
	 * @return True if the block is now in fire. False otherwise.
	 */
	public static boolean useLighter(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		ItemStack itemInHand = player.getItemInHand(handIn);
		if (!itemInHand.isEmpty() && itemInHand.getItem().is(LIGHTERS)) {
			worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(handIn));
			return true;
		}
		return false;
	}

	public static void addTooltip(List<ITextComponent> tooltip, String... tooltipNames){
		addTooltip(tooltip, null, tooltipNames);
	}
	
	public static void addTooltip(List<ITextComponent> tooltip, @Nullable Block block, String... tooltipNames){
		if(Screen.hasShiftDown()){
			if(block != null){
				ResourceLocation blockName = block.getRegistryName();
				if(blockName != null){
					tooltip.add(new TranslationTextComponent("tooltip." + MOD_ID + "." + blockName.getPath()).withStyle(TextFormatting.GRAY));
				}
			}
			for(String tooltipName : tooltipNames){
				tooltip.add(new TranslationTextComponent("tooltip." + MOD_ID + "." + tooltipName).withStyle(TextFormatting.GRAY));
			}
		}else tooltip.add(TOOLTIP_HOLD_SHIFT);

	}
}
