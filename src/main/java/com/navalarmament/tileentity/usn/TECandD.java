package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalProcessor;
import net.minecraft.nbt.NBTTagCompound;

public class TECandD extends TENavalProcessor {

    private int channelId = -1;
    private int threatLevel = 0;

    public TECandD() {
        super(20);
    }

    @Override
    protected void onServerTick() {
    }

    public int getChannelId() { return channelId; }
    public void setChannelId(int id) { this.channelId = id; markDirty(); }
    public int getThreatLevel() { return threatLevel; }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("channelId", channelId);
        nbt.setInteger("threatLevel", threatLevel);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        channelId = nbt.getInteger("channelId");
        threatLevel = nbt.getInteger("threatLevel");
    }
}