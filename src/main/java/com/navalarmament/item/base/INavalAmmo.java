package com.navalarmament.item.base;

import com.navalarmament.system.TargetType;
import java.util.List;

public interface INavalAmmo {
    List<Class<?>> getCompatibleWeapons();
    List<TargetType> getEffectiveTargetTypes();
    int getRange();
    float getDamage();
    float getSpeed();
    float getExplosionRadius();
}