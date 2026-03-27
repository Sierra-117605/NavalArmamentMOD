package com.navalarmament.tileentity.usn;

import com.navalarmament.system.TargetData;
import com.navalarmament.system.ThreatEvaluator;
import com.navalarmament.tileentity.base.TENavalProcessor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TECandD extends TENavalProcessor {

    private int channelId = -1;
    private int threatLevel = 0;
    private List<TargetData> integratedTargets = new ArrayList<TargetData>();

    public TECandD() { super(20); }

    @Override
    protected void onServerTick() {
        // 古いターゲットを削除（100Tick以上）
        long now = worldObj.getTotalWorldTime();
        java.util.Iterator<TargetData> it = integratedTargets.iterator();
        while (it.hasNext()) {
            if (now - it.next().detectedAt > 100) it.remove();
        }
        // 脅威レベル更新
        threatLevel = ThreatEvaluator.calcThreatLevel(integratedTargets);
        // WCSに送信
        sendToWCS();
        syncToClient();
    }

    public void receiveTargets(List<TargetData> targets) {
        for (TargetData td : targets) {
            boolean found = false;
            for (TargetData existing : integratedTargets) {
                if (existing.entityId == td.entityId) {
                    existing.posX = td.posX; existing.posY = td.posY; existing.posZ = td.posZ;
                    existing.threatLevel = td.threatLevel;
                    existing.detectedAt = td.detectedAt;
                    found = true; break;
                }
            }
            if (!found) integratedTargets.add(td);
        }
    }

    private void sendToWCS() {
        if (integratedTargets.isEmpty()) return;
        for (String conn : connections) {
            String[] parts = conn.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TEWCS) {
                ((TEWCS) te).receiveTargets(integratedTargets);
            }
        }
    }

    public List<TargetData> getIntegratedTargets() { return integratedTargets; }
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
        channelId  = nbt.getInteger("channelId");
        threatLevel= nbt.getInteger("threatLevel");
    }
}