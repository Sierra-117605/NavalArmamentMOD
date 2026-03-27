package com.navalarmament.tileentity.common;

import com.navalarmament.item.base.INavalAmmo;
import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.Set;
import java.util.UUID;

public class TEAmmoStorage extends TENavalBase implements IInventory {

    private final InventoryBasic inventory = new InventoryBasic("ammo_storage", false, 54);

    public TEAmmoStorage() { super(20); }

    @Override
    public boolean canUpdate() { return true; }

    @Override
    protected void onServerTick() {
        UUID netId = CableNetwork.getInstance().getNetworkId(xCoord, yCoord, zCoord);
        if (netId == null) return;
        Set<String> members = CableNetwork.getInstance().getNetworkMembers(netId);
        if (members == null) return;
        for (String pos : members) {
            String[] parts = pos.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            if (x == xCoord && y == yCoord && z == zCoord) continue;
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TENavalWeapon) {
                supplyWeapon((TENavalWeapon) te);
            }
        }
    }

    private void supplyWeapon(TENavalWeapon weapon) {
        for (int ws = 0; ws < weapon.getAmmoInventory().getSizeInventory(); ws++) {
            ItemStack weaponStack = weapon.getAmmoInventory().getStackInSlot(ws);
            if (weaponStack != null && weaponStack.stackSize >= weaponStack.getMaxStackSize()) continue;
            for (int ss = 0; ss < inventory.getSizeInventory(); ss++) {
                ItemStack storageStack = inventory.getStackInSlot(ss);
                if (storageStack == null) continue;
                if (!(storageStack.getItem() instanceof INavalAmmo)) continue;
                INavalAmmo ammo = (INavalAmmo) storageStack.getItem();
                boolean compatible = false;
                for (Class<?> cls : ammo.getCompatibleWeapons()) {
                    if (cls.isInstance(weapon)) { compatible = true; break; }
                }
                if (!compatible) continue;
                if (weaponStack == null) {
                    int transfer = Math.min(storageStack.stackSize, 64);
                    ItemStack newStack = storageStack.copy();
                    newStack.stackSize = transfer;
                    weapon.getAmmoInventory().setInventorySlotContents(ws, newStack);
                    storageStack.stackSize -= transfer;
                    if (storageStack.stackSize <= 0) inventory.setInventorySlotContents(ss, null);
                } else if (weaponStack.isItemEqual(storageStack)) {
                    int space = weaponStack.getMaxStackSize() - weaponStack.stackSize;
                    if (space <= 0) break;
                    int transfer = Math.min(storageStack.stackSize, space);
                    weaponStack.stackSize += transfer;
                    storageStack.stackSize -= transfer;
                    if (storageStack.stackSize <= 0) inventory.setInventorySlotContents(ss, null);
                } else continue;
                markDirty();
                break;
            }
        }
    }

    @Override public int getSizeInventory() { return inventory.getSizeInventory(); }
    @Override public ItemStack getStackInSlot(int i) { return inventory.getStackInSlot(i); }
    @Override public ItemStack decrStackSize(int i, int n) { return inventory.decrStackSize(i, n); }
    @Override public ItemStack getStackInSlotOnClosing(int i) { return inventory.getStackInSlotOnClosing(i); }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory.setInventorySlotContents(i, s); }
    @Override public String getInventoryName() { return "Ammo Storage"; }
    @Override public boolean hasCustomInventoryName() { return false; }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUseableByPlayer(net.minecraft.entity.player.EntityPlayer p) { return true; }
    @Override public void openInventory() {}
    @Override public void closeInventory() {}
    @Override public boolean isItemValidForSlot(int i, ItemStack s) {
        return s != null && s.getItem() instanceof INavalAmmo;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack s = inventory.getStackInSlot(i);
            if (s != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("slot", (byte)i);
                s.writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        nbt.setTag("items", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("items", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            inventory.setInventorySlotContents(tag.getByte("slot"),
                ItemStack.loadItemStackFromNBT(tag));
        }
    }
}