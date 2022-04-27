package org.dawnoftimebuilder.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.dawnoftimebuilder.tileentity.DryerTileEntity;

import net.minecraft.client.Minecraft;

@OnlyIn(Dist.CLIENT)
public class DryerTERenderer extends BlockEntityRenderer<DryerTileEntity> {

    public DryerTERenderer(BlockEntityRenderDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
	public void render(DryerTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            this.renderItemModel(matrixStack, h.getStackInSlot(0), buffer, combinedLightIn, combinedOverlayIn);
            matrixStack.translate(0, 0.5D, 0);
            this.renderItemModel(matrixStack, h.getStackInSlot(1), buffer, combinedLightIn, combinedOverlayIn);
        });
	}

	public void renderItemModel(PoseStack matrixStack, ItemStack itemStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn){
	    if(itemStack.isEmpty()) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        boolean isBlockItem = itemStack.getItem() instanceof BlockItem;
        for(int i = 0; i < 4; i++) {
            matrixStack.pushPose();
            matrixStack.translate(0.35D, 0, 0.35D);
            matrixStack.translate((i == 1 || i == 2) ? 0.3D : 0, 0.1D, i >= 2 ? 0.3D : 0);
            matrixStack.mulPose(Vector3f.YN.rotationDegrees(90.0F * i));
            if (isBlockItem) {
                matrixStack.scale(0.2F, 0.2F, 0.2F);
                matrixStack.translate(0.0F, 0.4F, 0.0F);
                itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
            } else {
                matrixStack.mulPose(Vector3f.XN.rotationDegrees(90.0F));
                matrixStack.scale(0.3F, 0.3F, 0.3F);
                itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
            }
            matrixStack.popPose();
        }
    }
}