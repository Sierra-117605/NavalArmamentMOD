package com.navalarmament.tileentity.usn;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TEOperatorConsole extends TENavalBase {

    public TEOperatorConsole() { super(20); }

    @Override
    protected void onServerTick() {
        // 接続中の全武装の弾数をクライアントに送信
        List<TENavalWeapon> weapons = findAllWeaponsViaBFS();
        for (TENavalWeapon w : weapons) {
            for (Object obj : net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                net.minecraft.entity.player.EntityPlayerMP player = (net.minecraft.entity.player.EntityPlayerMP) obj;
                com.navalarmament.network.NavalPacketHandler.sendAmmoSync(
                    player, w.xCoord, w.yCoord, w.zCoord, w.getAmmoCount());
            }
        }
    }

    public List<TENavalWeapon> findAllWeaponsViaBFS() {
        return findWeaponsInternal();
    }

    public List<TENavalWeapon> findWeaponsViaBFS() {
        return findWeaponsInternal();
    }

    private List<TENavalWeapon> findWeaponsInternal() {
        List<TENavalWeapon> result = new ArrayList<TENavalWeapon>();
        Set<String> visited = new HashSet<String>();
        Queue<int[]> queue = new LinkedList<int[]>();
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};

        // まずWCSを探す
        List<TEWCS> wcsList = findWCSViaBFS();
        // WCS経由で武装を探す
        for (TEWCS wcs : wcsList) {
            List<TENavalWeapon> weapons = wcs.findAllWeaponsViaBFS();
            for (TENavalWeapon w : weapons) {
                boolean found = false;
                for (TENavalWeapon existing : result) {
                    if (existing.xCoord == w.xCoord && existing.yCoord == w.yCoord && existing.zCoord == w.zCoord) {
                        found = true; break;
                    }
                }
                if (!found) result.add(w);
            }
        }
        return result;
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
            if ((b instanceof BlockNavalCable || b instanceof BlockNavalDummy) && visited.add(k))
                queue.add(new int[]{nx, ny, nz});
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
}