package me.braunly.togglepvp;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.RootCommandNode;
import me.braunly.togglepvp.command.PvpCommand;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegistry {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment
    ) {
        if (!environment.dedicated) return;

        TogglePvp.LOGGER.info("Registering /pvp command...");
        RootCommandNode<ServerCommandSource> rootNode = dispatcher.getRoot();
        LiteralArgumentBuilder<ServerCommandSource> pvpBuilder = CommandManager.literal("pvp");

        pvpBuilder.requires(Permissions.require(Perms.Registry.base, true))
                .executes(new PvpCommand())
                .then(CommandManager.argument("target_player", EntityArgumentType.player())
                        .requires(Permissions.require(Perms.Registry.others, 2))
                        .then(CommandManager.argument("pvp_status", StringArgumentType.word()).suggests(PvpCommand.suggestedState())
                                .executes(new PvpCommand()::setOther)));

        rootNode.addChild(pvpBuilder.build());
    }
}
