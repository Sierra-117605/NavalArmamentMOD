package com.navalarmament.item.base;

import java.util.List;

public interface INavalAmmo {
    int getRange();
    float getDamage();
    float getExplosionRadius();
    float getSpeed();
    List<Class<?>> getCompatibleWeapons();
}