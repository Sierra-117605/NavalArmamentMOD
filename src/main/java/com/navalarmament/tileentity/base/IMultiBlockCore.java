package com.navalarmament.tileentity.base;

import net.minecraft.world.World;

public interface IMultiBlockCore {

    // ダミーブロックが破壊されたときに呼ばれる
    void onDummyDestroyed(World world, int x, int y, int z);

    // マルチブロック全体を展開する
    boolean expand(World world);

    // マルチブロック全体を撤去する
    void collapse(World world);

    // コアブロックの座標を返す
    int[] getCorePos();
}