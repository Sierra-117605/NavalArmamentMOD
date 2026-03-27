package com.navalarmament.block.base;

import com.navalarmament.init.NavalCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockNavalBase extends Block {

    public BlockNavalBase(Material material) {
        super(material);
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public String getNationId() {
        return "common";
    }
}