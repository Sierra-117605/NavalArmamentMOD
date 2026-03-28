package com.navalarmament.tileentity.usn;

import com.navalarmament.system.CableNetwork;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.tileentity.TileEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TEWCS extends TENavalBase {

    private final List<TargetData> targets = new ArrayList<TargetData>();

    public TEWCS() { super(20); }

    public void receiveTargets(List<TargetData> incoming) {
        targets.clear();
        targets.addAll(incoming);
    }

    @Override
    protected void onServerTick() {
        Iterator<TargetData> it = targets.iterator();
        while (it.hasNext()) { if (it.next().entity.isDead) it.remove(); }
        if (targets.isEmpty()) return;

        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        Set<String> members = CableNetwork.getInstance().getNetworkMembers(netId);
        if (members == null) return;

        List<TENavalWeapon> weapons = new ArrayList<TENavalWeapon>();
        for (String pos : members) {
            String[] p = pos.split(",");
            int x = Integer.parseInt(p[0]);
            int y = Integer.parseInt(p[1]);
            int z = Integer.parseInt(p[2]);
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TENavalWeapon) {
                TENavalWeapon w = (TENavalWeapon) te;
                if (w.getEngagementMode() == 2) weapons.add(w);
            }
        }

        for (TargetData td : targets) {
            if (td.assigned) continue;
            for (TENavalWeapon w : weapons) {
                if (w.getAmmoCount() > 0
                        && w.getCurrentRange() >= (int) td.distance
                        && w.selectAmmoForTarget(td.targetType) != null) {
                    w.setCurrentTarget(td);
                    td.assigned = true;
                    break;
                }
            }
        }
    }
}