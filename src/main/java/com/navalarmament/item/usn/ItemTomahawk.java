package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk41VLS;
import java.util.ArrayList;
import java.util.List;

public class ItemTomahawk extends ItemNavalAmmo {
    public ItemTomahawk() {
        super(20480, 300f, 15f, 1.5f);
        setUnlocalizedName("tomahawk");
        setTextureName("navalarmament:tomahawk");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk41VLS.class);
        return list;
    }
}