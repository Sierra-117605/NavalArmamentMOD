package com.navalarmament.item.usn;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.tileentity.usn.TESubTorpedo;
import com.navalarmament.tileentity.usn.TESubVLS8;
import com.navalarmament.tileentity.usn.TESubVLS16;
import java.util.ArrayList;
import java.util.List;

public class ItemSubSSM extends ItemNavalAmmo {
    public ItemSubSSM() {
        super(20480, 250f, 12f, 2.0f);
        setUnlocalizedName("sub_ssm");
        setTextureName("navalarmament:sub_ssm");
    }
    @Override
    public List<Class<?>> getCompatibleWeapons() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(TESubTorpedo.class);
        list.add(TESubVLS8.class);
        list.add(TESubVLS16.class);
        return list;
    }
}