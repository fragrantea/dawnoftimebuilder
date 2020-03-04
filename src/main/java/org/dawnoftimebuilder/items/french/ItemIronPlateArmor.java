package org.dawnoftimebuilder.items.french;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.client.renderer.model.ModelIronPlateArmor;
import org.dawnoftimebuilder.items.general.DoTBItemCustomArmor;

import static org.dawnoftimebuilder.enums.EArmorMaterial.IRONPLATE;

public class ItemIronPlateArmor extends DoTBItemCustomArmor {
	public ItemIronPlateArmor(EntityEquipmentSlot equipmentSlotIn) {
		super("iron_plate_armor", IRONPLATE.getArmorMaterial(), equipmentSlotIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createModel(){
		return new ModelIronPlateArmor(0.0F, this.armorType, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped createSlimModel() {
		return new ModelIronPlateArmor(0.0F, this.armorType, false);
	}
}
