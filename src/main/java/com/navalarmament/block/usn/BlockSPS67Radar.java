package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEPS67Radar;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSPS67Radar extends BlockNavalBase {
    public BlockSPS67Radar() {
        super(Material.iron);
        setBlockName("sps67_radar");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEPS67Radar(); }
    @Override public String getNationId() { return "usn"; }
}