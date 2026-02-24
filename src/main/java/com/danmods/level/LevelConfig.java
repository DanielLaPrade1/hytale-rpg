package com.danmods.level;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class LevelConfig {
    private long[] levelThresholds;

    public static final BuilderCodec<LevelConfig> CODEC =
            BuilderCodec.builder(LevelConfig.class, LevelConfig::new)
            .append(
                    new KeyedCodec<>("Level_Thresholds", Codec.LONG_ARRAY),
                    (config, thresholds) -> config.levelThresholds = thresholds,
                    (config) -> config.levelThresholds
            ).add()
            .build();

    public LevelConfig() {}


    public long[] getlevelThresholds() {
        return levelThresholds;
    }

}
