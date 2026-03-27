package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSubmarineEquip extends BlockNavalBase {
    public BlockSubmarineEquip() {
        super(Material.iron);
        setBlockName("submarine_equip");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override public boolean hasTileEntity(int metadata) { return false; }
    @Override public TileEntity createTileEntity(World world, int metadata) { return null; }
}