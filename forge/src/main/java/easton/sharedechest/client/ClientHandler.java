package easton.sharedechest.client;

import easton.sharedechest.registry.ModMenus;
import easton.sharedechest.screen.SharedEnderChestScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientHandler {

	public static void registerMenuScreen(RegisterMenuScreensEvent event) {
		event.register(ModMenus.SHARED_ENDER_CHEST.get(), SharedEnderChestScreen::new);
	}
}
