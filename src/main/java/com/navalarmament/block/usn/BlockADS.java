package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEADS;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockADS extends BlockNavalBase {

    public BlockADS() {
        super(Material.iron);
        setBlockName("ads");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEADS();
    }

    @Override
    public String getNationId() { return "usn"; }
}