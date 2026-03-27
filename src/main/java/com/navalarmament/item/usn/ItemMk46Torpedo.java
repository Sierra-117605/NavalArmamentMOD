package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk32Torpedo;
import java.util.ArrayList;
import java.util.List;

public class ItemMk46Torpedo extends ItemNavalAmmo {
    public ItemMk46Torpedo() {
        super(1280, 200f, 8f, 1.5f);
        setUnlocalizedName("mk46");
        setTextureName("navalarmament:mk46");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk32Torpedo.class);
        return list;
    }
}