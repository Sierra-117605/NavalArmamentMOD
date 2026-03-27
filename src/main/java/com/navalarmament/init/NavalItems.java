package com.navalarmament.init;

import com.navalarmament.item.base.ItemNavalAmmo;
import com.navalarmament.item.usn.ItemShell5Inch;
import com.navalarmament.item.usn.ItemShell5InchAP;
import com.navalarmament.item.usn.ItemShell25mm;
import com.navalarmament.item.usn.ItemShell20mm;
import com.navalarmament.item.usn.ItemSM2;
import com.navalarmament.item.usn.ItemESSM;
import com.navalarmament.item.usn.ItemHarpoon;
import com.navalarmament.item.usn.ItemTomahawk;
import com.navalarmament.item.usn.ItemMk46Torpedo;
import com.navalarmament.item.usn.ItemMk48Torpedo;
import com.navalarmament.item.usn.ItemSubSSM;
import com.navalarmament.item.usn.ItemSubCruise;
import com.navalarmament.item.usn.ItemSLBM;
import cpw.mods.fml.common.registry.GameRegistry;

public class NavalItems {

    public static ItemShell5Inch   SHELL_5INCH;
    public static ItemShell5InchAP SHELL_5INCH_AP;
    public static ItemShell25mm    SHELL_25MM;
    public static ItemShell20mm    SHELL_20MM;
    public static ItemSM2          SM2;
    public static ItemESSM         ESSM;
    public static ItemHarpoon      HARPOON;
    public static ItemTomahawk     TOMAHAWK;
    public static ItemMk46Torpedo  MK46;
    public static ItemMk48Torpedo  MK48;
    public static ItemSubSSM       SUB_SSM;
    public static ItemSubCruise    SUB_CRUISE;
    public static ItemSLBM         SLBM;

    public static void register() {
        SHELL_5INCH    = reg(new ItemShell5Inch(),    "shell_5inch");
        SHELL_5INCH_AP = reg(new ItemShell5InchAP(),  "shell_5inch_ap");
        SHELL_25MM     = reg(new ItemShell25mm(),      "shell_25mm");
        SHELL_20MM     = reg(new ItemShell20mm(),      "shell_20mm");
        SM2            = reg(new ItemSM2(),            "sm2");
        ESSM           = reg(new ItemESSM(),           "essm");
        HARPOON        = reg(new ItemHarpoon(),        "harpoon");
        TOMAHAWK       = reg(new ItemTomahawk(),       "tomahawk");
        MK46           = reg(new ItemMk46Torpedo(),    "mk46");
        MK48           = reg(new ItemMk48Torpedo(),    "mk48");
        SUB_SSM        = reg(new ItemSubSSM(),         "sub_ssm");
        SUB_CRUISE     = reg(new ItemSubCruise(),      "sub_cruise");
        SLBM           = reg(new ItemSLBM(),           "slbm");
    }

    private static <T extends ItemNavalAmmo> T reg(T item, String name) {
        GameRegistry.registerItem(item, name);
        return item;
    }
}