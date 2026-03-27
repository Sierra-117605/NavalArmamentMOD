package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalProcessor;
import net.minecraft.nbt.NBTTagCompound;

public class TEDataLink extends TENavalProcessor {

    private int channelId = 0;

    public TEDataLink() { super(20); }

    @Override
    protected void onServerTick() {}

    public int getChannelId() { return channelId; }
    public void setChannelId(int id) { this.channelId = id; markDirty(); }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("channelId", channelId);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        channelId = nbt.getInteger("channelId");
    }
}