package me.braunly.togglepvp;

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

	public static class Perms {
		public static final class Registry {
			// Access to /pvp command
			public static final String base = MOD_ID + ".base";
			// Access to /pvp <target_player> command flag
			public static final String others = MOD_ID + ".others";
			// Ignore cooldown settings for pvp status changing
			public static final String ignoreCooldown = MOD_ID + ".ignorecooldown";
		}
	}
}
