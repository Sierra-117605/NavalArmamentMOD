package com.navalarmament.tileentity.usn;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalBase;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TECandD extends TENavalBase {

    private final List<TargetData> integratedTargets = new ArrayList<TargetData>();

    public TECandD() { super(20); }

    public void receiveTargets(List<TargetData> targets) {
        com.navalarmament.NavalArmamentMod.logger.info("C&D receiveTargets called: " + targets.size() + " incoming, current=" + integratedTargets.size());
        for (TargetData td : targets) {
            boolean found = false;
            for (TargetData existing : integratedTargets) {
                if (existing.entity.getEntityId() == td.entity.getEntityId()) {
                    found = true;
                    break;
                }
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
        com.navalarmament.NavalArmamentMod.logger.info("C&D sendToWCS: " + integratedTargets.size() + " targets");
        if (integratedTargets.isEmpty()) return;

        List<TEWCS> wcsList = findWCSViaBFS();
        for (TEWCS wcs : wcsList) {
            wcs.receiveTargets(integratedTargets);
        }
    }

    private List<TEWCS> findWCSViaBFS() {
        List<TEWCS> result = new ArrayList<TEWCS>();
        Set<String> visited = new HashSet<String>();
        Queue<int[]> queue = new LinkedList<int[]>();
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};

        for (int[] d : dirs) {
            int nx = xCoord + d[0], ny = yCoord + d[1], nz = zCoord + d[2];
            String k = nx + "," + ny + "," + nz;
            Block b = worldObj.getBlock(nx, ny, nz);
            if ((b instanceof BlockNavalCable || b instanceof BlockNavalDummy) && visited.add(k)) {
                queue.add(new int[]{nx, ny, nz});
            }
        }

        int limit = 1000;
        while (!queue.isEmpty() && limit-- > 0) {
            int[] cur = queue.poll();
            int cx = cur[0], cy = cur[1], cz = cur[2];
            for (int[] d : dirs) {
                int nx = cx + d[0], ny = cy + d[1], nz = cz + d[2];
                String k = nx + "," + ny + "," + nz;
                Block b = worldObj.getBlock(nx, ny, nz);
                if (b instanceof BlockNavalCable || b instanceof BlockNavalDummy) {
                    if (visited.add(k)) queue.add(new int[]{nx, ny, nz});
                } else {
                    TileEntity te = worldObj.getTileEntity(nx, ny, nz);
                    if (te instanceof TEWCS && visited.add(k)) result.add((TEWCS) te);
                }
            }
        }
        return result;
    }

    public List<TargetData> getIntegratedTargets() { return integratedTargets; }
}