package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEHarpoonLauncher;
import java.util.Arrays;
import java.util.List;

public class ItemHarpoon extends ItemNavalAmmo {
    public ItemHarpoon() {
        super(10240, 300f, 10f, 1.8f);
        setUnlocalizedName("harpoon");
        setTextureName("navalarmament:harpoon");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TEHarpoonLauncher.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.SURFACE);
    }
}