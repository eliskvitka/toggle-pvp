package me.kvitka.togglepvp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TogglePvp implements ModInitializer {
	public static final String MOD_ID = "togglepvp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");

		PvpAbility.init();

		CommandRegistrationCallback.EVENT.register(CommandRegistry::register);
	}
}
