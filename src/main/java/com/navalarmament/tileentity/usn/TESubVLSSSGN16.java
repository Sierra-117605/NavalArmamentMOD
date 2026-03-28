package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TESubVLSSSGN16 extends TENavalWeapon {
    public TESubVLSSSGN16() { super(16); }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
    @Override public int getAmmoStackLimit() { return 7; }
}