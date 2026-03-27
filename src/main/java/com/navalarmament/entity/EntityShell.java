package com.navalarmament.entity;

import net.minecraft.world.World;

public class EntityShell extends EntityNavalProjectile {

    private static final float GRAVITY = 0.05f;

    public EntityShell(World world) { super(world); }

    public EntityShell(World world, double x, double y, double z,
                        double vx, double vy, double vz,
                        float damage, float explosionRadius) {
        super(world, x, y, z, vx, vy, vz, damage, explosionRadius);
    }

    @Override
    protected void applyTrajectory() {
        motionY -= GRAVITY;
        motionX *= 0.99f;
        motionZ *= 0.99f;
    }

    @Override
    protected int getMaxLifeTicks() { return 400; }
}