package com.navalarmament.system;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.item.EntityTNTPrimed;
import java.util.ArrayList;
import java.util.List;

public class ThreatEvaluator {

    public static List<TargetData> evaluate(List<Entity> entities,
                                             double ox, double oy, double oz) {
        List<TargetData> threats = new ArrayList<TargetData>();
        for (Entity e : entities) {
            if (e == null || e.isDead) continue;
            if (e instanceof EntityMob || e instanceof EntityTNTPrimed) {
                threats.add(new TargetData(e, ox, oy, oz));
            }
        }
        return threats;
    }
}