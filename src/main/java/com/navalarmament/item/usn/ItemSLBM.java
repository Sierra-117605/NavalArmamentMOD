package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TESubVLSSSBN8;
import com.navalarmament.tileentity.usn.TESubVLSSSBN16;
import java.util.Arrays;
import java.util.List;

public class ItemSLBM extends ItemNavalAmmo {
    public ItemSLBM() {
        super(20480, 1000f, 30f, 2.5f);
        setUnlocalizedName("slbm");
        setTextureName("navalarmament:slbm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(TESubVLSSSBN8.class, TESubVLSSSBN16.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.SURFACE);
    }
}