package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk41VLS;
import java.util.ArrayList;
import java.util.List;

public class ItemSM2 extends ItemNavalAmmo {
    public ItemSM2() {
        super(5120, 150f, 8f, 3.0f);
        setUnlocalizedName("sm2");
        setTextureName("navalarmament:sm2");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk41VLS.class);
        return list;
    }
}