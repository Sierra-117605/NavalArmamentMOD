package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEMk32Torpedo;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMk32Torpedo extends BlockNavalBase {
    public BlockMk32Torpedo() {
        super(Material.iron);
        setBlockName("mk32_torpedo");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEMk32Torpedo(); }
    @Override public String getNationId() { return "usn"; }
}