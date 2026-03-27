package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TECandD;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCandD extends BlockNavalBase {

    public BlockCandD() {
        super(Material.iron);
        setBlockName("candd");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TECandD();
    }

    @Override
    public String getNationId() { return "usn"; }
}