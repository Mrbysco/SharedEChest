package easton.sharedechest.handlers;

import easton.sharedechest.InventoryManager;
import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.payload.SharedButtonPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
	private static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleSharedButton(final SharedButtonPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Player player = context.player();
					if (player != null) {
						if (player.containerMenu instanceof SharedEnderChestMenu secMenu) {
							boolean shared = payload.shared();
							if (shared) {
								secMenu.inventory = InventoryManager.sharedInventory;
							} else {
								secMenu.inventory = ((SharedEnderChestMenu) player.containerMenu).personalInv;
							}
							secMenu.createSlots(player.getInventory());
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("sharedechest.networking.shared_button.failed", e.getMessage()));
					return null;
				});
	}
}
