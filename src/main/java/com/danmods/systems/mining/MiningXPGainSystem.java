package com.danmods.systems.mining;

import com.danmods.xp.XPChangeEvent;
import com.danmods.xp.XPChangeReason;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class MiningXPGainSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
    public MiningXPGainSystem() {
        super(BreakBlockEvent.class);
    }

    @Override
    public void handle(
            int id,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl BreakBlockEvent event
    ) {
        var ref = archetypeChunk.getReferenceTo(id);

        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (playerRef == null) return;

        // Pickaxe
        var itemInHand = event.getItemInHand();
        if (itemInHand == null) return;
        // Mined Block
        var blockItem = event.getBlockType().getItem();
        if (blockItem == null) return;

        // Check if block is ore
        var blockId = blockItem.getId();
        if (blockId.startsWith("Ore_")) {
            // Cut off ore name at second '_' index
            int undIndex1 = blockId.indexOf('_');
            int undIndex2 = blockId.indexOf('_', undIndex1 + 1);
            String oreName = blockId.substring(0, undIndex2);

            int xpAwarded = OreTable.getXPFromOre(oreName);
            XPChangeEvent.dispatch(ref, xpAwarded, XPChangeReason.MINING);

            var icon = new ItemStack(blockId, 1).toPacket();
            NotificationUtil.sendNotification(
                    playerRef.getPacketHandler(),
                    Message.raw("+%d XP".formatted(xpAwarded)).color("#FFD700"),
                    Message.raw("").color("#FFD700"),
                    icon
            );
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
