package me.braunly.togglepvp;

import io.github.ladysnake.pal.*;
import me.braunly.togglepvp.command.PvpCommand;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
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

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player instanceof ServerPlayerEntity && entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity attackingPlayer = (ServerPlayerEntity) player;
                ServerPlayerEntity targetPlayer = (ServerPlayerEntity) entity;
                PvpCommand.resetCooldown(targetPlayer, attackingPlayer);
            }
            return ActionResult.PASS;
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
