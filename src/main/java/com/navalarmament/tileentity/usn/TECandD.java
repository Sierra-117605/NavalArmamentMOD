package com.navalarmament.tileentity.usn;

import com.navalarmament.system.CableNetwork;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalBase;
import net.minecraft.tileentity.TileEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TECandD extends TENavalBase {

    private final List<TargetData> integratedTargets = new ArrayList<TargetData>();

    public TECandD() { super(20); }

    public void receiveTargets(List<TargetData> targets) {
        for (TargetData td : targets) {
            boolean found = false;
            for (TargetData existing : integratedTargets) {
                if (existing.entity == td.entity) { found = true; break; }
            }
            if (!found) integratedTargets.add(td);
        }
        Iterator<TargetData> it = integratedTargets.iterator();
        while (it.hasNext()) { if (it.next().entity.isDead) it.remove(); }
    }

    @Override
    protected void onServerTick() {
        Iterator<TargetData> it = integratedTargets.iterator();
        while (it.hasNext()) { if (it.next().entity.isDead) it.remove(); }
        sendToWCS();
    }

    private void sendToWCS() {
        if (integratedTargets.isEmpty()) return;
        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        Set<String> members = CableNetwork.getInstance().getNetworkMembers(netId);
        if (members == null) return;
        for (String pos : members) {
            String[] p = pos.split(",");
            int x = Integer.parseInt(p[0]);
            int y = Integer.parseInt(p[1]);
            int z = Integer.parseInt(p[2]);
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TEWCS) {
                ((TEWCS) te).receiveTargets(integratedTargets);
            }
        }
    }

    public List<TargetData> getIntegratedTargets() { return integratedTargets; }
}