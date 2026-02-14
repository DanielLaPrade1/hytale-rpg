package com.danmods.xp;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.level.LevelUpEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;

import java.util.function.Consumer;

public class XPChangeHandler implements Consumer<XPChangeEvent> {

    @Override
    public void accept(XPChangeEvent event) {
        var eventPlayerRef = event.playerRef();
        if (!eventPlayerRef.isValid()) return;

        var store = eventPlayerRef.getStore();

        var playerRPGComponent = store.getComponent(eventPlayerRef, PlayerRPGComponent.getComponentType());
        if (playerRPGComponent == null) return;

        // Apply XP accordingly
        switch (event.reason()) {
            case COMMAND_ADD, ENEMY_KILL:
                int oldLevel = playerRPGComponent.getLevel();
                boolean leveledUp = playerRPGComponent.addXP(event.amount());
                if (leveledUp) LevelUpEvent.dispatch(eventPlayerRef, oldLevel, playerRPGComponent.getLevel());
                break;
            case COMMAND_SET:
                playerRPGComponent.setTotalXP(event.amount());
                break;
        }
    }
}
