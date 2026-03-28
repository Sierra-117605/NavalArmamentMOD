package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TESubVLS8 extends TENavalWeapon {
    public TESubVLS8() { super(8); }
    @Override public int getGuiColumns() { return 8; }
    @Override public int getAmmoStackLimit() { return 1; }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
}