package easton.sharedechest;

import easton.sharedechest.registry.ModMenus;
import easton.sharedechest.screen.SharedEnderChestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.gui.screens.MenuScreens;

public class SharedEChestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register(client ->
				MenuScreens.register(ModMenus.SHARED_ENDER_CHEST.get(), SharedEnderChestScreen::new));
	}
}
