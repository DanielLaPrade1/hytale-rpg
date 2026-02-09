package com.danmods.pages;

import com.danmods.components.PlayerRPGComponent;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBinding;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

//public class XPManagerPage extends InteractiveCustomUIPage<XPManagerPage.Data> {
//
//    public static class Data {
//        public String value;
//
//        public static final BuilderCodec<Data> CODEC = BuilderCodec
//                .builder(Data.class, Data::new)
//                .append(new KeyedCodec<>(
//                        "@XPInput", Codec.STRING),
//                        (data, value) -> data.value = value,
//                        data -> data.value)
//                .add()
//                .build();
//    }
//
//    public XPManagerPage(@NonNullDecl PlayerRef playerRef) {
//        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,
//                Data.CODEC);
//    }
//
//    @Override
//    public void build(
//            @NonNullDecl Ref<EntityStore> ref,
//            @NonNullDecl UICommandBuilder uiCommandBuilder,
//            @NonNullDecl UIEventBuilder uiEventBuilder,
//            @NonNullDecl Store<EntityStore> store
//    ) {
//        uiCommandBuilder.append("Pages/XPManager.ui");
//
//        uiEventBuilder.addEventBinding(
//                CustomUIEventBindingType.Activating,
//                "#GreetButton",
//                new EventData().append("@XPInput", "#XPInput.Value")
//        );
//    }
//
//    @Override
//    public void handleDataEvent(
//            @NonNullDecl Ref<EntityStore> ref,
//            @NonNullDecl Store<EntityStore> store,
//            @NonNullDecl Data data
//    ) {
//        Player player = store.getComponent(ref, Player.getComponentType());
//        if (player == null) return;
//
//        player.sendMessage(Message.raw("You added %s XP".formatted(data.value)));
//
//        // Must call to avoid infinite update
//        sendUpdate();
//    }
//}

public class XPManagerPage extends BasicCustomUIPage {

    public XPManagerPage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction);
    }

    @Override
    public void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("Pages/XPManager.ui");
    }
}
