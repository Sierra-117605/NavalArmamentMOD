package com.navalarmament.tileentity.usn;

import com.navalarmament.tileentity.base.TENavalSensor;

public class TESQS53Sonar extends TENavalSensor {
    public TESQS53Sonar() { super(20); }
    @Override public int getScanRange() { return 1280; }
}