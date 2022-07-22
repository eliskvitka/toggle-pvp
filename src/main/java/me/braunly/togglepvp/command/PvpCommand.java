package me.braunly.togglepvp.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.braunly.togglepvp.PvpAbility;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.concurrent.CompletableFuture;

public class PvpCommand implements Command<ServerCommandSource> {

    public PvpCommand() {

    }
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // TODO: add cooldown check
        return setStatus(context.getSource().getPlayer(), !PvpAbility.get(context.getSource().getPlayer()));
    }

    public int setOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity sender = context.getSource().getPlayer();
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, "target_player");
        boolean pvpStatus = StringArgumentType.getString(context, "pvp_status").equalsIgnoreCase("on");

        sender.sendMessage(Text.literal("")
                .append("PVP status for ")
                .append(Text.literal(targetPlayer.getDisplayName().getString()).styled(s -> s.withColor(Formatting.AQUA)))
                .append(" is ")
                .append(pvpStatus ? Text.literal("ENABLED").styled(s -> s.withColor(Formatting.RED)) : Text.literal("DISABLED").styled(s -> s.withColor(Formatting.GREEN))), false);
        return setStatus(targetPlayer, pvpStatus);
    }

    public int setStatus(ServerPlayerEntity player, boolean pvpState) throws CommandSyntaxException {
        PvpAbility.set(player, pvpState);

        boolean pvpStatus = PvpAbility.get(player);
        player.sendMessage(Text.literal("")
                .append(Text.literal("PVP status is - ").styled(s -> s.withColor(Formatting.WHITE)))
                .append(pvpStatus ? Text.literal("ENABLED").styled(s -> s.withColor(Formatting.RED)) : Text.literal("DISABLED").styled(s -> s.withColor(Formatting.GREEN))), false);
        return 0;
    }

    public static SuggestionProvider<ServerCommandSource> suggestedState() {
        return new SuggestionProvider<ServerCommandSource>() {
            @Override
            public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
                builder.suggest("on");
                builder.suggest("off");
                return builder.buildFuture();
            }
        };
    }
}