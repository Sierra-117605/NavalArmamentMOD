package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import com.navalarmament.tileentity.usn.TESubVLSSSGN8;
import com.navalarmament.tileentity.usn.TESubVLSSSGN16;
import java.util.ArrayList;
import java.util.List;

public class ItemSubCruise extends ItemNavalAmmo {
    public ItemSubCruise() {
        super(20480, 400f, 20f, 1.5f);
        setUnlocalizedName("sub_cruise");
        setTextureName("navalarmament:sub_cruise");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TESubTorpedo.class);
        list.add(TESubVLSSSGN8.class);
        list.add(TESubVLSSSGN16.class);
        return list;
    }
}