package com.danmods.pages;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class RPGManagerPage extends InteractiveCustomUIPage<RPGManagerPage.RPGManagerData> {

    public static class RPGManagerData {
        public String action;

        public static final BuilderCodec<RPGManagerData> CODEC =
                BuilderCodec.builder(RPGManagerData.class, RPGManagerData::new)
                        .append(
                                new KeyedCodec<>("Action", Codec.STRING),
                                (data, action) -> data.action = action,
                                (data) -> data.action
                        ).add()
                        .build();
    }

    public RPGManagerPage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,
                RPGManagerData.CODEC);
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

        // Bind Action Buttons
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#CombatButton",
                new EventData().append("Action", "Combat")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#MiningButton",
                new EventData().append("Action", "Mining")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#DefenseButton",
                new EventData().append("Action", "Defense")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#EnduranceButton",
                new EventData().append("Action", "Endurance")
        );
    }

    @Override
    public void handleDataEvent(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl RPGManagerData data
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());

        changeSkillTree(ref, store, data.action);
    }

    private void changeSkillTree(Ref<EntityStore> ref, Store<EntityStore> store, String page) {
        UICommandBuilder uicommandBuilder = new UICommandBuilder();
        UIEventBuilder uieventBuilder = new UIEventBuilder();

        uicommandBuilder.clear("#SkillTreeContainer");

        String buttonId = "";

        switch (page) {
            case "Combat":
                buttonId = "#CombatButton";
                break;
            case "Mining":
                buttonId = "#MiningButton";
                break;
            case "Defense":
                buttonId = "#DefenseButton";
                break;
            case "Endurance":
                buttonId = "#EnduranceButton";
                break;
            default:
                break;
        }
        //uicommandBuilder.set("%s.Style.Background".formatted(buttonId), "ff0000");

        uicommandBuilder.append("#SkillTreeContainer", "Pages/SkillTree.ui");

        uicommandBuilder.set("#SkillLabel.Text", page);

        sendUpdate(uicommandBuilder, uieventBuilder, false);
    }

}