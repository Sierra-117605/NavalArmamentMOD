package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TESubVLSSSBN8 extends TENavalWeapon {
    public TESubVLSSSBN8() { super(8); }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
    @Override public int getAmmoStackLimit() { return 1; }
}