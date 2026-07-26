package easton.sharedechest;

import easton.sharedechest.menu.SharedEnderChestMenu;
import easton.sharedechest.payload.SharedButtonPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;

public class SharedEChest implements ModInitializer {

	@Override
	public void onInitialize() {
		CommonClass.init();

		PayloadTypeRegistry.playC2S().register(SharedButtonPayload.ID, SharedButtonPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SharedButtonPayload.ID, (payload, context) -> {
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
		});

	}
}
