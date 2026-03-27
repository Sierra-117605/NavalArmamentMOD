package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHullStructure extends BlockNavalBase {
    public BlockHullStructure() {
        super(Material.iron);
        setBlockName("hull_structure");
        setHardness(10.0F);
        setResistance(20.0F);
        setBlockTextureName("iron_block");
    }
    @Override public boolean hasTileEntity(int metadata) { return false; }
    @Override public TileEntity createTileEntity(World world, int metadata) { return null; }
}