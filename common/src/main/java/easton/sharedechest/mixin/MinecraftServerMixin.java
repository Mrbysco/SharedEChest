package easton.sharedechest.mixin;

import easton.sharedechest.InventoryManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.function.Function;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(method = "halt(Z)V", at = @At("HEAD"))
	private void saveSharedInv(boolean bl, CallbackInfo ci) throws IOException {
		InventoryManager.saveSharedInv((MinecraftServer) (Object) this);
	}

	@Inject(method = "spin(Ljava/util/function/Function;)Lnet/minecraft/server/MinecraftServer;", at = @At("RETURN"))
	private static <S extends MinecraftServer> void loadInv(Function<Thread, S> serverFactory, CallbackInfoReturnable<S> cir) throws IOException {
		InventoryManager.loadSharedInv(cir.getReturnValue());
	}

}
