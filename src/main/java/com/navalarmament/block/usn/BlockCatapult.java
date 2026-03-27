package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TECatapult;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCatapult extends BlockNavalBase {
    public BlockCatapult() {
        super(Material.iron);
        setBlockName("catapult");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TECatapult(); }
    @Override public String getNationId() { return "usn"; }
}