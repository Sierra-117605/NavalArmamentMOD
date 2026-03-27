package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEWCS;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWCS extends BlockNavalBase {

    public BlockWCS() {
        super(Material.iron);
        setBlockName("wcs");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEWCS();
    }

    @Override
    public String getNationId() { return "usn"; }
}