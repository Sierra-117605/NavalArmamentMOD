package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEMk45Gun;
import java.util.ArrayList;
import java.util.List;

public class ItemShell5Inch extends ItemNavalAmmo {

    public ItemShell5Inch() {
        super(5120, 30f, 5f, 2.5f);
        setUnlocalizedName("shell_5inch");
        setTextureName("navalarmament:shell_5inch");
    }

    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEMk45Gun.class);
        return list;
    }
}