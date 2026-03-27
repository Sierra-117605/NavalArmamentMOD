package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TEPhalanxCIWS;
import java.util.ArrayList;
import java.util.List;

public class ItemShell20mm extends ItemNavalAmmo {
    public ItemShell20mm() {
        super(1280, 10f, 0f, 4.0f);
        setUnlocalizedName("shell_20mm");
        setTextureName("navalarmament:shell_20mm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TEPhalanxCIWS.class);
        return list;
    }
}