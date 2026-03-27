package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEMk41VLS8;
import com.navalarmament.tileentity.usn.TEMk41VLS16;
import com.navalarmament.tileentity.usn.TEMk41VLS32;
import com.navalarmament.tileentity.usn.TEMk41VLS64;
import java.util.Arrays;
import java.util.List;

public class ItemSM2 extends ItemNavalAmmo {
    public ItemSM2() {
        super(5120, 200f, 15f, 2.0f);
        setUnlocalizedName("sm2");
        setTextureName("navalarmament:sm2");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(
            TEMk41VLS8.class, TEMk41VLS16.class,
            TEMk41VLS32.class, TEMk41VLS64.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.AIR, TargetType.SURFACE);
    }
}