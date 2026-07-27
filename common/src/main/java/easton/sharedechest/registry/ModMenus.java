package easton.sharedechest.registry;

import easton.sharedechest.Constants;
import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.platform.Services;
import easton.sharedechest.registration.RegistrationProvider;
import easton.sharedechest.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
	public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(BuiltInRegistries.MENU, Constants.MOD_ID);

	public static final RegistryObject<MenuType<?>, MenuType<SharedEnderChestMenu>> SHARED_ENDER_CHEST = MENU_TYPES.register(
			"shared_chest",
			Services.PLATFORM::createMenuType);


	public static void load() {
		// Load class
	}
}
