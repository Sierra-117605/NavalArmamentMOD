package com.navalarmament.item.base;

import com.navalarmament.init.NavalCreativeTabs;
import com.navalarmament.system.TargetType;
import net.minecraft.item.Item;
import java.util.List;

public abstract class ItemNavalAmmo extends Item implements INavalAmmo {

    protected final int   range;
    protected final float damage;
    protected final float speed;
    protected final float explosionRadius;

    public ItemNavalAmmo(int range, float damage, float speed, float explosionRadius) {
        this.range           = range;
        this.damage          = damage;
        this.speed           = speed;
        this.explosionRadius = explosionRadius;
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override public int   getRange()           { return range; }
    @Override public float getDamage()          { return damage; }
    @Override public float getSpeed()           { return speed; }
    @Override public float getExplosionRadius() { return explosionRadius; }

    @Override public abstract List<Class<?>>   getCompatibleWeapons();
    @Override public abstract List<TargetType> getEffectiveTargetTypes();
}