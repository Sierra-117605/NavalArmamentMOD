package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalWeapon;

public class TEMk41VLS extends TENavalWeapon {
    public TEMk41VLS() { super(8); }
    @Override public int getGuiColumns() { return 8; }
    @Override public int getAmmoStackLimit() { return 1; }
    @Override public float getRotationSpeed() { return 0f; }
}