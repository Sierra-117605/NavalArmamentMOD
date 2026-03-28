package com.navalarmament.tileentity.base;

import com.navalarmament.entity.EntityMissile;
import com.navalarmament.entity.EntityShell;
import com.navalarmament.entity.EntityTorpedo;
import com.navalarmament.item.base.INavalAmmo;
import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.system.TargetData;
import com.navalarmament.system.TargetType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TENavalWeapon extends TENavalBase {

    protected final InventoryBasic ammoInventory;
    protected int engagementMode = 0; // 0=MANUAL 1=SEMI 2=AUTO
    protected TargetData currentTarget;
    protected float yaw = 0f, pitch = 0f;

    // CICからの優先弾種オーバーライド
    private String preferredAmmoClass = "";
    // CICからの優先目標種別オーバーライド（空文字=自動）
    private String preferredTargetType = "";

    public TENavalWeapon(int ammoSlots) {
        super(20);
        this.ammoInventory = new InventoryBasic("weapon", false, ammoSlots);
    }

    public int getAmmoStackLimit() { return 64; }
    public abstract float getRotationSpeed();
    public IInventory getAmmoInventory() { return ammoInventory; }
    public int getEngagementMode() { return engagementMode; }
    public void setEngagementMode(int mode) { engagementMode = mode; markDirty(); }
    public String getPreferredAmmoClass() { return preferredAmmoClass; }
    public void setPreferredAmmoClass(String cls) { preferredAmmoClass = cls; markDirty(); }
    public String getPreferredTargetType() { return preferredTargetType; }
    public void setPreferredTargetType(String t) { preferredTargetType = t; markDirty(); }

    public void setCurrentTarget(TargetData target) { assignTarget(target); }

    public int getAmmoCount() {
        int count = 0;
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s != null) count += s.stackSize;
        }
        return count;
    }

    public int getCurrentRange() {
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s != null && s.getItem() instanceof INavalAmmo)
                return ((INavalAmmo) s.getItem()).getRange();
        }
        return 0;
    }

    // 自動装填可能かどうか（サブクラスでオーバーライド可）
    public boolean canAutoReload() { return true; }

    @Override
    protected void onServerTick() {
        if (engagementMode == 0 || currentTarget == null) return;
        if (currentTarget.entity.isDead) { currentTarget = null; return; }
        rotateToTarget();
        if (isAimed()) fire();
    }

    public void assignTarget(TargetData target) {
        if (engagementMode > 0) this.currentTarget = target;
    }

    // 目標種別に応じた弾薬をスロットから選択
    public ItemStack selectAmmoForTarget(TargetType targetType) {
        // CICオーバーライドがあればそちらを優先
        if (!preferredAmmoClass.isEmpty()) {
            for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
                ItemStack s = ammoInventory.getStackInSlot(i);
                if (s != null && s.getItem().getClass().getName().equals(preferredAmmoClass))
                    return s;
            }
        }
        // 目標種別に対応した弾薬を自動選択
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s == null) continue;
            if (!(s.getItem() instanceof INavalAmmo)) continue;
            INavalAmmo ammo = (INavalAmmo) s.getItem();
            if (ammo.getEffectiveTargetTypes().contains(targetType)) return s;
        }
        // 対応弾薬なければ最初のスロットの弾薬を返す
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s != null) return s;
        }
        return null;
    }

    private void rotateToTarget() {
        double dx = currentTarget.x - xCoord;
        double dy = currentTarget.y - yCoord;
        double dz = currentTarget.z - zCoord;
        float targetYaw   = (float)(Math.atan2(dz, dx) * 180 / Math.PI);
        float targetPitch = (float)(Math.atan2(dy, Math.sqrt(dx*dx+dz*dz)) * 180 / Math.PI);
        float speed = getRotationSpeed();
        yaw   = approach(yaw,   targetYaw,   speed);
        pitch = approach(pitch, targetPitch, speed);
    }

    private float approach(float current, float target, float speed) {
        float diff = target - current;
        while (diff > 180) diff -= 360;
        while (diff < -180) diff += 360;
        if (Math.abs(diff) <= speed) return target;
        return current + Math.signum(diff) * speed;
    }

    private boolean isAimed() {
        double dx = currentTarget.x - xCoord;
        double dy = currentTarget.y - yCoord;
        double dz = currentTarget.z - zCoord;
        float targetYaw   = (float)(Math.atan2(dz, dx) * 180 / Math.PI);
        float targetPitch = (float)(Math.atan2(dy, Math.sqrt(dx*dx+dz*dz)) * 180 / Math.PI);
        return Math.abs(yaw - targetYaw) < 3f && Math.abs(pitch - targetPitch) < 3f;
    }

    private void fire() {
        TargetType type = currentTarget.targetType;
        // CICで目標種別オーバーライドがあれば使用
        if (!preferredTargetType.isEmpty()) {
            try { type = TargetType.valueOf(preferredTargetType); } catch (Exception e) {}
        }
        ItemStack ammoStack = selectAmmoForTarget(type);
        if (ammoStack == null) return;
        INavalAmmo ammo = (INavalAmmo) ammoStack.getItem();

        double dx = currentTarget.x - xCoord;
        double dy = currentTarget.y - yCoord;
        double dz = currentTarget.z - zCoord;
        double dist = Math.sqrt(dx*dx+dy*dy+dz*dz);
        if (dist > ammo.getRange()) return;

        double vx = dx/dist * ammo.getSpeed();
        double vy = dy/dist * ammo.getSpeed();
        double vz = dz/dist * ammo.getSpeed();

        Entity projectile;
        if (type == TargetType.SUBSURFACE) {
            projectile = new EntityTorpedo(worldObj, xCoord, yCoord, zCoord,
                vx, vy, vz, ammo.getDamage(), ammo.getExplosionRadius());
        } else if (ammo instanceof ItemNavalAmmo && ammo.getRange() > 1280) {
            projectile = new EntityMissile(worldObj, xCoord, yCoord, zCoord,
                vx, vy, vz, ammo.getDamage(), ammo.getExplosionRadius(),
                currentTarget.entity);
        } else {
            projectile = new EntityShell(worldObj, xCoord, yCoord, zCoord,
                vx, vy, vz, ammo.getDamage(), ammo.getExplosionRadius());
        }
        worldObj.spawnEntityInWorld(projectile);

        // 消費（VLS等の自動装填なし武装はそのまま減らす）
        ammoStack.stackSize--;
        if (ammoStack.stackSize <= 0) {
            for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
                if (ammoInventory.getStackInSlot(i) == ammoStack) {
                    ammoInventory.setInventorySlotContents(i, null);
                    break;
                }
            }
        }
        markDirty();
        currentTarget = null;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("mode", engagementMode);
        nbt.setFloat("yaw", yaw);
        nbt.setFloat("pitch", pitch);
        nbt.setString("preferredAmmo", preferredAmmoClass);
        nbt.setString("preferredTargetType", preferredTargetType);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < ammoInventory.getSizeInventory(); i++) {
            ItemStack s = ammoInventory.getStackInSlot(i);
            if (s != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("slot", (byte)i);
                s.writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        nbt.setTag("ammo", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        engagementMode       = nbt.getInteger("mode");
        yaw                  = nbt.getFloat("yaw");
        pitch                = nbt.getFloat("pitch");
        preferredAmmoClass   = nbt.getString("preferredAmmo");
        preferredTargetType  = nbt.getString("preferredTargetType");
        NBTTagList list = nbt.getTagList("ammo", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            ammoInventory.setInventorySlotContents(
                tag.getByte("slot"), ItemStack.loadItemStackFromNBT(tag));
        }
    }
}