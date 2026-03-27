package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalWeapon;

public class TEHarpoonLauncher extends TENavalWeapon {
    public TEHarpoonLauncher() { super(4); }
    @Override public float getRotationSpeed() { return 2.0f; }
}