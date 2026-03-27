package com.navalarmament.tileentity.common;

import com.navalarmament.tileentity.base.TENavalBase;
import net.minecraft.nbt.NBTTagCompound;

public class TEElevator extends TENavalBase {
    private boolean isMoving = false;

    public TEElevator() { super(1); }
    @Override protected void onServerTick() {}

    public boolean isMoving() { return isMoving; }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isMoving", isMoving);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        isMoving = nbt.getBoolean("isMoving");
    }
}