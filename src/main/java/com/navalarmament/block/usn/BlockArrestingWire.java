package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TEArrestingWire;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArrestingWire extends BlockNavalBase {
    public BlockArrestingWire() {
        super(Material.iron);
        setBlockName("arresting_wire");
        setHardness(3.0F);
        setBlockTextureName("iron_block");
    }
    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEArrestingWire(); }
    @Override public String getNationId() { return "usn"; }
}