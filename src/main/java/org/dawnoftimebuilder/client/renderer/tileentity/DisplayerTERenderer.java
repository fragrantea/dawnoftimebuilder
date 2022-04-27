package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

@OnlyIn(Dist.CLIENT)
public class DisplayerTERenderer extends BlockEntityRenderer<DisplayerTileEntity> {

	public DisplayerTERenderer(BlockEntityRenderDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}

	@Override
	public void render(DisplayerTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn){
		BlockState state = tileEntity.getBlockState();
		if(state.getBlock() instanceof DisplayerBlock){
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				ItemStack itemStack;
				DisplayerBlock block = (DisplayerBlock) state.getBlock();
				double xStart = block.getDisplayerX(state);
				double yStart = block.getDisplayerY(state);
				double zStart = block.getDisplayerZ(state);
				float rotationAngle;

				for(int i = 0; i < 9; i++) {
					//After checking if the capability is there and set all the variables, we render each of the non-empty itemStack
					itemStack = h.getStackInSlot(i);
					ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

					if (!itemStack.isEmpty()) {
						matrixStack.pushPose();
						matrixStack.translate(xStart, yStart, zStart);
						matrixStack.translate((0.5D - xStart) * (i % 3), 0.05D, (0.5D - zStart) * Math.floor((double) i / 3));
						if (i == 0 || i == 8) rotationAngle = 20.0F;
						else if (i == 2 || i == 6) rotationAngle = -20.0F;
						else rotationAngle = 0.0F;
						Item item = itemStack.getItem();
						if (item instanceof BlockItem) {
							matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationAngle));
							Block blockFromItem = ((BlockItem) item).getBlock();
							if (blockFromItem instanceof IBlockSpecialDisplay) {
								float scale = ((IBlockSpecialDisplay) blockFromItem).getDisplayScale();
								matrixStack.scale(scale, scale, scale);
								matrixStack.translate(0.0F, 0.485F, 0.0F);
							} else {
								matrixStack.scale(0.2F, 0.2F, 0.2F);
								matrixStack.translate(0.0F, 0.4F, 0.0F);
							}
							itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
						} else {
							matrixStack.scale(0.3F, 0.3F, 0.3F);
							matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationAngle + 90.0F));
							matrixStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
							itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
						}
						matrixStack.popPose();
					}
				}
			});
		}
	}
}
