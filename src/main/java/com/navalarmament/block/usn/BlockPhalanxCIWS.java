package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEPhalanxCIWS;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPhalanxCIWS extends BlockNavalBase {
    public BlockPhalanxCIWS() {
        super(Material.iron);
        setBlockName("phalanx_ciws");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEPhalanxCIWS(); }
    @Override public String getNationId() { return "usn"; }
}