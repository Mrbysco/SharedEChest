package easton.sharedechest;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "sharedechest";
	public static final String MOD_NAME = "Shared Ender Chest";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final Identifier BUTTON_PRESS_ID = modLoc("button_press");

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}


}