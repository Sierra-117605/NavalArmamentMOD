package com.navalarmament.tileentity.base;

import com.navalarmament.system.CableNetwork;
import com.navalarmament.system.TargetData;
import com.navalarmament.system.ThreatEvaluator;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TENavalSensor extends TENavalBase {

    public TENavalSensor(int updateInterval) { super(updateInterval); }

    @Override
    protected void onServerTick() {
        scan();
    }

    protected void scan() {
        int r = getScanRange();
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
            xCoord-r, yCoord, zCoord-r,
            xCoord+r, yCoord+r, zCoord+r);

        List entities = worldObj.getEntitiesWithinAABB(Entity.class, box);
        List<TargetData> targets = new ArrayList<TargetData>();
        long tick = worldObj.getTotalWorldTime();

        for (Object obj : entities) {
            Entity e = (Entity) obj;
            if (e == null || e.isDead) continue;
            double dx = e.posX - xCoord;
            double dy = e.posY - yCoord;
            double dz = e.posZ - zCoord;
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
            TargetData td = new TargetData(e.getEntityId(),
                e.posX, e.posY, e.posZ,
                e.motionX, e.motionY, e.motionZ,
                dist, tick);
            td.threatLevel = ThreatEvaluator.evaluate(e, td);
            targets.add(td);
        }

        if (!targets.isEmpty()) {
            propagateToCandD(targets);
        }
    }

    private void propagateToCandD(List<TargetData> targets) {
        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        List<String> candds = CableNetwork.getInstance().getCandDPositions(netId);
        for (String pos : candds) {
            String[] parts = pos.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            net.minecraft.tileentity.TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof com.navalarmament.tileentity.usn.TECandD) {
                ((com.navalarmament.tileentity.usn.TECandD) te).receiveTargets(targets);
            }
        }
    }

    public abstract int getScanRange();
}