package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCICDecoration extends BlockNavalBase {
    public BlockCICDecoration() {
        super(Material.iron);
        setBlockName("cic_decoration");
        setHardness(2.0F);
        setBlockTextureName("iron_block");
    }
    @Override public boolean hasTileEntity(int metadata) { return false; }
    @Override public TileEntity createTileEntity(World world, int metadata) { return null; }
    @Override public String getNationId() { return "usn"; }
}