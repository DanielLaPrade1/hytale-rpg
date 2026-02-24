package com.danmods.level;

public class LevelTable {
    public static long[] LEVEL_THRESHOLDS;
    public static int STARTING_LEVEL;
    public static int MAX_LEVEL;

    public static void setLevelTable(LevelConfig levelConfig) {
        LEVEL_THRESHOLDS = levelConfig.getlevelThresholds();
        STARTING_LEVEL = 1;
        MAX_LEVEL = LEVEL_THRESHOLDS.length;
    }

    public static int getLevelFromXP(long totalXP) {
        if (totalXP == 0) return STARTING_LEVEL;
        if (totalXP < 0) {
            throw new IllegalArgumentException(
                    "TotalXP must be >= 0, got " + totalXP
            );
        }
        for (int level = LEVEL_THRESHOLDS.length; level >= STARTING_LEVEL; level--) {
            if (totalXP >= LEVEL_THRESHOLDS[level - 1]) {return level;}
        }
        return STARTING_LEVEL;
    }

    public static long getTotalXPToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level == LEVEL_THRESHOLDS.length) return 0L;
        return LEVEL_THRESHOLDS[level] - LEVEL_THRESHOLDS[level - 1];
    }

    public static long getXpInCurrentLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        return totalXP - LEVEL_THRESHOLDS[level - 1];
    }

    public static long getXpToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level > LEVEL_THRESHOLDS.length) return 0L;
        return LEVEL_THRESHOLDS[level - 1] - totalXP;
    }

    // Decimal representation of progress to next level
    public static float getProgressToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level == LEVEL_THRESHOLDS.length) return 1.0f;

        long nextThreshold = LEVEL_THRESHOLDS[level];
        long currentThreshold = LEVEL_THRESHOLDS[level - 1];

        long xpCurrent = totalXP - currentThreshold;
        long xpNeeded = nextThreshold - currentThreshold;

        return (float) xpCurrent / xpNeeded;
    }
}
