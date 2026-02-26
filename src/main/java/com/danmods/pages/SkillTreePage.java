package com.danmods.pages;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
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

public class SkillTreePage extends InteractiveCustomUIPage<SkillTreePage.SkillTreeData> {

    public static class SkillTreeData {
        public String pageName;

        public static BuilderCodec<SkillTreeData> CODEC =
                BuilderCodec.builder(SkillTreeData.class, SkillTreeData::new)
                        .append(
                                new KeyedCodec<>("Page Name", Codec.STRING),
                                (data, value) -> data.pageName = value,
                                data -> data.pageName
                        ).add()
                        .build();
    }

    public SkillTreePage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,
                SkillTreeData.CODEC);
    }

    @Override
    public void build(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl UICommandBuilder uiCommandBuilder,
            @NonNullDecl UIEventBuilder uiEventBuilder,
            @NonNullDecl Store<EntityStore> store
    ) {
        uiCommandBuilder.append("Pages/SkillTree.ui");
    }
}
