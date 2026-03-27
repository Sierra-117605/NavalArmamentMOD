package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import java.util.Arrays;
import java.util.List;

public class ItemMk48Torpedo extends ItemNavalAmmo {
    public ItemMk48Torpedo() {
        super(2560, 250f, 8f, 1.2f);
        setUnlocalizedName("mk48");
        setTextureName("navalarmament:mk48");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TESubTorpedo.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.SUBSURFACE, TargetType.SURFACE);
    }
}