package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk45Gun;
import java.util.ArrayList;
import java.util.List;

public class ItemShell5InchAP extends ItemNavalAmmo {
    public ItemShell5InchAP() {
        super(5120, 80f, 2f, 2.8f);
        setUnlocalizedName("shell_5inch_ap");
        setTextureName("navalarmament:shell_5inch_ap");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk45Gun.class);
        return list;
    }
}