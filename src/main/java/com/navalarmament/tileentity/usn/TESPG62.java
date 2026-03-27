package com.navalarmament.tileentity.usn;
import com.navalarmament.tileentity.base.TENavalSensor;
public class TESPG62 extends TENavalSensor {
    public TESPG62() { super(20); }
    @Override public int getScanRange() { return 5120; }
}