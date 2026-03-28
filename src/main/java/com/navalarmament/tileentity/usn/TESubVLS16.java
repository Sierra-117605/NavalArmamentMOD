package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalWeapon;
public class TESubVLS16 extends TENavalWeapon {
    public TESubVLS16() { super(16); }
    @Override public boolean canAutoReload() { return false; }
    @Override public float getRotationSpeed() { return 0f; }
}