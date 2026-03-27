package com.navalarmament.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityTorpedo extends EntityNavalProjectile {

    private int seekCooldown = 0;

    public EntityTorpedo(World world) { super(world); }

    public EntityTorpedo(World world, double x, double y, double z,
                          double vx, double vy, double vz,
                          float damage, float explosionRadius) {
        super(world, x, y, z, vx, vy, vz, damage, explosionRadius);
    }

    @Override
    protected void applyTrajectory() {
        if (!isInWater()) {
            motionY -= 0.1f;
            return;
        }
        if (--seekCooldown <= 0) {
            seekTarget();
            seekCooldown = 5;
        }
        if (targetEntityId >= 0) {
            Entity target = worldObj.getEntityByID(targetEntityId);
            if (target != null && !target.isDead) {
                double dx = target.posX - posX;
                double dy = target.posY - posY;
                double dz = target.posZ - posZ;
                double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
                motionX += (dx/dist * speed - motionX) * 0.1f;
                motionY += (dy/dist * speed - motionY) * 0.1f;
                motionZ += (dz/dist * speed - motionZ) * 0.1f;
            }
        }
    }

    private void seekTarget() {
        java.util.List entities = worldObj.getEntitiesWithinAABBExcludingEntity(
            this, boundingBox.expand(128, 128, 128));
        for (Object obj : entities) {
            Entity e = (Entity) obj;
            if (e == null || e.isDead || !e.isInWater()) continue;
            targetEntityId = e.getEntityId();
            return;
        }
    }

    @Override
    protected int getMaxLifeTicks() { return 600; }
}