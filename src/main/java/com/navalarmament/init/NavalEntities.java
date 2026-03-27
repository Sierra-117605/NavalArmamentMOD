package com.navalarmament.init;

import com.navalarmament.entity.EntityMissile;
import com.navalarmament.entity.EntityShell;
import com.navalarmament.entity.EntityTorpedo;
import cpw.mods.fml.common.registry.EntityRegistry;
import com.navalarmament.NavalArmamentMod;

public class NavalEntities {

    public static void register() {
        EntityRegistry.registerModEntity(
            EntityShell.class,   "naval_shell",   1,
            NavalArmamentMod.instance, 64, 5, true);
        EntityRegistry.registerModEntity(
            EntityMissile.class, "naval_missile", 2,
            NavalArmamentMod.instance, 128, 3, true);
        EntityRegistry.registerModEntity(
            EntityTorpedo.class, "naval_torpedo", 3,
            NavalArmamentMod.instance, 64, 5, true);
    }
}