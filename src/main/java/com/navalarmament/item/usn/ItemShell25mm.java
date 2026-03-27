package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk38Gun;
import java.util.ArrayList;
import java.util.List;

public class ItemShell25mm extends ItemNavalAmmo {
    public ItemShell25mm() {
        super(1280, 15f, 1f, 3.5f);
        setUnlocalizedName("shell_25mm");
        setTextureName("navalarmament:shell_25mm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk38Gun.class);
        return list;
    }
}