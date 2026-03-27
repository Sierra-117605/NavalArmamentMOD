package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEOperatorConsole;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOperatorConsole extends BlockNavalBase {
    public BlockOperatorConsole() {
        super(Material.iron);
        setBlockName("operator_console");
        setHardness(2.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEOperatorConsole(); }
    @Override public String getNationId() { return "usn"; }
}