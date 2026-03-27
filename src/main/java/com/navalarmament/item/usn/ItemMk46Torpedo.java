package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEMk32Torpedo;
import java.util.Arrays;
import java.util.List;

public class ItemMk46Torpedo extends ItemNavalAmmo {
    public ItemMk46Torpedo() {
        super(1280, 150f, 6f, 1.0f);
        setUnlocalizedName("mk46");
        setTextureName("navalarmament:mk46");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TEMk32Torpedo.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.SUBSURFACE);
    }
}