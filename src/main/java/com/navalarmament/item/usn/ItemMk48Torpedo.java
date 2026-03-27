package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk32Torpedo;
import java.util.ArrayList;
import java.util.List;

public class ItemMk48Torpedo extends ItemNavalAmmo {
    public ItemMk48Torpedo() {
        super(2560, 350f, 12f, 1.8f);
        setUnlocalizedName("mk48");
        setTextureName("navalarmament:mk48");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk32Torpedo.class);
        return list;
    }
}