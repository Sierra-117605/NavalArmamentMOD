package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEIFLOLS;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockIFLOLS extends BlockNavalBase {
    public BlockIFLOLS() {
        super(Material.iron);
        setBlockName("iflols");
        setHardness(2.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEIFLOLS(); }
    @Override public String getNationId() { return "usn"; }
}