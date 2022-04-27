package org.dawnoftimebuilder.item.egyptian;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.client.model.armor.CustomArmorModel;
import org.dawnoftimebuilder.client.model.armor.PharaohArmorModel;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;

import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.PHARAOH;

public class PharaohArmorItem extends CustomArmorItem {

	public PharaohArmorItem(EquipmentSlot slot) {
		super("pharaoh_armor", PHARAOH, slot);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, true);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public CustomArmorModel<LivingEntity> createSlimModel(LivingEntity entityLiving) {
		return new PharaohArmorModel<>(this.slot, false);
	}
}