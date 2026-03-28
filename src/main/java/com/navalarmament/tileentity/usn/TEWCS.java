package com.navalarmament.tileentity.usn;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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

        List<TENavalWeapon> weapons = findWeaponsViaBFS();

        for (TargetData td : targets) {
            td.assigned = false;
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

    public List<TENavalWeapon> findWeaponsViaBFS() {
        return findWeaponsViaBFS(false);
    }

    public List<TENavalWeapon> findAllWeaponsViaBFS() {
        return findWeaponsViaBFS(true);
    }

    private List<TENavalWeapon> findWeaponsViaBFS(boolean all) {
        List<TENavalWeapon> result = new ArrayList<TENavalWeapon>();
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
                    if (te instanceof TENavalWeapon) {
                        TENavalWeapon w = (TENavalWeapon) te;
                        if ((all || w.getEngagementMode() == 2) && visited.add(k)) result.add(w);
                    }
                }
            }
        }
        return result;
    }
}