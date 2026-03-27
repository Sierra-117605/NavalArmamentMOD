package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEMk45Gun;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMk45Gun extends BlockNavalBase {

    public BlockMk45Gun() {
        super(Material.iron);
        setBlockName("mk45_gun");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEMk45Gun();
    }

    @Override
    public String getNationId() { return "usn"; }
}