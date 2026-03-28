package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TESubVLSSSBN16 extends TENavalWeapon {
    public TESubVLSSSBN16() { super(1); }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
    @Override public int getAmmoStackLimit() { return 1; }
}