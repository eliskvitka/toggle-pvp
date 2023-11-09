package me.braunly.togglepvp;

public class Perms {
    public static final class Registry {
        // Access to /pvp command
        public static final String base = TogglePvp.MOD_ID + ".base";
        // Access to /pvp <target_player> command flag
        public static final String others = TogglePvp.MOD_ID + ".others";
        // Ignore cooldown settings for pvp status changing
        public static final String ignoreCooldown = TogglePvp.MOD_ID + ".ignorecooldown";
    }
}
