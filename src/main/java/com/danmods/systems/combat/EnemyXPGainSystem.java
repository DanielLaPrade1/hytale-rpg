package com.danmods.systems.combat;

import com.danmods.components.PlayerRPGComponent;
import com.danmods.xp.XPChangeEvent;
import com.danmods.xp.XPChangeReason;
import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;


public class EnemyXPGainSystem extends DeathSystems.OnDeathSystem {
    public static final long MIN_XP_FROM_KILL = 33;
    public static final long MAX_XP_FROM_KILL = 52;

    @Override
    public void onComponentAdded(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl DeathComponent deathComponent,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {

       Damage deathInfo = deathComponent.getDeathInfo();

       // Check for player kill

       if (deathInfo == null) return;
       if (!(deathInfo.getSource() instanceof Damage.EntitySource source)) return;

       var killerRef = source.getRef();
       if (!killerRef.isValid()) return;

       var killer = store.getComponent(killerRef, PlayerRef.getComponentType());
       if (killer == null) return;

       // Killer is player, get and update RPG component

       var playerRPGComponent = store.getComponent(killerRef, PlayerRPGComponent.getComponentType());
       if (playerRPGComponent == null) return;

       long xpAwarded = getXPFromRange();

       XPChangeEvent.dispatch(killerRef, xpAwarded, XPChangeReason.ENEMY_KILL);

       var enemyNameRef = store.getComponent(ref, DisplayNameComponent.getComponentType());
       if (enemyNameRef == null) return;

       NotificationUtil.sendNotification(
                killer.getPacketHandler(),
                Message.raw("+%d XP".formatted(xpAwarded)).color("#FFD700"),
                enemyNameRef.getDisplayName().color("#FFD700")
        );
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.of(DeathComponent.getComponentType());
    }

    // Return random XP amount within range
    private long getXPFromRange() {
        return (long) (Math.random()
                * (EnemyXPGainSystem.MAX_XP_FROM_KILL - EnemyXPGainSystem.MIN_XP_FROM_KILL)
                + EnemyXPGainSystem.MIN_XP_FROM_KILL);
    }
}
