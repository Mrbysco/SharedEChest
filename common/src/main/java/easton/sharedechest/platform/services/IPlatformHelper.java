package easton.sharedechest.platform.services;

import easton.sharedechest.menu.SharedEnderChestMenu;
import net.minecraft.world.inventory.MenuType;

public interface IPlatformHelper {

	MenuType<SharedEnderChestMenu> createMenuType();

	void onSharedButtonPress(boolean shared);
}
