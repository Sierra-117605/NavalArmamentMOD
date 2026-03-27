package com.navalarmament.item.base;

import com.navalarmament.init.NavalCreativeTabs;
import net.minecraft.item.Item;

public abstract class ItemNavalAmmo extends Item implements INavalAmmo {

    protected int range;
    protected float damage;
    protected float explosionRadius;
    protected float speed;

    public ItemNavalAmmo(int range, float damage, float explosionRadius, float speed) {
        this.range           = range;
        this.damage          = damage;
        this.explosionRadius = explosionRadius;
        this.speed           = speed;
        setMaxStackSize(64);
        setMaxDamage(0);
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override public int getRange()              { return range; }
    @Override public float getDamage()           { return damage; }
    @Override public float getExplosionRadius()  { return explosionRadius; }
    @Override public float getSpeed()            { return speed; }
}