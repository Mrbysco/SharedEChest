package easton.sharedechest;

import easton.sharedechest.handlers.ServerPayloadHandler;
import easton.sharedechest.payload.SharedButtonPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class SharedEChest {

	public SharedEChest(IEventBus eventBus) {
		CommonClass.init();

		eventBus.addListener(this::setupPackets);
	}

	private void setupPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID).optional();
		registrar.playToServer(SharedButtonPayload.ID, SharedButtonPayload.CODEC, ServerPayloadHandler.getInstance()::handleSharedButton);
	}
}