package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.entity.SilkmothModel;
import org.dawnoftimebuilder.entity.SilkmothEntity;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class SilkmothRenderer extends MobRenderer<SilkmothEntity, SilkmothModel> {

	private static final ResourceLocation SILKMOTH_TEXTURES = new ResourceLocation(MOD_ID, "textures/entity/silkmoth.png");

	public SilkmothRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SilkmothModel(), 0.1F);
	}

	@Override
	public ResourceLocation getTextureLocation(SilkmothEntity entity) {
		return SILKMOTH_TEXTURES;
	}
}
