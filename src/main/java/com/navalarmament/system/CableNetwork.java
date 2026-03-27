package com.navalarmament.system;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.tileentity.base.ICableConnectable;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.*;

public class CableNetwork {

    private static CableNetwork instance = new CableNetwork();
    public static CableNetwork getInstance() { return instance; }

    // networkId -> 接続デバイスBlockPosのSet（"x,y,z"形式）
    private final Map<UUID, Set<String>> networks = new HashMap<UUID, Set<String>>();
    // pos -> networkId
    private final Map<String, UUID> posToNetwork = new HashMap<String, UUID>();
    // networkId -> C&D posリスト
    private final Map<UUID, List<String>> networkCandD = new HashMap<UUID, List<String>>();

    private static String key(int x, int y, int z) { return x+","+y+","+z; }

    public void onCablePlaced(World world, int x, int y, int z) {
        Set<UUID> adjacent = new HashSet<UUID>();
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
        for (int[] d : dirs) {
            String k = key(x+d[0], y+d[1], z+d[2]);
            if (posToNetwork.containsKey(k)) adjacent.add(posToNetwork.get(k));
        }
        String pos = key(x, y, z);
        if (adjacent.isEmpty()) {
            UUID id = UUID.randomUUID();
            Set<String> net = new HashSet<String>();
            net.add(pos);
            networks.put(id, net);
            posToNetwork.put(pos, id);
            rebuildRoleCache(id, world);
        } else if (adjacent.size() == 1) {
            UUID id = adjacent.iterator().next();
            networks.get(id).add(pos);
            posToNetwork.put(pos, id);
            rebuildRoleCache(id, world);
        } else {
            mergeNetworks(world, adjacent, pos);
        }
    }

    public void onCableRemoved(World world, int x, int y, int z) {
        String pos = key(x, y, z);
        UUID netId = posToNetwork.remove(pos);
        if (netId == null) return;
        Set<String> net = networks.remove(netId);
        networkCandD.remove(netId);
        if (net == null) return;
        net.remove(pos);
        rebuildFromScratch(world, net);
    }

    public void onDeviceConnected(World world, int dx, int dy, int dz) {
        String pos = key(dx, dy, dz);
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
        for (int[] d : dirs) {
            String k = key(dx+d[0], dy+d[1], dz+d[2]);
            if (posToNetwork.containsKey(k)) {
                UUID id = posToNetwork.get(k);
                networks.get(id).add(pos);
                posToNetwork.put(pos, id);
                rebuildRoleCache(id, world);
                return;
            }
        }
    }

    public UUID getNetworkId(int x, int y, int z) {
        return posToNetwork.get(key(x, y, z));
    }

    public List<String> getCandDPositions(UUID networkId) {
        List<String> list = networkCandD.get(networkId);
        return list != null ? list : Collections.<String>emptyList();
    }

    private void rebuildRoleCache(UUID netId, World world) {
        List<String> candds = new ArrayList<String>();
        Set<String> net = networks.get(netId);
        if (net == null) return;
        for (String pos : net) {
            String[] parts = pos.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof com.navalarmament.tileentity.usn.TECandD) candds.add(pos);
        }
        networkCandD.put(netId, candds);
    }

    private void mergeNetworks(World world, Set<UUID> ids, String newPos) {
        UUID masterId = ids.iterator().next();
        Set<String> masterNet = networks.get(masterId);
        masterNet.add(newPos);
        posToNetwork.put(newPos, masterId);
        for (UUID id : ids) {
            if (id.equals(masterId)) continue;
            Set<String> other = networks.remove(id);
            networkCandD.remove(id);
            if (other != null) {
                for (String p : other) posToNetwork.put(p, masterId);
                masterNet.addAll(other);
            }
        }
        rebuildRoleCache(masterId, world);
    }

    private void rebuildFromScratch(World world, Set<String> remaining) {
        Set<String> unvisited = new HashSet<String>(remaining);
        while (!unvisited.isEmpty()) {
            String start = unvisited.iterator().next();
            Set<String> component = bfs(world, start, unvisited);
            UUID newId = UUID.randomUUID();
            networks.put(newId, component);
            for (String p : component) posToNetwork.put(p, newId);
            unvisited.removeAll(component);
            rebuildRoleCache(newId, world);
        }
    }

    private Set<String> bfs(World world, String start, Set<String> candidates) {
        Set<String> visited = new HashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.add(start); visited.add(start);
        int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            String[] parts = cur.split(",");
            int cx = Integer.parseInt(parts[0]);
            int cy = Integer.parseInt(parts[1]);
            int cz = Integer.parseInt(parts[2]);
            for (int[] d : dirs) {
                String nk = key(cx+d[0], cy+d[1], cz+d[2]);
                if (!visited.contains(nk) && candidates.contains(nk)) {
                    Block block = world.getBlock(
                        cx+d[0], cy+d[1], cz+d[2]);
                    if (block instanceof BlockNavalCable || block instanceof BlockNavalBase) {
                        visited.add(nk);
                        queue.add(nk);
                    }
                }
            }
        }
        return visited;
    }
}