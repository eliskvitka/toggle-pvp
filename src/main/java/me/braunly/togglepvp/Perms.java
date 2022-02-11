package me.braunly.togglepvp;

import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.command.CommandSource;

public class Perms {
    public static final class Registry {
        public static final String base = TogglePvp.MOD_ID + ".base";
        public static final String others = TogglePvp.MOD_ID + ".others";
        public static final String exempt = TogglePvp.MOD_ID + ".exempt";
    }

    static void init() {
        PermissionCheckEvent.EVENT.register((source, permission) -> {
            if (isSuperAdmin(source)) {
                return TriState.TRUE;
            }
            return TriState.DEFAULT;
        });
    }

    private static boolean isSuperAdmin(CommandSource source) {
        return source.hasPermissionLevel(4);
    }
}
