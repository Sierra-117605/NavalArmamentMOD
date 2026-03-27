package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEMk38Gun;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMk38Gun extends BlockNavalBase {
    public BlockMk38Gun() {
        super(Material.iron);
        setBlockName("mk38_gun");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEMk38Gun(); }
    @Override public String getNationId() { return "usn"; }
}