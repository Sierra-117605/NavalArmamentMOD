package com.navalarmament.tileentity.common;

import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.base.ICableConnectable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TENavalCable extends TileEntity {

    private boolean[] connected = new boolean[6];
    private boolean registeredToNetwork = false;

    private static final int[][] DIRS = {
        { 0, 0,-1}, { 0, 0, 1},
        { 1, 0, 0}, {-1, 0, 0},
        { 0, 1, 0}, { 0,-1, 0}
    };

    @Override
    public void updateEntity() {
        if (worldObj == null || worldObj.isRemote) return;
        if (!registeredToNetwork) {
            CableNetwork.getInstance().onCablePlaced(worldObj, xCoord, yCoord, zCoord);
            registeredToNetwork = true;
        }
    }

    public void updateConnections(World world, int x, int y, int z) {
        boolean changed = false;
        for (int i = 0; i < 6; i++) {
            int nx = x + DIRS[i][0];
            int ny = y + DIRS[i][1];
            int nz = z + DIRS[i][2];
            TileEntity neighbor = world.getTileEntity(nx, ny, nz);
            boolean canConnect = neighbor instanceof ICableConnectable
                              || neighbor instanceof TENavalCable;
            if (connected[i] != canConnect) {
                connected[i] = canConnect;
                changed = true;
                if (canConnect && neighbor instanceof ICableConnectable) {
                    ((ICableConnectable) neighbor).onCableConnected(x, y, z);
                } else if (!canConnect && neighbor instanceof ICableConnectable) {
                    ((ICableConnectable) neighbor).onCableDisconnected(x, y, z);
                }
            }
        }
        if (changed) {
            markDirty();
            world.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public boolean[] getConnected() { return connected; }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for (int i = 0; i < 6; i++) nbt.setBoolean("conn" + i, connected[i]);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        for (int i = 0; i < 6; i++) connected[i] = nbt.getBoolean("conn" + i);
    }
}