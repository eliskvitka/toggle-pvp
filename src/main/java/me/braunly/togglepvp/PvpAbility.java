package me.braunly.togglepvp;

import io.github.ladysnake.pal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class PvpAbility {
    private static final Identifier id = Identifier.of(TogglePvp.MOD_ID, "pvp_state");
    private static final PlayerAbility PVP_ABILITY = Pal.registerAbility(id, SimpleAbilityTracker::new);
    private static final AbilitySource abilitySource = Pal.getAbilitySource(id);

    public static void init() {
        PlayerAbilityEnableCallback.EVENT.register((player, ability, abilitySource) -> {
            if (ability == PVP_ABILITY) {
                return !PVP_ABILITY.isEnabledFor(player);
            }
            return true;
        });
    }

    public static void set(PlayerEntity player, boolean state) {
        if (state) {
            abilitySource.grantTo(player, PVP_ABILITY);
        } else {
            abilitySource.revokeFrom(player, PVP_ABILITY);
        }
    }

    public static boolean get(PlayerEntity player) {
        return PVP_ABILITY.isEnabledFor(player);
    }
}
