package com.navalarmament.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockNavalBase extends Block {

    public BlockNavalBase(Material material) {
        super(material);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    // 国別パックIDを返す（サブクラスでオーバーライド）
    public String getNationId() {
        return "common";
    }
}