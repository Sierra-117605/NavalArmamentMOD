package com.navalarmament.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NavalCreativeTabs {

    public static CreativeTabs NAVAL_TAB = new CreativeTabs("navalarmament") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(NavalBlocks.SPY1_RADAR);
        }
    };
}