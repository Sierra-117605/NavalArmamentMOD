package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEDataLink;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDataLink extends BlockNavalBase {

    public BlockDataLink() {
        super(Material.iron);
        setBlockName("datalink");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEDataLink();
    }

    @Override
    public String getNationId() { return "usn"; }
}