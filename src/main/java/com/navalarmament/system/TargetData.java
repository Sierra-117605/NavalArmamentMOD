package com.navalarmament.system;

import net.minecraft.entity.Entity;

public class TargetData {
    public final Entity entity;
    public final double x, y, z;
    public final double distance;
    public final TargetType targetType;
    public final long detectedAt;
    public final boolean isAlly = false; // 将来の多国籍対応用
    public int assignedWeaponX, assignedWeaponY, assignedWeaponZ;
    public boolean assigned;

    public TargetData(Entity entity, double originX, double originY, double originZ) {
        this.entity     = entity;
        this.x          = entity.posX;
        this.y          = entity.posY;
        this.z          = entity.posZ;
        double dx = x - originX, dy = y - originY, dz = z - originZ;
        this.distance   = Math.sqrt(dx*dx + dy*dy + dz*dz);
        this.targetType = detectType(entity);
        this.detectedAt = System.currentTimeMillis();
        this.assigned   = false;
    }

    public static TargetType detectType(Entity entity) {
        if (entity.isInWater()) return TargetType.SUBSURFACE;
        if (!entity.onGround && entity.posY > 64) return TargetType.AIR;
        return TargetType.SURFACE;
    }
}