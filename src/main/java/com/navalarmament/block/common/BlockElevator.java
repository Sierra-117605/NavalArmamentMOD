package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.common.TEElevator;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockElevator extends BlockNavalBase {
    public BlockElevator() {
        super(Material.iron);
        setBlockName("elevator");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEElevator(); }
}