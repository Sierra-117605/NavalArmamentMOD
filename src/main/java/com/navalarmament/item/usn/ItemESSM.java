package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk41VLS;
import java.util.ArrayList;
import java.util.List;

public class ItemESSM extends ItemNavalAmmo {
    public ItemESSM() {
        super(2560, 120f, 6f, 3.5f);
        setUnlocalizedName("essm");
        setTextureName("navalarmament:essm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk41VLS.class);
        return list;
    }
}