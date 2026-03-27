package com.navalarmament.tileentity.usn;

import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalProcessor;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TEWCS extends TENavalProcessor {

    private int engagementMode = 0;
    private List<TargetData> pendingTargets = new ArrayList<TargetData>();

    public TEWCS() { super(20); }

    @Override
    protected void onServerTick() {
        if (engagementMode == 2 && !pendingTargets.isEmpty()) {
            assignTargets();
            pendingTargets.clear();
        }
    }

    public void receiveTargets(List<TargetData> targets) {
        this.pendingTargets = new ArrayList<TargetData>(targets);
    }

    private void assignTargets() {
        for (TargetData td : pendingTargets) {
            if (td.isAlly) continue;
            for (String conn : connections) {
                String[] parts = conn.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);
                TileEntity te = worldObj.getTileEntity(x, y, z);
                if (te instanceof TENavalWeapon) {
                    TENavalWeapon w = (TENavalWeapon) te;
                    if (w.getEngagementMode() == 2 && w.getAmmoCount() > 0
                            && w.getCurrentRange() >= (int)td.distance) {
                        w.setCurrentTarget(td);
                        break;
                    }
                }
            }
        }
    }

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