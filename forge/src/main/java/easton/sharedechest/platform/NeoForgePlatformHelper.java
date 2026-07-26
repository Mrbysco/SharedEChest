package easton.sharedechest.platform;

import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.payload.SharedButtonPayload;
import easton.sharedechest.platform.services.IPlatformHelper;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public class NeoForgePlatformHelper implements IPlatformHelper {
	@Override
	public MenuType<SharedEnderChestMenu> createMenuType() {
		return IMenuTypeExtension.create(SharedEnderChestMenu::create);
	}

	@Override
	public void onSharedButtonPress(boolean shared) {
		ClientPacketDistributor.sendToServer(new SharedButtonPayload(shared));
	}
}
