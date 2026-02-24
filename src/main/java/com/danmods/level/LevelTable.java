package com.danmods.level;

public class LevelTable {
    private static long[] levelThresholds;
    private static int startingLevel;

    public static void setLevelTable(LevelConfig levelConfig) {
        levelThresholds = levelConfig.getlevelThresholds();

        if (levelThresholds == null || levelThresholds.length == 0) {
            throw new IllegalStateException("Level levelThresholds cannot be empty.");
        }

        if (levelThresholds[0] != 0) {
            throw new IllegalStateException("First level threshold must be 0.");
        }

        for (int i = 1; i < levelThresholds.length; i++) {
            if (levelThresholds[i] <= levelThresholds[i - 1]) {
                throw new IllegalStateException("Level levelThresholds must be strictly increasing.");
            }
        }

        startingLevel = 1;
    }

    public static int getLevelFromXP(long totalXP) {
        if (totalXP == 0) return startingLevel;
        if (totalXP < 0) {
            throw new IllegalArgumentException(
                    "TotalXP must be >= 0, got " + totalXP
            );
        }
        for (int level = levelThresholds.length; level >= startingLevel; level--) {
            if (totalXP >= levelThresholds[level - 1]) {return level;}
        }
        return startingLevel;
    }

    public static long getTotalXPToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level == levelThresholds.length) return 0L;
        return levelThresholds[level] - levelThresholds[level - 1];
    }

    public static long getXpInCurrentLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        return totalXP - levelThresholds[level - 1];
    }

    public static long getXpToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level > levelThresholds.length) return 0L;
        return levelThresholds[level - 1] - totalXP;
    }

    // Decimal representation of progress to next level
    public static float getProgressToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level == levelThresholds.length) return 1.0f;

        long nextThreshold = levelThresholds[level];
        long currentThreshold = levelThresholds[level - 1];

        long xpCurrent = totalXP - currentThreshold;
        long xpNeeded = nextThreshold - currentThreshold;

        return (float) xpCurrent / xpNeeded;
    }

    public static int getMaxLevel() {
        return levelThresholds.length;
    }
}
