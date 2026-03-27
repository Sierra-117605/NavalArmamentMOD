package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEMk41VLS;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMk41VLS extends BlockNavalBase {
    public BlockMk41VLS() {
        super(Material.iron);
        setBlockName("mk41_vls");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEMk41VLS(); }
    @Override public String getNationId() { return "usn"; }
}