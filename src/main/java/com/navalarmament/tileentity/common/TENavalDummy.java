package com.navalarmament.tileentity.common;

import com.navalarmament.tileentity.base.IMultiBlockCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TENavalDummy extends TileEntity {

    private int coreX, coreY, coreZ;
    private boolean hasCore = false;

    public void setCorePos(int x, int y, int z) {
        this.coreX = x;
        this.coreY = y;
        this.coreZ = z;
        this.hasCore = true;
        markDirty();
    }

    public int[] getCorePos() {
        if (!hasCore) return null;
        return new int[]{coreX, coreY, coreZ};
    }

    public void notifyCore(World world) {
        if (!hasCore) return;
        TileEntity core = world.getTileEntity(coreX, coreY, coreZ);
        if (core instanceof IMultiBlockCore) {
            ((IMultiBlockCore) core).onDummyDestroyed(world, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasCore", hasCore);
        if (hasCore) {
            nbt.setInteger("coreX", coreX);
            nbt.setInteger("coreY", coreY);
            nbt.setInteger("coreZ", coreZ);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        hasCore = nbt.getBoolean("hasCore");
        if (hasCore) {
            coreX = nbt.getInteger("coreX");
            coreY = nbt.getInteger("coreY");
            coreZ = nbt.getInteger("coreZ");
        }
    }
}