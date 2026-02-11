package com.danmods.commands;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SetXPCommand extends AbstractPlayerCommand {
    private final RequiredArg<Integer> amountArg;

    public SetXPCommand() {
        super("set-xp", "Set your XP");
        this.amountArg = withRequiredArg("amount", "Amount of XP (>0)", ArgTypes.INTEGER);
    }

    @Override
    protected void execute(
            @NonNullDecl CommandContext commandContext,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world
    ) {
        var amount = amountArg.get(commandContext);

        if (store.getComponent(ref, PlayerRPGComponent.getComponentType()) == null) {
            playerRef.sendMessage(Message.raw("NO RPG data found"));
            return;
        }

        playerRef.sendMessage(Message.raw("XP Amount set to: %d".formatted(amount)));

    }
}
