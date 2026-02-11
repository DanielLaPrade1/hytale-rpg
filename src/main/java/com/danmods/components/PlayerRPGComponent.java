package com.danmods.components;

import com.danmods.level.LevelTable;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class PlayerRPGComponent implements Component<EntityStore> {

    // Component Type Holder

    private static ComponentType<EntityStore, PlayerRPGComponent> TYPE;

    public static void setComponentType(ComponentType<EntityStore, PlayerRPGComponent> type) {
        TYPE = type;
    }

    public static ComponentType<EntityStore, PlayerRPGComponent> getComponentType() {
        return TYPE;
    }

    // Component Codec

    public static final BuilderCodec<PlayerRPGComponent> CODEC = BuilderCodec
            .builder(PlayerRPGComponent.class, PlayerRPGComponent::new)
            .append(
                    new KeyedCodec<>("TotalXP", Codec.LONG),
                    (component, value) -> component.totalXP = value,
                    component -> component.totalXP
            ).add()
            .build();

    // Component Constructors and Logic

    private long totalXP = 0;

    public PlayerRPGComponent() {}

    public PlayerRPGComponent(long XP) {
        this.totalXP = Math.max(0L, XP);
    }

    public long getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(long XP) {
        this.totalXP = Math.max(0L, XP);
    }

    public int getLevel() {
        return LevelTable.getLevelFromXP(totalXP);
    }

    public long getTotalXPToNextLevel() { return LevelTable.getTotalXPToNextLevel(totalXP);}

    public long getXpInCurrentLevel() {
        return LevelTable.getXpInCurrentLevel(totalXP);
    }

    public long getXPToNextLevel() {
        return LevelTable.getXpToNextLevel(totalXP);
    }

    public float getProgress() {
        return LevelTable.getProgressToNextLevel(totalXP);
    }

    public boolean isMaxLevel() {
        return getLevel() > LevelTable.MAX_LEVEL;
    }

    public boolean addXP(long amount) {
        if (amount <= 0) return false;

        int oldLevel = getLevel();
        totalXP += amount;
        int newLevel = getLevel();

        return newLevel > oldLevel;

    }

    // Overwritten Component Methods

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new PlayerRPGComponent(this.totalXP);
    }

    @Override
    public String toString() {
        return "PlayerRPGComponent{level=" + getLevel() +
                ", totalXP=" + totalXP +
                ", toNext=" + getXPToNextLevel() + "}";
    }
}
