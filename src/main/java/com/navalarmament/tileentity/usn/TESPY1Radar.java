package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalSensor;

public class TESPY1Radar extends TENavalSensor {
    public TESPY1Radar() { super(20); }
    @Override public int getScanRange() { return 256; }
}