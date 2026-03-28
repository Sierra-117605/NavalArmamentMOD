package com.navalarmament.tileentity.base;

import com.navalarmament.system.CableNetwork;
import com.navalarmament.system.TargetData;
import com.navalarmament.system.TargetType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class TENavalSensor extends TENavalBase {

    protected List<TargetData> detectedTargets = new ArrayList<TargetData>();

    public TENavalSensor(int updateInterval) { super(updateInterval); }

    public abstract int getScanRange();

    @Override
    protected void onServerTick() {
        detectedTargets.clear();
        int r = getScanRange();
        List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(
            null,
            net.minecraft.util.AxisAlignedBB.getBoundingBox(
                xCoord - r, yCoord - r, zCoord - r,
                xCoord + r, yCoord + r, zCoord + r));
        for (Entity e : entities) {
            if (e == null || e.isDead) continue;
            if (!(e instanceof EntityMob)) continue;
            detectedTargets.add(new TargetData(e, xCoord, yCoord, zCoord));
        }
        sendToCanDD();
    }

    private void sendToCanDD() {
        if (detectedTargets.isEmpty()) return;
        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        Set<String> members = CableNetwork.getInstance().getNetworkMembers(netId);
        if (members == null) return;
        for (String pos : members) {
            String[] p = pos.split(",");
            int x = Integer.parseInt(p[0]);
            int y = Integer.parseInt(p[1]);
            int z = Integer.parseInt(p[2]);
            net.minecraft.tileentity.TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof com.navalarmament.tileentity.usn.TECandD) {
                ((com.navalarmament.tileentity.usn.TECandD) te).receiveTargets(detectedTargets);
            }
        }
    }

    public List<TargetData> getDetectedTargets() { return detectedTargets; }
}