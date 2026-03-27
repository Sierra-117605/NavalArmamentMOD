package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetType;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import com.navalarmament.tileentity.usn.TESubVLSSSGN8;
import com.navalarmament.tileentity.usn.TESubVLSSSGN16;
import java.util.Arrays;
import java.util.List;

public class ItemSubSSM extends ItemNavalAmmo {
    public ItemSubSSM() {
        super(20480, 250f, 12f, 2.0f);
        setUnlocalizedName("sub_ssm");
        setTextureName("navalarmament:sub_ssm");
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