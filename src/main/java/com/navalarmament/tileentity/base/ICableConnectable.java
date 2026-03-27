package com.navalarmament.tileentity.base;

public interface ICableConnectable {
    void onCableConnected(int x, int y, int z);
    void onCableDisconnected(int x, int y, int z);
    java.util.Set<String> getConnections();
}