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

public class RPGManagerPage extends InteractiveCustomUIPage<RPGManagerPage.RPGManagerData>{
    private String selectedNav;

    public static class RPGManagerData {
        public String nav;

        public static final BuilderCodec<RPGManagerData> CODEC =
                BuilderCodec.builder(RPGManagerData.class, RPGManagerData::new)
                        .append(
                                new KeyedCodec<>("Nav", Codec.STRING),
                                (data, nav) -> data.nav = nav,
                                (data) -> data.nav
                        ).add()
                        .build();
    }

    public RPGManagerPage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,
                RPGManagerData.CODEC);
        this.selectedNav = "#CombatNavButton";
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
                "#CombatNavButton",
                new EventData().append("Nav", "#CombatNavButton")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#MiningNavButton",
                new EventData().append("Nav", "#MiningNavButton")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#DefenseNavButton",
                new EventData().append("Nav", "#DefenseNavButton")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#EnduranceNavButton",
                new EventData().append("Nav", "#EnduranceNavButton")
        );

        setNav(ref, store, selectedNav);
    }

    @Override
    public void handleDataEvent(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl RPGManagerData data
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());

        setNav(ref, store, data.nav);
    }

    private void setNav(Ref<EntityStore> ref, Store<EntityStore> store, String nav) {
        UICommandBuilder uicommandBuilder = new UICommandBuilder();
        UIEventBuilder uieventBuilder = new UIEventBuilder();

        uicommandBuilder.clear("#SkillTreeContainer");

        // Set Nav Button Color
        if (!nav.equals(selectedNav)) {
            uicommandBuilder.set("%s.Background".formatted(selectedNav), "#090e14");
            selectedNav = nav;
        }
        uicommandBuilder.set("%s.Background".formatted(nav), "#004aad");

        uicommandBuilder.append("#SkillTreeContainer", "Pages/SkillTree.ui");

        uicommandBuilder.set("#SkillLabel.Text", nav);

        sendUpdate(uicommandBuilder, uieventBuilder, false);
    }

}