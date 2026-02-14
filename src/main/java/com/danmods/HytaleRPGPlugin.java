package com.danmods;

import com.danmods.commands.RPGCommandCollection;
import com.danmods.components.PlayerRPGComponent;
import com.danmods.systems.mining.MiningXPGainSystem;
import com.danmods.xp.XPChangeEvent;
import com.danmods.level.LevelUpEvent;
import com.danmods.xp.XPChangeHandler;
import com.danmods.level.LevelUpHandler;
import com.danmods.systems.PlayerJoinSystem;
import com.danmods.systems.combat.EnemyXPGainSystem;
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

        // Component Registries
        var rpgType = storeRegistry.registerComponent(
                PlayerRPGComponent.class,
                "HytaleRPG_PlayerData",
                PlayerRPGComponent.CODEC
        );
        PlayerRPGComponent.setComponentType(rpgType);

        // System Registries
        storeRegistry.registerSystem(new PlayerJoinSystem());
        storeRegistry.registerSystem(new EnemyXPGainSystem());
        storeRegistry.registerSystem(new MiningXPGainSystem());

        // Event and Handler Registries
        var eventRegistry = getEventRegistry();

        eventRegistry.register(XPChangeEvent.class, new XPChangeHandler());
        eventRegistry.register(LevelUpEvent.class, new LevelUpHandler());

        // Command Registries
        var commandRegistry = getCommandRegistry();

        commandRegistry.registerCommand(new RPGCommandCollection());


    }
}
