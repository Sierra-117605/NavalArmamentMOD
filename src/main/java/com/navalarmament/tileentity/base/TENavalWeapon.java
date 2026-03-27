package com.navalarmament.tileentity.base;

import com.navalarmament.item.base.INavalAmmo;
import com.navalarmament.system.TargetData;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TENavalWeapon extends TENavalBase {

    protected InventoryBasic ammoInventory;
    protected int engagementMode = 0;
    public float currentYaw   = 0f;
    public float currentPitch = 0f;
    protected float targetYaw   = 0f;
    protected float targetPitch = 0f;
    protected TargetData currentTarget = null;

    public TENavalWeapon(int slots) {
        super(20);
        this.ammoInventory = new InventoryBasic("ammo", false, slots);
    }

    @Override
    public void updateEntity() {
        rotateTick();
        super.updateEntity();
    }

    @Override
    protected void onServerTick() {
        if (engagementMode == 2 && currentTarget != null) {
            aimAtTarget();
            if (isAimed() && consumeAmmo()) {
                fireAtTarget();
                syncToClient();
            }
        }
    }

    private void aimAtTarget() {
        double dx = currentTarget.posX - xCoord;
        double dy = currentTarget.posY - yCoord;
        double dz = currentTarget.posZ - zCoord;
        double dist = Math.sqrt(dx*dx + dz*dz);
        targetYaw   = (float)(Math.toDegrees(Math.atan2(dz, dx)));
        targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
    }

    private boolean isAimed() {
        float dyaw = Math.abs(currentYaw - targetYaw);
        float dpitch = Math.abs(currentPitch - targetPitch);
        return dyaw < 3.0f && dpitch < 3.0f;
    }

    private void fireAtTarget() {
        INavalAmmo ammo = getLoadedAmmoStats();
        if (ammo == null) return;
        // Phase 6でEntityを生成。現状はログ出力のみ
        com.navalarmament.NavalArmamentMod.logger.info(
            "FIRE at " + currentTarget.posX + "," + currentTarget.posY + "," + currentTarget.posZ);
    }

    protected void rotateTick() {
        float dyaw = targetYaw - currentYaw;
        float dpitch = targetPitch - currentPitch;
        if (dyaw >  180) dyaw -= 360;
        if (dyaw < -180) dyaw += 360;
        float speed = getRotationSpeed();
        currentYaw   += Math.signum(dyaw)   * Math.min(Math.abs(dyaw),   speed);
        currentPitch += Math.signum(dpitch) * Math.min(Math.abs(dpitch), speed);
    }

    public INavalAmmo getLoadedAmmoStats() {
        ItemStack stack = ammoInventory.getStackInSlot(0);
        if (stack == null || !(stack.getItem() instanceof INavalAmmo)) return null;
        return (INavalAmmo) stack.getItem();
    }

    public int getCurrentRange() {
        INavalAmmo ammo = getLoadedAmmoStats();
        return ammo != null ? ammo.getRange() : 0;
    }

    public boolean consumeAmmo() {
        ItemStack stack = ammoInventory.getStackInSlot(0);
        if (stack == null || stack.stackSize <= 0) return false;
        stack.stackSize--;
        if (stack.stackSize == 0) ammoInventory.setInventorySlotContents(0, null);
        return true;
    }

    public int getAmmoCount() {
        ItemStack stack = ammoInventory.getStackInSlot(0);
        return stack != null ? stack.stackSize : 0;
    }

    public boolean acceptsAmmoType(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof INavalAmmo)) return false;
        INavalAmmo ammo = (INavalAmmo) stack.getItem();
        for (Class<?> cls : ammo.getCompatibleWeapons()) {
            if (cls.isInstance(this)) return true;
        }
        return false;
    }

    public void setCurrentTarget(TargetData td) { this.currentTarget = td; }
    public TargetData getCurrentTarget() { return currentTarget; }
    public int getEngagementMode() { return engagementMode; }
    public void setEngagementMode(int mode) { this.engagementMode = mode; markDirty(); }
    public InventoryBasic getAmmoInventory() { return ammoInventory; }

    public abstract float getRotationSpeed();

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("engagementMode", engagementMode);
        nbt.setFloat("currentYaw",   currentYaw);
        nbt.setFloat("currentPitch", currentPitch);
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("slot", (byte)i);
                s.writeToNBT(tag);
                items.appendTag(tag);
            }
        }
        nbt.setTag("ammo", items);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        engagementMode = nbt.getInteger("engagementMode");
        currentYaw     = nbt.getFloat("currentYaw");
        currentPitch   = nbt.getFloat("currentPitch");
        NBTTagList items = nbt.getTagList("ammo", 10);
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound tag = items.getCompoundTagAt(i);
            int slot = tag.getByte("slot");
            ammoInventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
        }
    }
}