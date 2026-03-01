package com.danmods.tree;
import java.util.List;

public record SkillNodeData(
        String skillName,
        int skillLevel,
        int price,
        // Eventually ArrayList<Upgrades>
        List<String> upgrades
) {}
