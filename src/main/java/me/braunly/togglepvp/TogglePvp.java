package me.braunly.togglepvp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TogglePvp implements ModInitializer {
	public static final String MOD_ID = "togglepvp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");

		PvpAbility.init();

		ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
			// Init permissions
			Perms.init();
		});

		CommandRegistrationCallback.EVENT.register(CommandRegistry::register);
	}
}
