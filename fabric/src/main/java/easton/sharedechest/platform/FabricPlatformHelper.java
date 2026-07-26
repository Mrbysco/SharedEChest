package easton.sharedechest.platform;

import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.payload.SharedButtonPayload;
import easton.sharedechest.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class FabricPlatformHelper implements IPlatformHelper {
	@Override
	public MenuType<SharedEnderChestMenu> createMenuType() {
		return new MenuType<>(SharedEnderChestMenu::new, FeatureFlagSet.of());
	}

	@Override
	public void onSharedButtonPress(boolean shared) {
		ClientPlayNetworking.send(new SharedButtonPayload(shared));
	}
}
