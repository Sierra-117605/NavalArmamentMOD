package com.navalarmament.tileentity.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;
import java.util.Set;

public abstract class TENavalBase extends TileEntity implements ICableConnectable {

    protected Set<String> connections = new HashSet<String>();
    protected final int updateInterval;

    public TENavalBase(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;
        long tick = worldObj.getTotalWorldTime();
        int offset = Math.abs(xCoord * 31 + zCoord) % updateInterval;
        if ((tick + offset) % updateInterval != 0) return;
        onServerTick();
    }

    protected abstract void onServerTick();

    @Override
    public void onCableConnected(int x, int y, int z) {
        connections.add(x + "," + y + "," + z);
        markDirty();
    }

    @Override
    public void onCableDisconnected(int x, int y, int z) {
        connections.remove(x + "," + y + "," + z);
        markDirty();
    }

    @Override
    public Set<String> getConnections() { return connections; }

    protected void syncToClient() {
        if (worldObj == null || worldObj.isRemote) return;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        for (String conn : connections) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("pos", conn);
            list.appendTag(tag);
        }
        nbt.setTag("connections", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        connections.clear();
        NBTTagList list = nbt.getTagList("connections", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            connections.add(list.getCompoundTagAt(i).getString("pos"));
        }
    }

    @Override
    public S35PacketUpdateTileEntity getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}