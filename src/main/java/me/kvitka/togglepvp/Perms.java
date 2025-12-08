package me.kvitka.togglepvp;

public class Perms {
    public static final class Registry {
        // Access to /pvp command
        public static final String base = TogglePvp.MOD_ID + ".base";
        // Access to /pvp <target_player> command flag
        public static final String others = TogglePvp.MOD_ID + ".others";
        // Cooldown settings for pvp status changing
        public static final String useCooldown = TogglePvp.MOD_ID + ".cooldown";
        public static final String ignoreCooldown = TogglePvp.MOD_ID + ".cooldown.ignore";
        // Set PVP state automatically based on permission
        public static final String forceEnable = TogglePvp.MOD_ID + ".force.enable";
        public static final String forceDisable = TogglePvp.MOD_ID + ".force.disable";
    }
}
