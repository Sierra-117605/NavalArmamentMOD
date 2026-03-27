package com.navalarmament.tileentity.usn;

import com.navalarmament.block.usn.BlockSPY1Radar;
import com.navalarmament.tileentity.base.IMultiBlockCore;
import com.navalarmament.tileentity.base.TENavalSensor;
import com.navalarmament.util.MultiBlockHelper;
import net.minecraft.world.World;

public class TESPY1Radar extends TENavalSensor implements IMultiBlockCore {

    public TESPY1Radar() { super(20); }

    @Override
    public int getScanRange() { return 5120; }

    @Override
    public void onDummyDestroyed(World world, int x, int y, int z) {
        collapse(world);
    }

    @Override
    public boolean expand(World world) {
        return MultiBlockHelper.expand(world, xCoord, yCoord, zCoord,
            BlockSPY1Radar.BLUEPRINT, 0);
    }

    @Override
    public void collapse(World world) {
        MultiBlockHelper.collapse(world, xCoord, yCoord, zCoord,
            BlockSPY1Radar.BLUEPRINT, 0);
    }

    @Override
    public int[] getCorePos() { return new int[]{xCoord, yCoord, zCoord}; }
}