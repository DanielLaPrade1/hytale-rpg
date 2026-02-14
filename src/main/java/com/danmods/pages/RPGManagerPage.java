package com.danmods.pages;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class RPGManagerPage extends InteractiveCustomUIPage<RPGManagerPage.Data> {

    public static class Data {
        public String value;

        public static final BuilderCodec<Data> CODEC =
                BuilderCodec.builder(Data.class, Data::new).build();
    }

    public RPGManagerPage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,
                Data.CODEC);
    }

    @Override
    public void build(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl UICommandBuilder uiCommandBuilder,
            @NonNullDecl UIEventBuilder uiEventBuilder,
            @NonNullDecl Store<EntityStore> store
    ) {
        uiCommandBuilder.append("Pages/XPManager.ui");

        var rpgComponent = store.getComponent(ref, PlayerRPGComponent.getComponentType());
        if (rpgComponent == null) return;
        // Set Values of UI Elements
        uiCommandBuilder.set("#XPDisplay.Text", "%d / %d".formatted(
                rpgComponent.getXpInCurrentLevel(),
                rpgComponent.getTotalXPToNextLevel()));
        uiCommandBuilder.set("#LevelDisplay.Text", "(Level %d)".formatted(
                rpgComponent.getLevel()));
        uiCommandBuilder.set("#XPBar.Value", rpgComponent.getProgress());

    }

    @Override
    public void handleDataEvent(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Data data
    ) {
    }
}