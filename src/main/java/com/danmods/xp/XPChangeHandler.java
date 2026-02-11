package com.danmods.xp;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.level.LevelUpEvent;

import java.util.function.Consumer;

public class XPChangeHandler implements Consumer<XPChangeEvent> {

    @Override
    public void accept(XPChangeEvent event) {
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
