package org.dawnoftimebuilder.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CustomArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

	public final EquipmentSlot slot;

	public CustomArmorModel(EquipmentSlot slot, int textureWidthIn, int textureHeightIn){
		super(0.0F, 0.0F, textureWidthIn, textureHeightIn);
		this.slot = slot;
	}

	public void setupArmorAnim(T entityIn, float ageInTicks) {
		//Fix the "breathing" and wrong head rotation on ArmorStands
		if (entityIn instanceof ArmorStandEntity) {
			ArmorStandEntity entityAS = (ArmorStandEntity) entityIn;
			float f = (float) Math.PI / 180F;
			this.head.xRot = f * entityAS.getHeadPose().getX();
			this.head.yRot = f * entityAS.getHeadPose().getY();
			this.head.zRot = f * entityAS.getHeadPose().getZ();
			this.head.setPos(0.0F, 1.0F, 0.0F);
			this.body.xRot = f * entityAS.getBodyPose().getX();
			this.body.yRot = f * entityAS.getBodyPose().getY();
			this.body.zRot = f * entityAS.getBodyPose().getZ();
			this.leftArm.xRot = f * entityAS.getLeftArmPose().getX();
			this.leftArm.yRot = f * entityAS.getLeftArmPose().getY();
			this.leftArm.zRot = f * entityAS.getLeftArmPose().getZ();
			this.rightArm.xRot = f * entityAS.getRightArmPose().getX();
			this.rightArm.yRot = f * entityAS.getRightArmPose().getY();
			this.rightArm.zRot = f * entityAS.getRightArmPose().getZ();
			this.leftLeg.xRot = f * entityAS.getLeftLegPose().getX();
			this.leftLeg.yRot = f * entityAS.getLeftLegPose().getY();
			this.leftLeg.zRot = f * entityAS.getLeftLegPose().getZ();
			this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
			this.rightLeg.xRot = f * entityAS.getRightLegPose().getX();
			this.rightLeg.yRot = f * entityAS.getRightLegPose().getY();
			this.rightLeg.zRot = f * entityAS.getRightLegPose().getZ();
			this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
			this.hat.copyFrom(this.head);
			return;
		}
	}

	public static void setRotateAngle(ModelRenderer renderer, float x, float y, float z) {
		renderer.xRot = x;
		renderer.yRot = y;
		renderer.zRot = z;
	}

	public static float sinPI(float f) {
		return Mth.sin(f * (float)Math.PI);
	}
}
