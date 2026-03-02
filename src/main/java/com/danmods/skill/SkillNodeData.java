package com.danmods.skill;
import java.util.List;

public record SkillNodeData(
        String id,
        String skillName,
        String skillDescription,
        int price,
        // Eventually ArrayList<Upgrades>
        List<String> upgrades
) {

}
