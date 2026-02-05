package com.danmods;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytaleRPGPlugin extends JavaPlugin {
    public HytaleRPGPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        var registry = getEntityStoreRegistry();

        // Register PlayerRPG Component
        var rpgType = registry.registerComponent(
                PlayerRPGComponent.class,
                "HytaleRPG_PlayerData",
                PlayerRPGComponent.CODEC
        );
        PlayerRPGComponent.setComponentType(rpgType);
    }

}
