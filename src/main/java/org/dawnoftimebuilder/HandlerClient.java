package org.dawnoftimebuilder;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.dawnoftimebuilder.client.gui.creative.CreativeInventoryEvents;
import org.dawnoftimebuilder.client.gui.screen.DisplayerScreen;
import org.dawnoftimebuilder.client.renderer.entity.ChairRenderer;
import org.dawnoftimebuilder.client.renderer.entity.JapaneseDragonRenderer;
import org.dawnoftimebuilder.client.renderer.entity.SilkmothRenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DisplayerTERenderer;
import org.dawnoftimebuilder.client.renderer.tileentity.DryerTERenderer;

import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.DISPLAYER_CONTAINER;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.*;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

@Mod.EventBusSubscriber(modid = DawnOfTimeBuilder.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HandlerClient {

	@SubscribeEvent
	public static void fMLClientSetupEvent(final FMLClientSetupEvent event){
		MinecraftForge.EVENT_BUS.register(new CreativeInventoryEvents());

		ScreenManager.register(DISPLAYER_CONTAINER.get(), DisplayerScreen::new);

		ClientRegistry.bindTileEntityRenderer(DISPLAYER_TE.get(), DisplayerTERenderer::new);
		ClientRegistry.bindTileEntityRenderer(DRYER_TE.get(), DryerTERenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(SILKMOTH_ENTITY.get(), SilkmothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(JAPANESE_DRAGON_ENTITY.get(), JapaneseDragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CHAIR_ENTITY.get(), ChairRenderer::new);

		//Above Minecraft 1.15, we need to register renderLayer here
		// General
		ItemBlockRenderTypes.setRenderLayer(ACACIA_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(ACACIA_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(ACACIA_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BIRCH_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BIRCH_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BIRCH_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DARK_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DARK_OAK_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DARK_OAK_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(JUNGLE_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(JUNGLE_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(JUNGLE_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OAK_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OAK_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(OAK_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(FIREPLACE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(IRON_PORTCULLIS.get(), RenderType.cutoutMipped());

		//French
		ItemBlockRenderTypes.setRenderLayer(LIMESTONE_FIREPLACE.get(), RenderType.cutoutMipped());

		//German
		ItemBlockRenderTypes.setRenderLayer(LATTICE_GLASS.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(LATTICE_GLASS_PANE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(LATTICE_WAXED_OAK_WINDOW.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(LATTICE_STONE_BRICKS_WINDOW.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(STONE_BRICKS_ARROWSLIT.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(STONE_BRICKS_FIREPLACE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_DOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_TRAPDOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WAXED_OAK_CHANDELIER.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(PLANTER_GERANIUM_PINK.get(), RenderType.cutoutMipped());

		//Japanese
		ItemBlockRenderTypes.setRenderLayer(CHARRED_SPRUCE_DOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CHARRED_SPRUCE_TRAPDOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CHARRED_SPRUCE_PERGOLA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CHARRED_SPRUCE_LATTICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CHARRED_SPRUCE_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(RED_PAINTED_BEAM.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CAST_IRON_TEAPOT_GRAY.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CAST_IRON_TEAPOT_GREEN.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CAST_IRON_TEAPOT_DECORATED.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BAMBOO_DRYING_TRAY.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CAMELLIA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(MULBERRY.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(IKEBANA_FLOWER_POT.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WHITE_LITTLE_FLAG.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(RICE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(IRORI_FIREPLACE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(SAKE_BOTTLE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(STICK_BUNDLE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(STONE_LANTERN.get(), RenderType.cutoutMipped());

		//Pre_columbian
		ItemBlockRenderTypes.setRenderLayer(COMMELINA.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(WILD_MAIZE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(MAIZE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(PLASTERED_STONE_CRESSET.get(), RenderType.cutoutMipped());

		//Roman
		ItemBlockRenderTypes.setRenderLayer(WILD_GRAPE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(CYPRESS.get(), RenderType.cutoutMipped());
	}
}
