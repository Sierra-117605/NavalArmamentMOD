package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TEMk41VLS64 extends TENavalWeapon {
    public TEMk41VLS64() { super(64); }
    @Override public int getGuiColumns() { return 8; }
    @Override public int getAmmoStackLimit() { return 1; }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
}