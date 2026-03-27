package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalProcessor;
import net.minecraft.nbt.NBTTagCompound;

public class TEWCS extends TENavalProcessor {

    private int engagementMode = 0;

    public TEWCS() { super(20); }

    @Override
    protected void onServerTick() {}

    public int getEngagementMode() { return engagementMode; }
    public void setEngagementMode(int mode) { this.engagementMode = mode; markDirty(); }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("engagementMode", engagementMode);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        engagementMode = nbt.getInteger("engagementMode");
    }
}