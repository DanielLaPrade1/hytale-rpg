package com.danmods.systems;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class PlayerJoinSystem extends RefSystem<EntityStore> {


    @Override
    public void onEntityAdded(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl AddReason addReason,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        // Only run on LOAD (Player Joining), ignore else
        if (addReason != AddReason.LOAD) return;

        var playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (playerRef == null) return;

        // Will be null if players first time joining
        var rpgType = PlayerRPGComponent.getComponentType();
        var rpgRef = store.getComponent(ref, rpgType);

        if (rpgRef != null) {
            playerRef.sendMessage(
                    Message.raw("Welcome Back %s! Level %d (%d XP)"
                            .formatted(playerRef.getUsername(),
                                    rpgRef.getLevel(),
                                    rpgRef.getTotalXP())
                    )
            );
        } else {
            commandBuffer.addComponent(ref, rpgType, new PlayerRPGComponent());
            playerRef.sendMessage(Message.raw(("Welcome %s! Your Journey begins at Level 1." +
                    " Level up by killing enemies and become the strongest in the land!")
                    .formatted(playerRef.getUsername())
            ));
        }
    }

    // Nothing to clean up, Component persists automatically
    @Override
    public void onEntityRemove(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl RemoveReason removeReason,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {}

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        // Only care about players (only entities with the PlayerRef component)
        return Archetype.of(PlayerRef.getComponentType());
    }
}
