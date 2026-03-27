package com.navalarmament.block.common;

import com.navalarmament.init.NavalCreativeTabs;
import com.navalarmament.tileentity.common.TENavalCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNavalCable extends Block {

    public BlockNavalCable() {
        super(Material.iron);
        setBlockName("naval_cable");
        setHardness(1.0F);
        setBlockTextureName("iron_block");
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TENavalCable();
    }

    // 隣接ブロック変化時に接続を更新
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (world.isRemote) return;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TENavalCable) {
            ((TENavalCable) te).updateConnections(world, x, y, z);
        }
    }
}