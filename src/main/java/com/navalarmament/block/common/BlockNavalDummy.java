package com.navalarmament.block.common;

import com.navalarmament.tileentity.common.TENavalDummy;
import com.navalarmament.block.base.BlockNavalBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNavalDummy extends BlockNavalBase {

    public BlockNavalDummy() {
    super(Material.iron);
    setBlockName("naval_dummy");
    setHardness(-1.0F);
    setBlockTextureName("iron_block"); // 仮テクスチャ
}

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TENavalDummy();
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int side) {
        if (world.isRemote) return;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TENavalDummy) {
            TENavalDummy dummy = (TENavalDummy) te;
            dummy.notifyCore(world);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TENavalDummy) {
            TENavalDummy dummy = (TENavalDummy) te;
            int[] core = dummy.getCorePos();
            if (core != null) {
                Block coreBlock = world.getBlock(core[0], core[1], core[2]);
                return coreBlock.onBlockActivated(world, core[0], core[1], core[2],
                        player, side, hitX, hitY, hitZ);
            }
        }
        return false;
    }
}