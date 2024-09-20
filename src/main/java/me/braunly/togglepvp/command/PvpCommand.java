package me.braunly.togglepvp.command;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.braunly.togglepvp.PvpAbility;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PvpCommand implements Command<ServerCommandSource> {

    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final HashMap<UUID, Boolean> isNotificationSent = new HashMap<>();

    private static final long COOLDOWN_TIME = 30 * 1000; // 30 segundos en milisegundos

    public PvpCommand() {

    }

    public static void resetCooldown(ServerPlayerEntity target, ServerPlayerEntity attacker) {
        UUID targetPlayerUUID = target.getUuid();
        UUID attackerPlayerUUID = attacker.getUuid();

        if (PvpAbility.get(target) && PvpAbility.get(attacker)) {
            cooldowns.put(targetPlayerUUID, System.currentTimeMillis());
            cooldowns.put(attackerPlayerUUID, System.currentTimeMillis());

            if (isNotificationSent.containsKey(target.getUuid())) {
                if (!isNotificationSent.get(target.getUuid())){
                    target.sendMessage(Text.literal("")
                            .append(Text.translatable("message.togglepvp.combat_cooldown"))
                            .styled(s -> s.withColor(Formatting.YELLOW)));
                    isNotificationSent.put(target.getUuid(), true);

                }
            } else {
                target.sendMessage(Text.literal("")
                        .append(Text.translatable("message.togglepvp.combat_cooldown"))
                        .styled(s -> s.withColor(Formatting.YELLOW)));
                isNotificationSent.put(target.getUuid(), true);
            }
            if (isNotificationSent.containsKey(attacker.getUuid())) {
                if (!isNotificationSent.get(attacker.getUuid())){
                    attacker.sendMessage(Text.literal("")
                            .append(Text.translatable("message.togglepvp.combat_cooldown"))
                            .styled(s -> s.withColor(Formatting.YELLOW)));
                    isNotificationSent.put(attacker.getUuid(), true);

                }
            } else {
                attacker.sendMessage(Text.literal("")
                        .append(Text.translatable("message.togglepvp.combat_cooldown"))
                        .styled(s -> s.withColor(Formatting.YELLOW)));
                isNotificationSent.put(attacker.getUuid(), true);
            }


        }
    }

    private static boolean isOnCooldown(ServerPlayerEntity player) {
        if (cooldowns.containsKey(player.getUuid())) {
            long timeElapsed = System.currentTimeMillis() - cooldowns.get(player.getUuid());
            return timeElapsed < COOLDOWN_TIME;
        }
        return false;
    }

    public static SuggestionProvider<ServerCommandSource> suggestedState() {
        return (context, builder) -> {
            builder.suggest(Text.translatable("command.autocomplete.togglevpvp.enable").getString());
            builder.suggest(Text.translatable("command.autocomplete.togglevpvp.disable").getString());
            return builder.buildFuture();
        };
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (isOnCooldown(Objects.requireNonNull(context.getSource().getPlayer()))) {
            long timeLeft = getRemainingCooldown(context.getSource().getPlayer());
            ServerPlayerEntity sender = context.getSource().getPlayer();
            if (sender != null) {
                sender.sendMessage(Text.literal("")
                        .append(Text.translatable("message.togglepvp.cooldown", timeLeft))
                        .styled(s -> s.withColor(Formatting.RED)));
            }
            return 0;
        }


        setCooldown(Objects.requireNonNull(context.getSource().getPlayer()));

        isNotificationSent.put(context.getSource().getPlayer().getUuid(), false);
        return setStatus(context.getSource().getPlayer(), !PvpAbility.get(context.getSource().getPlayer()));
    }

    private void setCooldown(ServerPlayerEntity player) {
        cooldowns.put(player.getUuid(), System.currentTimeMillis());
    }

    private long getRemainingCooldown(ServerPlayerEntity player) {
        long timeElapsed = System.currentTimeMillis() - cooldowns.get(player.getUuid());
        return (COOLDOWN_TIME - timeElapsed) / 1000;
    }

    public int setOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity sender = context.getSource().getPlayer();
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, "target_player");
        boolean pvpStatus = StringArgumentType.getString(context, "pvp_status").equalsIgnoreCase("on");
        if (sender != null) {
            sender.sendMessage(Text.literal("")
                    .append(Text.translatable("message.togglepvp.pvp_status.other"))
                    .append(Text.literal(targetPlayer.getDisplayName().getString())
                            .styled(s -> s.withColor(Formatting.AQUA)))
                    .append(": ")
                    .append(pvpStatus
                            ? Text.translatable("message.togglepvp.pvp_state.enabled")
                            .styled(s -> s.withColor(Formatting.RED))
                            : Text.translatable("message.togglepvp.pvp_state.disabled")
                            .styled(s -> s.withColor(Formatting.GREEN))), false);
        }
        return setStatus(targetPlayer, pvpStatus);
    }

    public int setStatus(ServerPlayerEntity player, boolean pvpState) throws CommandSyntaxException {
        PvpAbility.set(player, pvpState);

        boolean pvpStatus = PvpAbility.get(player);
        player.sendMessage(Text.literal("")
                .append(Text.translatable("message.togglepvp.pvp_status"))
                .append(pvpStatus ? Text.translatable("message.togglepvp.pvp_state.enabled") : Text.translatable("message.togglepvp.pvp_state.disabled"))
                .styled(s -> s.withColor(Formatting.YELLOW)));
        return 0;
    }
}