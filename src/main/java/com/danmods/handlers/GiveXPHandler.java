package com.danmods.handlers;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.events.GiveXPEvent;
import com.danmods.events.LevelUpEvent;

import java.util.function.Consumer;

public class GiveXPHandler implements Consumer<GiveXPEvent> {

    @Override
    public void accept(GiveXPEvent event) {
        var playerRef = event.playerRef();
        if (!playerRef.isValid()) return;

        var store = playerRef.getStore();

        var playerRPGComponent = store.getComponent(playerRef, PlayerRPGComponent.getComponentType());
        if (playerRPGComponent == null) return;

        // Add XP and check for level up

        int oldLevel = playerRPGComponent.getLevel();
        boolean leveledUp = playerRPGComponent.addXP(event.amount());

        if (leveledUp) LevelUpEvent.dispatch(playerRef, oldLevel, playerRPGComponent.getLevel());
    }
}
