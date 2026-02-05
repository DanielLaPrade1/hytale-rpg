package com.danmods;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.events.GiveXPEvent;
import com.danmods.events.LevelUpEvent;
import com.danmods.handlers.GiveXPHandler;
import com.danmods.handlers.LevelUpHandler;
import com.danmods.systems.PlayerJoinSystem;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytaleRPGPlugin extends JavaPlugin {
    public HytaleRPGPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        var storeRegistry = getEntityStoreRegistry();
        var eventRegistry = getEventRegistry();

        // Register PlayerRPG Component
        var rpgType = storeRegistry.registerComponent(
                PlayerRPGComponent.class,
                "HytaleRPG_PlayerData",
                PlayerRPGComponent.CODEC
        );
        PlayerRPGComponent.setComponentType(rpgType);

        // Register Player Join System
        storeRegistry.registerSystem(new PlayerJoinSystem());

        //eventRegistry.register(GiveXPEvent.class, new GiveXPHandler());
        //eventRegistry.register(LevelUpEvent.class, new LevelUpHandler());

    }

}
