package com.navalarmament.tileentity.common;

import com.navalarmament.tileentity.base.TENavalBase;
import net.minecraft.nbt.NBTTagCompound;

public class TEShutter extends TENavalBase {
    private boolean isOpen = false;

    public TEShutter() { super(20); }
    @Override protected void onServerTick() {}

    public boolean isOpen() { return isOpen; }
    public void toggle() { isOpen = !isOpen; markDirty(); }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isOpen", isOpen);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        isOpen = nbt.getBoolean("isOpen");
    }
}