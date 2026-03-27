package com.navalarmament.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityNavalProjectile extends Entity {

    public float damage;
    public float explosionRadius;
    public float speed;
    public int shooterX, shooterY, shooterZ;
    public int targetEntityId = -1;
    private int ticksAlive = 0;

    public EntityNavalProjectile(World world) {
        super(world);
        setSize(0.5f, 0.5f);
    }

    public EntityNavalProjectile(World world, double x, double y, double z,
                                  double vx, double vy, double vz,
                                  float damage, float explosionRadius) {
        this(world);
        setPosition(x, y, z);
        motionX = vx;
        motionY = vy;
        motionZ = vz;
        this.damage = damage;
        this.explosionRadius = explosionRadius;
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        ticksAlive++;
        if (ticksAlive > getMaxLifeTicks()) {
            setDead();
            return;
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (!worldObj.isRemote) {
            applyTrajectory();

            // ブロックとの衝突チェック
            Vec3 from = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 to   = Vec3.createVectorHelper(
                posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition hit = worldObj.rayTraceBlocks(from, to);
            if (hit != null) {
                onImpact(hit);
                return;
            }

            // エンティティとの衝突
            if (targetEntityId >= 0) {
                Entity target = worldObj.getEntityByID(targetEntityId);
                if (target != null && !target.isDead) {
                    double dx = target.posX - posX;
                    double dy = target.posY - posY;
                    double dz = target.posZ - posZ;
                    if (dx*dx + dy*dy + dz*dz < 4.0) {
                        onHitEntity(target);
                        return;
                    }
                }
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        setPosition(posX, posY, posZ);
    }

    protected void onImpact(MovingObjectPosition hit) {
        if (explosionRadius > 0) {
            worldObj.createExplosion(null, posX, posY, posZ, explosionRadius, true);
        }
        setDead();
    }

    protected void onHitEntity(Entity target) {
        target.attackEntityFrom(DamageSource.generic, damage);
        if (explosionRadius > 0) {
            worldObj.createExplosion(null, posX, posY, posZ, explosionRadius, true);
        }
        setDead();
    }

    protected abstract void applyTrajectory();
    protected abstract int getMaxLifeTicks();

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setFloat("damage", damage);
        nbt.setFloat("explosionRadius", explosionRadius);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        damage = nbt.getFloat("damage");
        explosionRadius = nbt.getFloat("explosionRadius");
    }
}