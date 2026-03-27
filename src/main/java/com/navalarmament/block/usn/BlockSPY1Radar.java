package com.navalarmament.block.usn;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.usn.TESPY1Radar;
import com.navalarmament.util.MultiBlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSPY1Radar extends BlockNavalBase {

    // SPY-1のブループリント（コアからの相対座標）
    // 簡易版：2x2x3の小さなサイズで動作確認
    public static final int[][] BLUEPRINT = {
        {-1, 0,  0}, {1, 0,  0}, {0, 0, -1}, {0, 0, 1},
        {-1, 1,  0}, {1, 1,  0}, {0, 1, -1}, {0, 1, 1},
        {-1, 2,  0}, {1, 2,  0}, {0, 2, -1}, {0, 2, 1},
    };
    
    
    public BlockSPY1Radar() {
    super(Material.iron);
    setBlockName("spy1_radar");
    setHardness(5.0F);
    setBlockTextureName("iron_block"); // 仮テクスチャ（鉄ブロック）
}

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TESPY1Radar();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.isRemote) return;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TESPY1Radar) {
            MultiBlockHelper.expand(world, x, y, z, BLUEPRINT, 0);
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int side) {
        if (world.isRemote) return;
        MultiBlockHelper.collapse(world, x, y, z, BLUEPRINT, 0);
    }

    @Override
    public String getNationId() {
        return "usn";
    }
}