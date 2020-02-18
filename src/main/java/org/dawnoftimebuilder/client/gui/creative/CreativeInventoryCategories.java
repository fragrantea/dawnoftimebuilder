package org.dawnoftimebuilder.client.gui.creative;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.items.DoTBItems;
import java.util.ArrayList;
import java.util.Arrays;
import static org.dawnoftimebuilder.blocks.DoTBBlocks.*;

public enum CreativeInventoryCategories {

	GENERAL("General",
			Item.getItemFromBlock(path_dirt),
			Item.getItemFromBlock(path_dirt_slab),
			Item.getItemFromBlock(path_gravel),
			Item.getItemFromBlock(path_gravel_slab),
			Item.getItemFromBlock(path_stepping_stones),
			Item.getItemFromBlock(path_stepping_stones_slab),
			Item.getItemFromBlock(path_cobbled),
			Item.getItemFromBlock(path_cobbled_slab),
			Item.getItemFromBlock(path_ochre_tiles),
			Item.getItemFromBlock(path_ochre_tiles_slab),
			Item.getItemFromBlock(rammed_dirt),
			Item.getItemFromBlock(thatch_wheat),
			Item.getItemFromBlock(thatch_wheat_stairs),
			Item.getItemFromBlock(thatch_wheat_slab),
			Item.getItemFromBlock(thatch_wheat_edge),
			Item.getItemFromBlock(thatch_bamboo),
			Item.getItemFromBlock(thatch_bamboo_stairs),
			Item.getItemFromBlock(thatch_bamboo_slab),
			Item.getItemFromBlock(thatch_bamboo_edge),
			Item.getItemFromBlock(fireplace),
			Item.getItemFromBlock(iron_chain),
			Item.getItemFromBlock(stone_frieze)
	),

	FRENCH("French",
			Item.getItemFromBlock(path_dirt),
			Item.getItemFromBlock(path_dirt_slab),
			Item.getItemFromBlock(path_gravel),
			Item.getItemFromBlock(path_gravel_slab),
			Item.getItemFromBlock(path_cobbled),
			Item.getItemFromBlock(path_cobbled_slab),
			Item.getItemFromBlock(thatch_wheat),
			Item.getItemFromBlock(thatch_wheat_stairs),
			Item.getItemFromBlock(thatch_wheat_slab),
			Item.getItemFromBlock(thatch_wheat_edge),
			Item.getItemFromBlock(rammed_dirt),
			Item.getItemFromBlock(framed_rammed_dirt),
			Item.getItemFromBlock(oak_timber_frame),
			Item.getItemFromBlock(oak_timber_frame_corner),
			Item.getItemFromBlock(oak_timber_frame_pillar),
			Item.getItemFromBlock(oak_waxed_planks),
			Item.getItemFromBlock(oak_waxed_planks_stairs),
			Item.getItemFromBlock(oak_waxed_planks_slab),
			Item.getItemFromBlock(oak_waxed_planks_edge),
			Item.getItemFromBlock(oak_waxed_fence),
			Item.getItemFromBlock(oak_planks_edge),
			Item.getItemFromBlock(oak_beam),
			Item.getItemFromBlock(oak_support_beam),
			Item.getItemFromBlock(oak_support_slab),
			Item.getItemFromBlock(small_oak_shutters),
			DoTBItems.oak_shutters,
			Item.getItemFromBlock(lattice_glass),
			Item.getItemFromBlock(lattice_glass_pane),
			Item.getItemFromBlock(lattice_oak_window),
			Item.getItemFromBlock(cobbled_limestone),
			Item.getItemFromBlock(limestone_brick),
			Item.getItemFromBlock(limestone_brick_stairs),
			Item.getItemFromBlock(limestone_brick_slab),
			Item.getItemFromBlock(limestone_brick_edge),
			Item.getItemFromBlock(limestone_brick_wall),
			Item.getItemFromBlock(fireplace),
			Item.getItemFromBlock(limestone_fireplace),
			Item.getItemFromBlock(limestone_chimney),
			Item.getItemFromBlock(flat_roof_tiles),
			Item.getItemFromBlock(flat_roof_tiles_stairs),
			Item.getItemFromBlock(flat_roof_tiles_slab),
			Item.getItemFromBlock(flat_roof_tiles_edge),
			Item.getItemFromBlock(iron_portcullis),
			Item.getItemFromBlock(iron_chain),
			DoTBItems.iron_plate_armor_head,
			DoTBItems.iron_plate_armor_chest,
			DoTBItems.iron_plate_armor_legs,
			DoTBItems.iron_plate_armor_feet,
			DoTBItems.wax
	),

	JAPANESE("Japanese",
			Item.getItemFromBlock(path_dirt),
			Item.getItemFromBlock(path_dirt_slab),
			Item.getItemFromBlock(path_gravel),
			Item.getItemFromBlock(path_gravel_slab),
			Item.getItemFromBlock(path_stepping_stones),
			Item.getItemFromBlock(path_stepping_stones_slab),
			Item.getItemFromBlock(thatch_bamboo),
			Item.getItemFromBlock(thatch_bamboo_stairs),
			Item.getItemFromBlock(thatch_bamboo_slab),
			Item.getItemFromBlock(thatch_bamboo_edge),
			Item.getItemFromBlock(rammed_dirt),
			Item.getItemFromBlock(burnt_spruce_foundation),
			Item.getItemFromBlock(spruce_foundation_slab),
			Item.getItemFromBlock(burnt_spruce_timber_frame),
			Item.getItemFromBlock(burnt_spruce_timber_frame_pillar),
			Item.getItemFromBlock(burnt_spruce_log_covered),
			Item.getItemFromBlock(burnt_spruce_beam),
			Item.getItemFromBlock(burnt_spruce_log_wall),
			Item.getItemFromBlock(burnt_spruce_railing),
			Item.getItemFromBlock(burnt_spruce_log_fence),
			Item.getItemFromBlock(burnt_spruce_support_beam),
			Item.getItemFromBlock(spruce_support_slab),
			Item.getItemFromBlock(spruce_planks_edge),
			Item.getItemFromBlock(spruce_roof_support),
			Item.getItemFromBlock(paper_wall),
			Item.getItemFromBlock(paper_wall_flat),
			Item.getItemFromBlock(paper_wall_window),
			Item.getItemFromBlock(flowery_paper_wall),
			DoTBItems.paper_door,
			Item.getItemFromBlock(fireplace),
			Item.getItemFromBlock(irori),
			Item.getItemFromBlock(iron_chain),
			Item.getItemFromBlock(grey_roof_tiles),
			Item.getItemFromBlock(grey_roof_tiles_stairs),
			Item.getItemFromBlock(grey_roof_tiles_slab),
			Item.getItemFromBlock(grey_roof_tiles_edge),
			Item.getItemFromBlock(grey_roof_tiles_wall),
			DoTBItems.grey_tile,
			DoTBItems.grey_clay_tile,
			Item.getItemFromBlock(red_painted_log),
			DoTBItems.little_flag,
			Item.getItemFromBlock(paper_folding_screen),
			Item.getItemFromBlock(paper_lantern),
			Item.getItemFromBlock(paper_lamp),
			Item.getItemFromBlock(stone_lantern),
			Item.getItemFromBlock(small_tatami_mat),
			DoTBItems.tatami_mat,
			Item.getItemFromBlock(tatami_floor),
			DoTBItems.futon,
			Item.getItemFromBlock(spruce_low_table),
			Item.getItemFromBlock(spruce_legless_chair),
			Item.getItemFromBlock(ikebana_flower_pot),
			Item.getItemFromBlock(sake_bottle),
			Item.getItemFromBlock(sake_cup),
			Item.getItemFromBlock(cast_iron_teapot),
			Item.getItemFromBlock(cast_iron_teacup),
			Item.getItemFromBlock(bamboo_drying_tray),
			DoTBItems.camellia_seed,
			DoTBItems.camellia_leaves,
			DoTBItems.tea_leaves,
			DoTBItems.mulberry,
			DoTBItems.mulberry_leaves,
			DoTBItems.silk_worm_eggs,
			DoTBItems.silk_worm_hatchery,
			DoTBItems.silk_worms,
			DoTBItems.silk_cocoons,
			DoTBItems.silk,
			DoTBItems.stick_bundle,
			DoTBItems.rice,
			DoTBItems.tachi_sword,
			DoTBItems.bamboo_hat,
			DoTBItems.japanese_light_armor_head,
			DoTBItems.japanese_light_armor_chest,
			DoTBItems.japanese_light_armor_legs,
			DoTBItems.japanese_light_armor_feet,
			DoTBItems.o_yoroi_armor_head,
			DoTBItems.o_yoroi_armor_chest,
			DoTBItems.o_yoroi_armor_legs,
			DoTBItems.o_yoroi_armor_feet,
			DoTBItems.raijin_armor_head,
			DoTBItems.raijin_armor_chest,
			DoTBItems.raijin_armor_legs,
			DoTBItems.raijin_armor_feet
	),

	PRE_COLOMBIAN("Pre-Colombian",
			Item.getItemFromBlock(path_dirt),
			Item.getItemFromBlock(path_dirt_slab),
			Item.getItemFromBlock(path_gravel),
			Item.getItemFromBlock(path_gravel_slab),
			Item.getItemFromBlock(thatch_wheat),
			Item.getItemFromBlock(thatch_wheat_stairs),
			Item.getItemFromBlock(thatch_wheat_slab),
			Item.getItemFromBlock(thatch_wheat_edge),
			Item.getItemFromBlock(rammed_dirt),
			Item.getItemFromBlock(plastered_stone),
			Item.getItemFromBlock(red_plastered_stone),
			Item.getItemFromBlock(red_ornamented_plastered_stone),
			Item.getItemFromBlock(plastered_stone_stairs),
			Item.getItemFromBlock(plastered_stone_plate),
			Item.getItemFromBlock(plastered_stone_slab),
			Item.getItemFromBlock(plastered_stone_edge),
			Item.getItemFromBlock(red_plastered_stone_stairs),
			Item.getItemFromBlock(red_plastered_stone_plate),
			Item.getItemFromBlock(red_plastered_stone_slab),
			Item.getItemFromBlock(red_plastered_stone_edge),
			Item.getItemFromBlock(chiseled_plastered_stone),
			Item.getItemFromBlock(plastered_stone_frieze),
			Item.getItemFromBlock(green_plastered_stone_frieze),
			Item.getItemFromBlock(green_ornamented_plastered_stone_frieze),
			Item.getItemFromBlock(green_sculpted_plastered_stone_frieze),
			Item.getItemFromBlock(green_small_plastered_stone_frieze),
			Item.getItemFromBlock(red_plastered_stone_frieze),
			Item.getItemFromBlock(red_ornamented_plastered_stone_frieze),
			Item.getItemFromBlock(red_sculpted_plastered_stone_frieze),
			Item.getItemFromBlock(red_small_plastered_stone_frieze),
			Item.getItemFromBlock(plastered_stone_column),
			Item.getItemFromBlock(plastered_stone_window),
			Item.getItemFromBlock(plastered_stone_cresset),
			Item.getItemFromBlock(feathered_serpent_sculpture),
			Item.getItemFromBlock(serpent_sculpted_column),
			DoTBItems.commelina,
			DoTBItems.maize
	),

	ROMAN("Roman",
			Item.getItemFromBlock(ochre_roof_tiles),
			Item.getItemFromBlock(ochre_roof_tiles_slab),
			Item.getItemFromBlock(sandstone_column)
	),

	EGYPTIAN("Egyptian",
			 DoTBItems.pharaoh_armor_head,
			 DoTBItems.pharaoh_armor_chest,
			 DoTBItems.pharaoh_armor_legs,
			 DoTBItems.pharaoh_armor_feet
			 );

	private String name;
	private ArrayList<Item> items = new ArrayList<>();

	CreativeInventoryCategories(String name, Item... items) {
		this.name = name;
		this.items.addAll(Arrays.asList(items));
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Item> getItems(){
		return this.items;
	}
}