package com.danmods.level;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.function.Consumer;

public class LevelUpHandler implements Consumer<LevelUpEvent> {
    @Override
    public void accept(LevelUpEvent event) {
        // This is the entity ref
        var eventPlayerRef = event.playerRef();
        if (!eventPlayerRef.isValid()) return;

        var store = eventPlayerRef.getStore();
        // This is the player wrapper
        var playerRef = store.getComponent(eventPlayerRef, PlayerRef.getComponentType());
        if (playerRef == null) return;

        String message = event.levelsGained() == 1 ?
            "LEVEL UP! You are now level %d".formatted(event.newLevel()) :
            "LEVEL UP! +%d levels! You are now level %d".formatted(event.levelsGained(), event.newLevel());

        playerRef.sendMessage(Message.raw(message));
    }
}
