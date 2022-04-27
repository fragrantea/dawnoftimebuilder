package org.dawnoftimebuilder.registry;

import net.minecraft.inventory.container.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.container.DisplayerContainer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBContainersRegistry {

	public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

	public static final RegistryObject<MenuType<DisplayerContainer>> DISPLAYER_CONTAINER = reg("displayer", (IContainerFactory<DisplayerContainer>) (windowId, playerInventory, data) -> new DisplayerContainer(windowId, playerInventory, playerInventory.player.level, data.readBlockPos()));

	private static <T extends Container> RegistryObject<MenuType<T>> reg(String name, MenuType.IFactory<T> factory) {
		MenuType<T> type = new MenuType<>(factory);
		return CONTAINER_TYPES.register(name, () -> type);
	}
}
