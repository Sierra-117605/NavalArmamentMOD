package com.navalarmament.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityMissile extends EntityNavalProjectile {

    private int boostTicks = 10;
    private int ticksAlive = 0;
    private double targetX, targetY, targetZ;
    private boolean hasTarget = false;

    public EntityMissile(World world) { super(world); }

    public EntityMissile(World world, double x, double y, double z,
                          double vx, double vy, double vz,
                          float damage, float explosionRadius) {
        super(world, x, y, z, vx, vy, vz, damage, explosionRadius);
    }

    public void setTarget(double x, double y, double z) {
        targetX = x; targetY = y; targetZ = z;
        hasTarget = true;
    }

    @Override
    protected void applyTrajectory() {
        ticksAlive++;
        if (ticksAlive < boostTicks || !hasTarget) return;

        // ターゲットエンティティ追跡
        if (targetEntityId >= 0) {
            Entity e = worldObj.getEntityByID(targetEntityId);
            if (e != null && !e.isDead) {
                targetX = e.posX; targetY = e.posY; targetZ = e.posZ;
            }
        }

        double dx = targetX - posX;
        double dy = targetY - posY;
        double dz = targetZ - posZ;
        double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
        if (dist < 1.0) return;

        float str = ticksAlive < 30 ? 0.05f : 0.15f;
        motionX += (dx/dist * speed - motionX) * str;
        motionY += (dy/dist * speed - motionY) * str;
        motionZ += (dz/dist * speed - motionZ) * str;
    }

    @Override
    protected int getMaxLifeTicks() { return 1200; }
}