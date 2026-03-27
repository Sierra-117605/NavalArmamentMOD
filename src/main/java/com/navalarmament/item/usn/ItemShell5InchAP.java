package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEMk45Gun;
import java.util.Arrays;
import java.util.List;

public class ItemShell5InchAP extends ItemNavalAmmo {
    public ItemShell5InchAP() {
        super(1280, 120f, 8f, 1.2f);
        setUnlocalizedName("shell_5inch_ap");
        setTextureName("navalarmament:shell_5inch_ap");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TEMk45Gun.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.AIR, TargetType.SURFACE);
    }
}