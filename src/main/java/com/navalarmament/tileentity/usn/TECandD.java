package com.navalarmament.tileentity.usn;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.network.NavalPacketHandler;
import com.navalarmament.system.TargetData;
import com.navalarmament.tileentity.base.TENavalBase;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TECandD extends TENavalBase {

    /** サーバー側の統合済みターゲットリスト */
    private final List<TargetData> integratedTargets = new ArrayList<TargetData>();

    /** クライアント側の表示用データ（パケット同期済み） */
    public static class ClientTargetInfo {
        public String entityName;
        public String targetTypeName;
        public int distance;
        public boolean assigned;
    }
    private final List<ClientTargetInfo> clientTargets = new ArrayList<ClientTargetInfo>();

    public List<ClientTargetInfo> getClientTargets() { return clientTargets; }
    public void setClientTargets(List<ClientTargetInfo> list) {
        clientTargets.clear();
        clientTargets.addAll(list);
    }

    public TECandD() { super(20); }

    private static final long TARGET_TTL_MS = 3000L;

    public void receiveTargets(List<TargetData> targets) {
        for (TargetData td : targets) {
            // Replace existing entry so position/distance/detectedAt stay fresh
            Iterator<TargetData> it2 = integratedTargets.iterator();
            boolean assigned = false;
            int awx = 0, awy = 0, awz = 0;
            while (it2.hasNext()) {
                TargetData existing = it2.next();
                if (existing.entity.getEntityId() == td.entity.getEntityId()) {
                    assigned = existing.assigned;
                    awx = existing.assignedWeaponX;
                    awy = existing.assignedWeaponY;
                    awz = existing.assignedWeaponZ;
                    it2.remove();
                    break;
                }
            }
            td.assigned = assigned;
            td.assignedWeaponX = awx;
            td.assignedWeaponY = awy;
            td.assignedWeaponZ = awz;
            integratedTargets.add(td);
        }
    }

    @Override
    protected void onServerTick() {
        long now = System.currentTimeMillis();
        Iterator<TargetData> it = integratedTargets.iterator();
        while (it.hasNext()) {
            TargetData td = it.next();
            if (td.entity.isDead || now - td.detectedAt > TARGET_TTL_MS) it.remove();
        }
        // 距離でソートして最大40件に絞り、クライアントへ同期
        List<TargetData> sorted = new ArrayList<TargetData>(integratedTargets);
        Collections.sort(sorted, new Comparator<TargetData>() {
            @Override public int compare(TargetData a, TargetData b) {
                return Double.compare(a.distance, b.distance);
            }
        });
        if (sorted.size() > 40) sorted = sorted.subList(0, 40);
        NavalPacketHandler.sendCandDSync(worldObj, xCoord, yCoord, zCoord, sorted);
        sendToWCS();
    }

    private void sendToWCS() {
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