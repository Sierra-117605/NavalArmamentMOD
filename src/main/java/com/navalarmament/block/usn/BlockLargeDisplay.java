package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TELargeDisplay;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLargeDisplay extends BlockNavalBase {
    public BlockLargeDisplay() {
        super(Material.iron);
        setBlockName("large_display");
        setHardness(2.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TELargeDisplay(); }
    @Override public String getNationId() { return "usn"; }
}