package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEHarpoonLauncher;
import java.util.ArrayList;
import java.util.List;

public class ItemHarpoon extends ItemNavalAmmo {
    public ItemHarpoon() {
        super(20480, 200f, 10f, 2.0f);
        setUnlocalizedName("harpoon");
        setTextureName("navalarmament:harpoon");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEHarpoonLauncher.class);
        return list;
    }
}