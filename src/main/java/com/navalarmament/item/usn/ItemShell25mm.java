package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEMk38Gun;
import java.util.Arrays;
import java.util.List;

public class ItemShell25mm extends ItemNavalAmmo {
    public ItemShell25mm() {
        super(640, 30f, 12f, 1.5f);
        setUnlocalizedName("shell_25mm");
        setMaxStackSize(64); // 砲弾はマガジン給弾
        setTextureName("navalarmament:shell_25mm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TEMk38Gun.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.AIR, TargetType.SURFACE);
    }
}