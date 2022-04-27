package org.dawnoftimebuilder.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.entity.JapaneseDragonModel;
import org.dawnoftimebuilder.entity.JapaneseDragonEntity;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class JapaneseDragonRenderer extends MobRenderer<JapaneseDragonEntity, JapaneseDragonModel> {

	private static final ResourceLocation JAPANESE_DRAGON_TEXTURES = new ResourceLocation(MOD_ID, "textures/entity/japanese_dragon.png");

	public JapaneseDragonRenderer(Context renderManagerIn) {
		//TODO Adapt shadowSizeIn to the dragon's size.
		super(renderManagerIn, new JapaneseDragonModel(), 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(JapaneseDragonEntity p_110775_1_) {
		return JAPANESE_DRAGON_TEXTURES;
	}
}
