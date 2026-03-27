package com.navalarmament.system;

public class TargetData {
    public int entityId;
    public double posX, posY, posZ;
    public double motionX, motionY, motionZ;
    public double distance;
    public int threatLevel; // 0=GREEN 1=YELLOW 2=RED 3=BLACK
    public boolean isMcHeli;
    public boolean isAlly;
    public long detectedAt;

    public TargetData(int entityId, double x, double y, double z,
                      double mx, double my, double mz, double dist, long tick) {
        this.entityId  = entityId;
        this.posX = x; this.posY = y; this.posZ = z;
        this.motionX = mx; this.motionY = my; this.motionZ = mz;
        this.distance  = dist;
        this.detectedAt= tick;
        this.threatLevel = 1;
    }
}