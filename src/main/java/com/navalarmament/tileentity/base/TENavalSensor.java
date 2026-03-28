package com.navalarmament.tileentity.base;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.system.TargetData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
            if (!(e instanceof IMob)) continue;
            detectedTargets.add(new TargetData(e, xCoord, yCoord, zCoord));
        }
        sendToCanDD();
    }

    private void sendToCanDD() {
        if (detectedTargets.isEmpty()) return;

        // CableNetworkに依存せず、BFSで直接C&Dを探す
        List<com.navalarmament.tileentity.usn.TECandD> candds = findCandDViaBFS();
        for (com.navalarmament.tileentity.usn.TECandD candd : candds) {
            candd.receiveTargets(detectedTargets);
        }
    }

    private List<com.navalarmament.tileentity.usn.TECandD> findCandDViaBFS() {
        List<com.navalarmament.tileentity.usn.TECandD> result = new ArrayList<com.navalarmament.tileentity.usn.TECandD>();
        Set<String> visited = new HashSet<String>();
        Queue<int[]> queue = new LinkedList<int[]>();
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};

        // センサーの隣からBFS開始
        for (int[] d : dirs) {
            int nx = xCoord + d[0], ny = yCoord + d[1], nz = zCoord + d[2];
            String k = nx + "," + ny + "," + nz;
            Block b = worldObj.getBlock(nx, ny, nz);
            if ((b instanceof BlockNavalCable || b instanceof com.navalarmament.block.common.BlockNavalDummy) && visited.add(k)) {
                queue.add(new int[]{nx, ny, nz});
            }
        }

        int limit = 1000; // 無限ループ防止
        while (!queue.isEmpty() && limit-- > 0) {
            int[] cur = queue.poll();
            int cx = cur[0], cy = cur[1], cz = cur[2];

            // このケーブル位置のTEを確認（C&Dかどうか）
            // ケーブル自体の隣のデバイスも確認
            for (int[] d : dirs) {
                int nx = cx + d[0], ny = cy + d[1], nz = cz + d[2];
                String k = nx + "," + ny + "," + nz;
                Block b = worldObj.getBlock(nx, ny, nz);
                if (b instanceof BlockNavalCable || b instanceof BlockNavalDummy) {
                    if (visited.add(k)) queue.add(new int[]{nx, ny, nz});
                } else {
                    // ケーブル以外のブロックのTEを確認
                    TileEntity te = worldObj.getTileEntity(nx, ny, nz);
                    if (te instanceof com.navalarmament.tileentity.usn.TECandD) {
                        if (visited.add(k)) result.add((com.navalarmament.tileentity.usn.TECandD) te);
                    }
                }
            }
        }
        return result;
    }

    public List<TargetData> getDetectedTargets() { return detectedTargets; }
}