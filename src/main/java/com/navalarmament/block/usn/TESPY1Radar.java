package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.IMultiBlockCore;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.block.usn.BlockSPY1Radar;
import com.navalarmament.util.MultiBlockHelper;
import net.minecraft.world.World;

public class TESPY1Radar extends TENavalBase implements IMultiBlockCore {

    public TESPY1Radar() {
        super(20);
    }

    @Override
    protected void onServerTick() {
        // Phase 2では動作確認のみ。スキャン処理はPhase 3で実装
    }

    @Override
    public void onDummyDestroyed(World world, int x, int y, int z) {
        collapse(world);
    }

    @Override
    public boolean expand(World world) {
        return MultiBlockHelper.expand(
            world, xCoord, yCoord, zCoord,
            BlockSPY1Radar.BLUEPRINT, 0);
    }

    @Override
    public void collapse(World world) {
        MultiBlockHelper.collapse(
            world, xCoord, yCoord, zCoord,
            BlockSPY1Radar.BLUEPRINT, 0);
    }

    @Override
    public int[] getCorePos() {
        return new int[]{xCoord, yCoord, zCoord};
    }
}