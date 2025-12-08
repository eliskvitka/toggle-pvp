package me.kvitka.togglepvp.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.kvitka.togglepvp.PvpAbility;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PvpCommand implements Command<ServerCommandSource> {

    public PvpCommand() {

    }
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // TODO: add cooldown check
        return setStatus(context.getSource().getPlayer(), !PvpAbility.get(context.getSource().getPlayer()));
    }

    public int setOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource sender = context.getSource();
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, "target_player");
        boolean pvpStatus = StringArgumentType.getString(context, "pvp_status").equalsIgnoreCase("on");
        if (sender != null) {
            sender.sendMessage(Text.literal("")
                    .append(Text.translatable("message.togglepvp.pvp_status.other").getString())
                    .append(Text.literal(targetPlayer.getDisplayName().getString())
                            .styled(s -> s.withColor(Formatting.AQUA)))
                    .append(": ")
                    .append(pvpStatus
                            ? Text.translatable("message.togglepvp.pvp_state.enabled")
                            .styled(s -> s.withColor(Formatting.RED)).getString()
                            : Text.translatable("message.togglepvp.pvp_state.disabled")
                            .styled(s -> s.withColor(Formatting.GREEN)).getString()));
        }
        return setStatus(targetPlayer, pvpStatus);
    }

    public int setStatus(ServerPlayerEntity player, boolean pvpState) throws CommandSyntaxException {
        PvpAbility.set(player, pvpState);

        boolean pvpStatus = PvpAbility.get(player);
        player.sendMessage(Text.literal("")
                .append(Text.translatable("message.togglepvp.pvp_status")
                        .styled(s -> s.withColor(Formatting.WHITE)).getString() + "- ")
                .append(pvpStatus
                        ? Text.translatable("message.togglepvp.pvp_state.enabled")
                        .styled(s -> s.withColor(Formatting.RED)).getString()
                        : Text.translatable("message.togglepvp.pvp_state.disabled")
                        .styled(s -> s.withColor(Formatting.GREEN)).getString()), false);
        return 0;
    }

    public static SuggestionProvider<ServerCommandSource> suggestedState() {
        return (context, builder) -> {
            builder.suggest(Text.translatable("command.autocomplete.togglevpvp.enable").getString());
            builder.suggest(Text.translatable("command.autocomplete.togglevpvp.disable").getString());
            return builder.buildFuture();
        };
    }
}