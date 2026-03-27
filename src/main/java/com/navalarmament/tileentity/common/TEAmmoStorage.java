package com.navalarmament.tileentity.common;

import com.navalarmament.item.base.INavalAmmo;
import com.navalarmament.tileentity.base.TENavalBase;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TEAmmoStorage extends TENavalBase implements IInventory {

    private final InventoryBasic inventory = new InventoryBasic("ammo_storage", false, 54);

    public TEAmmoStorage() { super(20); }

    @Override
    protected void onServerTick() {
        // 接続した武装に弾薬を補充
        for (String conn : connections) {
            String[] parts = conn.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TENavalWeapon) {
                supplyWeapon((TENavalWeapon) te);
            }
        }
    }

    private void supplyWeapon(TENavalWeapon weapon) {
        // 武装のスロットが空または残弾が少ない場合に補充
        for (int ws = 0; ws < weapon.getAmmoInventory().getSizeInventory(); ws++) {
            ItemStack weaponStack = weapon.getAmmoInventory().getStackInSlot(ws);
            // 武装スロットが満杯なら次へ
            if (weaponStack != null && weaponStack.stackSize >= weaponStack.getMaxStackSize()) continue;
            // 倉庫から互換性のある弾薬を探す
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
                // 補充処理
                if (weaponStack == null) {
                    weapon.getAmmoInventory().setInventorySlotContents(ws, storageStack.splitStack(1));
                } else if (weaponStack.isItemEqual(storageStack)) {
                    int transfer = Math.min(
                        storageStack.stackSize,
                        weaponStack.getMaxStackSize() - weaponStack.stackSize);
                    weaponStack.stackSize += transfer;
                    storageStack.stackSize -= transfer;
                    if (storageStack.stackSize <= 0)
                        inventory.setInventorySlotContents(ss, null);
                }
                markDirty();
                break;
            }
        }
    }

    // IInventory実装
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