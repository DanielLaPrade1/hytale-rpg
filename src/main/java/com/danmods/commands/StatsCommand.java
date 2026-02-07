package com.danmods.commands;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class StatsCommand extends AbstractPlayerCommand {

    public StatsCommand() {
        super("stats", "Check player statistics");
    }

    @Override
    protected void execute(
            @NonNullDecl CommandContext commandContext,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world
    ) {
        var rpgComponent = store.getComponent(ref, PlayerRPGComponent.getComponentType());
        if (rpgComponent == null) {
            playerRef.sendMessage(Message.raw("NO RPG data found"));
            return;
        }

        int rpgLevel = rpgComponent.getLevel();

        // Display Stats
        playerRef.sendMessage(Message.raw("RPG STATS"));
        playerRef.sendMessage(Message.raw("==== RPG STATS ===="));
        playerRef.sendMessage(Message.raw("Level %d".formatted(rpgLevel)));
        playerRef.sendMessage(Message.raw("Total XP: %d".formatted(rpgComponent.getTotalXP())));
        playerRef.sendMessage(Message.raw(rpgComponent.isMaxLevel() ?
                "You've Reached Max Level" :
                "XP To Next Level (%d): %d".formatted(rpgLevel + 1, rpgComponent.getXPToNextLevel())));
    }
}