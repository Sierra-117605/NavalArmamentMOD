package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TESubVLSSSBN8;
import com.navalarmament.tileentity.usn.TESubVLSSSBN16;
import java.util.ArrayList;
import java.util.List;

public class ItemSLBM extends ItemNavalAmmo {
    public ItemSLBM() {
        super(20480, 1000f, 30f, 2.5f);
        setUnlocalizedName("slbm");
        setTextureName("navalarmament:slbm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TESubVLSSSBN8.class);
        list.add(TESubVLSSSBN16.class);
        return list;
    }
}