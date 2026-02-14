package com.danmods.xp;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public record XPChangeEvent(
        @Nonnull Ref<EntityStore> playerRef,
        long amount,
        XPChangeReason reason
)  implements IEvent<Void> {
    public static void dispatch(Ref<EntityStore> playerRef, long amount, XPChangeReason reason) {
        // Control event bus logic
        IEventDispatcher<XPChangeEvent, XPChangeEvent> dispatcher =
                HytaleServer.get().getEventBus().dispatchFor(XPChangeEvent.class);

        // create object if it has listeners
        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new XPChangeEvent(playerRef, amount, reason));
        }
    }
}
