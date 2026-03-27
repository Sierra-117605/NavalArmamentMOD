package com.navalarmament.system;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public class ThreatEvaluator {

    public static int evaluate(Entity e, TargetData td) {
        if (e instanceof EntityPlayer) return 2; // RED
        if (e instanceof EntityMob)   return 1; // YELLOW
        return 0; // GREEN
    }

    public static int calcThreatLevel(java.util.List<TargetData> targets) {
        int max = 0;
        for (TargetData td : targets) {
            if (td.threatLevel > max) max = td.threatLevel;
        }
        return max;
    }
}