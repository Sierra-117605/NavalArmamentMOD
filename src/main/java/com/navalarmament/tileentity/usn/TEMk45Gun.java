package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalWeapon;

public class TEMk45Gun extends TENavalWeapon {

    public TEMk45Gun() {
        super(64);
    }

    @Override
    public float getRotationSpeed() {
        return 3.0f;
    }
}