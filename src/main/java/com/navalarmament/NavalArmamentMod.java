package com.navalarmament;

import com.navalarmament.config.ConfigHandler;
import com.navalarmament.init.NavalBlocks;
import com.navalarmament.init.NavalEntities;
import com.navalarmament.init.NavalItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = NavalArmamentMod.MODID, name = NavalArmamentMod.NAME, version = NavalArmamentMod.VERSION)
public class NavalArmamentMod {

    public static final String MODID   = "navalarmament";
    public static final String NAME    = "Naval Armament MOD";
    public static final String VERSION = "1.0.0";

    @Instance(MODID)
    public static NavalArmamentMod instance;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Naval Armament MOD - PreInit");
        ConfigHandler.preInit(event);
        NavalBlocks.register();
        NavalItems.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Naval Armament MOD - Init");
        NavalEntities.register();
    }
}