package easton.sharedechest;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SimpleContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Constants {

	public static final String MOD_ID = "sharedechest";
	public static final String MOD_NAME = "Shared Ender Chest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final ResourceLocation BUTTON_PRESS_ID = modLoc("button_press");

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}


}