package easton.sharedechest.client;

import easton.sharedechest.registry.ModMenus;
import easton.sharedechest.screen.SharedEnderChestScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void registerMenuScreen(RegisterMenuScreensEvent event) {
		event.register(ModMenus.SHARED_ENDER_CHEST.get(), SharedEnderChestScreen::new);
	}
}
