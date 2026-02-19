package com.danmods.config;

import com.hypixel.hytale.codec.Codec;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class MiningConfig {

    private Map<String, Integer> ores;
    private Map<String, Integer> blocks;

    public static final BuilderCodec<MiningConfig> CODEC = BuilderCodec.builder(MiningConfig.class, MiningConfig::new)
            // Map<String,Integer> for Ores
            .append(
                    new KeyedCodec<>("Ores",
                            new ObjectMapCodec<>(
                                    Codec.INTEGER,
                                    LinkedHashMap::new,
                                    key -> key,
                                    str -> str
                            )
                    ),
                    MiningConfig::setOres,
                    MiningConfig::getOres
            ).add()
            .append(
                    new KeyedCodec<>("Blocks",
                            new ObjectMapCodec<>(
                                    Codec.INTEGER,
                                    LinkedHashMap::new,
                                    key -> key,
                                    str -> str
                            )
                    ),
                    MiningConfig::setBlocks,
                    MiningConfig::getBlocks
            ).add()
            .build();


    public MiningConfig() {}

    public Map<String, Integer> getOres() {
        return ores;
    }

    public void setOres(Map<String, Integer> ores) {
        this.ores = ores;
    }

    public Map<String, Integer> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<String, Integer> blocks) {
        this.blocks = blocks;
    }
}
