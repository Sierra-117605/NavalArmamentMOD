package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEAuxProcessor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAuxProcessor extends BlockNavalBase {

    public BlockAuxProcessor() {
        super(Material.iron);
        setBlockName("aux_processor");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEAuxProcessor();
    }

    @Override
    public String getNationId() { return "usn"; }
}