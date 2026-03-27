package com.navalarmament.block.common;

import com.navalarmament.init.NavalCreativeTabs;
import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.common.TENavalCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean hasTileEntity(int metadata) { return true; }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TENavalCable();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            CableNetwork.getInstance().onCablePlaced(world, x, y, z);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            CableNetwork.getInstance().onCableRemoved(world, x, y, z);
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (world.isRemote) return;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TENavalCable) {
            ((TENavalCable) te).updateConnections(world, x, y, z);
        }
    }
}