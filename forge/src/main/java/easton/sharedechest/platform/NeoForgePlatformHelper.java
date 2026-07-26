package easton.sharedechest.platform;

import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.payload.SharedButtonPayload;
import easton.sharedechest.platform.services.IPlatformHelper;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {
	@Override
	public MenuType<SharedEnderChestMenu> createMenuType() {
		return IMenuTypeExtension.create(SharedEnderChestMenu::create);
	}

	@Override
	public void onSharedButtonPress(boolean shared) {
		PacketDistributor.sendToServer(new SharedButtonPayload(shared));
	}
}
