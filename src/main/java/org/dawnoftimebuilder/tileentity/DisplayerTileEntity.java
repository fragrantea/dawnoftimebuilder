package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.dawnoftimebuilder.container.DisplayerContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public class DisplayerTileEntity extends TileEntity implements INamedContainerProvider {

	private final ItemStackHandler itemHandler = createHandler();

	public DisplayerTileEntity() {
		super(DISPLAYER_TE.get());
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", itemHandler.serializeNBT());
		return tag;
	}
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		//Only on client side
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.handleUpdateTag(state, tag);
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		return super.save(tag);
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.load(state, tag);
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(this.getType().getRegistryName().getPath());
	}

	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		if(this.getLevel() == null) return null;
		return new DisplayerContainer(windowID, playerInventory, this.getLevel(), this.getBlockPos());
	}
}