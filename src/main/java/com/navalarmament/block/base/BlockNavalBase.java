package com.navalarmament.block.base;

import com.navalarmament.init.NavalCreativeTabs;
import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.base.ICableConnectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockNavalBase extends Block {

    public BlockNavalBase(Material material) {
        super(material);
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override
    public boolean hasTileEntity(int metadata) { return true; }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof ICableConnectable) {
                CableNetwork.getInstance().onDeviceConnected(world, x, y, z);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            CableNetwork.getInstance().onCableRemoved(world, x, y, z);
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    public String getNationId() { return "common"; }
}