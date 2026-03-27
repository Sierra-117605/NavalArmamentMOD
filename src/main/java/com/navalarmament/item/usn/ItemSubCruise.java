package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import com.navalarmament.tileentity.usn.TESubVLSSSGN8;
import com.navalarmament.tileentity.usn.TESubVLSSSGN16;
import java.util.Arrays;
import java.util.List;

public class ItemSubCruise extends ItemNavalAmmo {
    public ItemSubCruise() {
        super(20480, 400f, 20f, 1.5f);
        setUnlocalizedName("sub_cruise");
        setTextureName("navalarmament:sub_cruise");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        return Arrays.<Class<?>>asList(
            TESubTorpedo.class, TESubVLSSSGN8.class, TESubVLSSSGN16.class);
    }
    @Override
    public List<TargetType> getEffectiveTargetTypes() {
        return Arrays.asList(TargetType.SURFACE);
    }
}