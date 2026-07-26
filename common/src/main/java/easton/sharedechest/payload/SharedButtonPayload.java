package easton.sharedechest.payload;

import easton.sharedechest.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SharedButtonPayload(boolean shared) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SharedButtonPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			SharedButtonPayload::shared,
			SharedButtonPayload::new);
	public static final Type<SharedButtonPayload> ID = new Type<>(Constants.BUTTON_PRESS_ID);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
