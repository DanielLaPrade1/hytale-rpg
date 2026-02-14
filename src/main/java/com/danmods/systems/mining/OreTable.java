package com.danmods.systems.mining;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class OreTable {
    private static final Map<String, Integer> ORE_TABLE = Map.of(
            "Ore_Adamantite", 95,
            "Ore_Cobalt", 70,
            "Ore_Gold", 55,
            "Ore_Silver", 50,
            "Ore_Thorium", 45,
            "Ore_Iron", 25,
            "Ore_Copper", 15
    );

    public static Integer getXPFromOre(String oreName) {
        return ORE_TABLE.get(oreName);
    }

}
