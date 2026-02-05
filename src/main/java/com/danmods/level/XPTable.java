package com.danmods.level;

public class XPTable {
    private static final long[] LEVEL_THRESHOLDS = {
            0,
            100,
            300,
            700,
            1200,
            1900,
            3000,
            4300,
            5900,
            8000,
            11000,
            14500
    };

    public static final int MAX_LEVEL = LEVEL_THRESHOLDS.length;
    public static final int STARTING_LEVEL = 1;

    private XPTable() {}

    public static int getLevelFromXP(long totalXP) {
        if (totalXP == 0) return STARTING_LEVEL;
        if (totalXP < 0) {
            throw new IllegalArgumentException(
                    "TotalXP must be >= 0, got " + totalXP
            );
        }
        for (int level = MAX_LEVEL; level >= STARTING_LEVEL; level--) {
            if (totalXP > LEVEL_THRESHOLDS[level - 1]) {return level;}
        }
        return STARTING_LEVEL;
    }

    public static long getXPFromLevel(int level) {
        if (level <= 0) {
            throw new IllegalArgumentException(
                    "Level must be >= 1, got " + level
            );
        }
        if (level >= MAX_LEVEL) return MAX_LEVEL;
        return LEVEL_THRESHOLDS[level];
    }

    public static long getXpInCurrentLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        return totalXP - LEVEL_THRESHOLDS[level];
    }

    public static long getXpToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level > MAX_LEVEL) return 0L;
        return LEVEL_THRESHOLDS[level] - totalXP;
    }

    // Decimal representation of progress to next level
    public static float getProgressToNextLevel(long totalXP) {
        int level = getLevelFromXP(totalXP);
        if (level == MAX_LEVEL) return 1.0f;

        long nextThreshold = LEVEL_THRESHOLDS[level];
        long currentThreshold = LEVEL_THRESHOLDS[level - 1];

        long xpCurrent = totalXP - currentThreshold;
        long xpNeeded = nextThreshold - currentThreshold;

        return (float) xpCurrent / xpNeeded;
    }

}
