package com.navalarmament.tileentity.usn;

import com.navalarmament.system.TargetData;
import com.navalarmament.system.TargetType;
import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.base.TENavalSensor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TESQS53Sonar extends TENavalSensor {
    public TESQS53Sonar() { super(20); }
    @Override public int getScanRange() { return 128; }

    @Override
    protected void onServerTick() {
        detectedTargets.clear();
        int r = getScanRange();
        List entities = worldObj.getEntitiesWithinAABBExcludingEntity(
            null,
            net.minecraft.util.AxisAlignedBB.getBoundingBox(
                xCoord-r, yCoord-r, zCoord-r,
                xCoord+r, yCoord+r, zCoord+r));
        for (Object obj : entities) {
            if (!(obj instanceof Entity)) continue;
            Entity e = (Entity) obj;
            if (e.isDead) continue;
            if (!(e instanceof EntityMob)) continue;
            // ソナーは水中のみ検知
            if (!e.isInWater()) continue;
            detectedTargets.add(new TargetData(e, xCoord, yCoord, zCoord));
        }
        sendToCanDD();
    }

    private void sendToCanDD() {
        if (detectedTargets.isEmpty()) return;
        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        Set members = CableNetwork.getInstance().getNetworkMembers(netId);
        if (members == null) return;
        for (Object posObj : members) {
            String pos = (String) posObj;
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
}