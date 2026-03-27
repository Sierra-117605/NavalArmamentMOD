package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalSensor;

public class TEPS67Radar extends TENavalSensor {
    public TEPS67Radar() { super(20); }
    @Override public int getScanRange() { return 5120; }
}