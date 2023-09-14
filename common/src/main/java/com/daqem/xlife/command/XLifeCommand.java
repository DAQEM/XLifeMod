package com.daqem.xlife.command;

import com.daqem.xlife.XLife;
import com.daqem.xlife.player.XLifeServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;

public class XLifeCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("xlife")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.literal("lives")
                                .then(Commands.argument("target_player", EntityArgument.player())
                                        .then(Commands.argument("lives", IntegerArgumentType.integer(1, 10))
                                                .executes(context -> {
                                                    ServerPlayer player = EntityArgument.getPlayer(context, "target_player");
                                                    int lives = context.getArgument("lives", Integer.class);
                                                    if (player instanceof XLifeServerPlayer serverPlayer) {
                                                        serverPlayer.x_life_mod$setLives(lives);
                                                    }
                                                    context.getSource().sendSuccess(XLife.translate("commands.xlife.set.lives.success", player.getDisplayName(), lives), true);
                                                    return 1;
                                                })))))

        );
    }
}
