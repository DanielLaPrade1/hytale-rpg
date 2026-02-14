package com.danmods.commands;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.xp.XPChangeEvent;
import com.danmods.xp.XPChangeReason;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class AddXPCommand extends AbstractPlayerCommand {

    private final RequiredArg<Integer> amountArg;

    public AddXPCommand() {
        super("add-xp", "Give yourself XP");
        this.amountArg = withRequiredArg("amount", "XP amount (>0)", ArgTypes.INTEGER)
                .addValidator(Validators.greaterThan(0));
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

        XPChangeEvent.dispatch(ref, amount, XPChangeReason.COMMAND_ADD);
        playerRef.sendMessage(Message.raw("+%d XP".formatted(amount)));
    }
}
