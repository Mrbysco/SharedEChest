package easton.sharedechest.mixin;

import easton.sharedechest.menu.SharedEnderChestMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.EnderChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnderChestBlock.class)
public class EnderChestBlockMixin {

	@ModifyArg(
			method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;"),
			index = 0
	)
	private MenuProvider useShareMenu(MenuProvider factory) {
		return new SimpleMenuProvider((syncId, playerInventory, player) -> {
			return new SharedEnderChestMenu(syncId, playerInventory, ((ChestMenu) factory.createMenu(3, playerInventory, player)).getContainer());
		}, Component.translatable("container.enderchest"));
	}

}
