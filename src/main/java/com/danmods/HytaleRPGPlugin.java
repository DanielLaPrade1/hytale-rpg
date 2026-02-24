package com.danmods;

import com.danmods.commands.RPGCommandCollection;
import com.danmods.components.PlayerRPGComponent;
import com.danmods.level.LevelConfig;
import com.danmods.level.LevelTable;
import com.danmods.systems.mining.MiningConfig;
import com.danmods.systems.mining.MiningXPGainSystem;
import com.danmods.xp.XPChangeEvent;
import com.danmods.level.LevelUpEvent;
import com.danmods.xp.XPChangeHandler;
import com.danmods.level.LevelUpHandler;
import com.danmods.systems.PlayerJoinSystem;
import com.danmods.systems.combat.EnemyXPGainSystem;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytaleRPGPlugin extends JavaPlugin {
    private Config<MiningConfig> miningConfig;
    private Config<LevelConfig> levelConfig;
    private MiningXPGainSystem miningSystem;

    public HytaleRPGPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        miningConfig = withConfig("miningxp", MiningConfig.CODEC);
        levelConfig = withConfig("playerrpg", LevelConfig.CODEC);
    }

    @Override
    protected void setup() {
        var storeRegistry = getEntityStoreRegistry();

        // Config Loader (JSON Files)
        miningConfig.save();
        levelConfig.save();

        // Component Registries
        var rpgType = storeRegistry.registerComponent(
                PlayerRPGComponent.class,
                "HytaleRPG_PlayerData",
                PlayerRPGComponent.CODEC
        );
        PlayerRPGComponent.setComponentType(rpgType);

        // System Registries
        storeRegistry.registerSystem(new EnemyXPGainSystem());

        // Event and Handler Registries
        var eventRegistry = getEventRegistry();

        eventRegistry.register(XPChangeEvent.class, new XPChangeHandler());
        eventRegistry.register(LevelUpEvent.class, new LevelUpHandler());

        // Command Registries
        var commandRegistry = getCommandRegistry();

        commandRegistry.registerCommand(new RPGCommandCollection());
    }

    @Override
    public void start() {
        var storeRegistry = getEntityStoreRegistry();

        MiningConfig mc = miningConfig.get();
        storeRegistry.registerSystem(new MiningXPGainSystem(mc));

        LevelConfig lvlConfig = levelConfig.get();
        LevelTable.setLevelTable(lvlConfig);
    }
}
