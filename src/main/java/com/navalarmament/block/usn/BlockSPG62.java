package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESPG62;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSPG62 extends BlockNavalBase {
    public BlockSPG62() {
        super(Material.iron);
        setBlockName("spg62");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TESPG62(); }
    @Override public String getNationId() { return "usn"; }
}