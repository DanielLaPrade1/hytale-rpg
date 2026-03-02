package com.danmods.components;

import com.danmods.level.*;
import com.danmods.skill.SkillType;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.EnumMap;
import java.util.Map;

public class PlayerRPGComponent implements Component<EntityStore> {

    private long totalXP = 0;
    private final EnumMap<SkillType, Integer> skillPoints =
            new EnumMap<>(SkillType.class);

    public PlayerRPGComponent() {
        for (SkillType skillType : SkillType.values()) {
            skillPoints.put(skillType, 0);
        }
    }

    // Component Type Holder
    private static ComponentType<EntityStore, PlayerRPGComponent> TYPE;

    public static void setComponentType(ComponentType<EntityStore, PlayerRPGComponent> type) {
        TYPE = type;
    }

    public static ComponentType<EntityStore, PlayerRPGComponent> getComponentType() {
        return TYPE;
    }

    // Component Codec
    public static final BuilderCodec<PlayerRPGComponent> CODEC =
            BuilderCodec.builder(PlayerRPGComponent.class, PlayerRPGComponent::new)
            .append(
                    new KeyedCodec<>("TotalXP", Codec.LONG),
                    (component, value) -> component.totalXP = value,
                    component -> component.totalXP
            ).add()
            .append(
                    new KeyedCodec<>(
                            "SkillPoints",
                            new ObjectMapCodec<>(
                                    Codec.INTEGER,
                                    () -> new EnumMap<>(SkillType.class),
                                    SkillType::name,
                                    SkillType::valueOf
                            )
                    ),
                    (component, value) -> {
                        // Ensure map is not empty
                        component.skillPoints.clear();
                        component.skillPoints.putAll(value);

                        for (SkillType type : SkillType.values()) {
                            component.skillPoints.putIfAbsent(type, 0);
                        }
                    },
                    component -> component.skillPoints
            )
            .add()
            .build();

    // XP
    public long getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(long XP) {
        totalXP = Math.max(0L, XP);
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
        return getLevel() > LevelTable.getMaxLevel();
    }

    public boolean addXP(long amount) {
        if (amount <= 0) return false;

        int oldLevel = getLevel();
        totalXP += amount;
        int newLevel = getLevel();

        return newLevel > oldLevel;
    }

    // Skill Points
    public int getSkillPoints(SkillType type) {
        return skillPoints.get(type);
    }

    public void addSkillPoints(SkillType type, int amount) {
        if (amount <= 0) return;
        skillPoints.merge(type, amount, Integer::sum);
    }

    public boolean spendSkillPoint(SkillType type, int price) {
        int current = skillPoints.get(type);
        if (current <= 0) return false;

        skillPoints.put(type, current - price);
        return true;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        PlayerRPGComponent copy = new PlayerRPGComponent();
        copy.totalXP = this.totalXP;
        copy.skillPoints.putAll(this.skillPoints);
        return copy;
    }

    @Override
    public String toString() {
        return "PlayerRPGComponent{level=" + getLevel() +
                ", totalXP=" + totalXP +
                ", toNext=" + getXPToNextLevel() +
                ", COMBATskillPoints=" + getSkillPoints(SkillType.COMBAT) +
                ", MININGskillPoints=" + getSkillPoints(SkillType.MINING) +
                ", DEFENSEskillPoints=" + getSkillPoints(SkillType.DEFENSE) +
                ", ENDURANCEskillPoints=" + getSkillPoints(SkillType.ENDURANCE) +"}";
    }
}
