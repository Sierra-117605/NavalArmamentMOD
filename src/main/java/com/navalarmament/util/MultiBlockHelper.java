package com.navalarmament.util;

import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.tileentity.common.TENavalDummy;
import com.navalarmament.init.NavalBlocks;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiBlockHelper {

    // ブループリント：コアからの相対座標リスト [x, y, z]
    // 展開：ダミーブロックを自動設置
    public static boolean expand(World world, int coreX, int coreY, int coreZ,
                                 int[][] blueprint, int facing) {
        int[][] rotated = rotate(blueprint, facing);

        // 1. 設置可能チェック
        for (int[] offset : rotated) {
            int tx = coreX + offset[0];
            int ty = coreY + offset[1];
            int tz = coreZ + offset[2];
            Block existing = world.getBlock(tx, ty, tz);
            if (!existing.isAir(world, tx, ty, tz) &&
                    !(existing instanceof BlockNavalDummy)) {
                return false; // 設置不可
            }
        }

        // 2. ダミーブロックを設置
        for (int[] offset : rotated) {
            int tx = coreX + offset[0];
            int ty = coreY + offset[1];
            int tz = coreZ + offset[2];
            world.setBlock(tx, ty, tz, NavalBlocks.NAVAL_DUMMY, 0, 3);
            TileEntity te = world.getTileEntity(tx, ty, tz);
            if (te instanceof TENavalDummy) {
                ((TENavalDummy) te).setCorePos(coreX, coreY, coreZ);
            }
        }
        return true;
    }

    // 撤去：ダミーブロックを全てAirに
    public static void collapse(World world, int coreX, int coreY, int coreZ,
                                int[][] blueprint, int facing) {
        int[][] rotated = rotate(blueprint, facing);
        for (int[] offset : rotated) {
            int tx = coreX + offset[0];
            int ty = coreY + offset[1];
            int tz = coreZ + offset[2];
            Block b = world.getBlock(tx, ty, tz);
            if (b instanceof BlockNavalDummy) {
                world.setBlockToAir(tx, ty, tz);
            }
        }
        world.setBlockToAir(coreX, coreY, coreZ);
    }

    // 向きに応じてブループリントを回転
    private static int[][] rotate(int[][] blueprint, int facing) {
        int[][] result = new int[blueprint.length][3];
        for (int i = 0; i < blueprint.length; i++) {
            int x = blueprint[i][0];
            int y = blueprint[i][1];
            int z = blueprint[i][2];
            switch (facing) {
                case 0: result[i] = new int[]{ x, y, z};  break; // NORTH
                case 1: result[i] = new int[]{ z, y,-x};  break; // EAST
                case 2: result[i] = new int[]{-x, y,-z};  break; // SOUTH
                case 3: result[i] = new int[]{-z, y, x};  break; // WEST
                default:result[i] = new int[]{ x, y, z};  break;
            }
        }
        return result;
    }
}