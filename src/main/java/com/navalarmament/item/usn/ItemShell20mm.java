package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TEPhalanxCIWS;
import java.util.Arrays;
import java.util.List;

public class ItemShell20mm extends ItemNavalAmmo {
    public ItemShell20mm() {
        super(320, 20f, 15f, 2.0f);
        setUnlocalizedName("shell_20mm");
        setMaxStackSize(64); // 砲弾はマガジン給弾
        setTextureName("navalarmament:shell_20mm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TEPhalanxCIWS.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.AIR);
    }
}