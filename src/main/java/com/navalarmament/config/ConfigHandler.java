package com.navalarmament.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static Configuration config;

    // 国別フラグ
    public static boolean enableUSN   = true;
    public static boolean enableJMSDF = false;
    public static boolean enableNATO  = false;
    public static boolean enableVMF   = false;
    public static boolean enablePLAN  = false;

    // 射程
    public static int rangeCIWS            = 1280;
    public static int rangeMainGun         = 5120;
    public static int rangeVLS             = 5120;
    public static int rangeAntiShipMissile = 20480;
    public static int rangeCruiseMissile   = 20480;
    public static int rangeSLBM            = 20480;
    public static int rangeRadar           = 5120;
    public static int rangeSonar           = 1280;

    // パフォーマンス
    public static int radarScanInterval    = 20;
    public static int maxForceLoadedChunks = 16;

    public static void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    public static void syncConfig() {
        config.load();

        // 国別パック
        enableUSN   = config.getBoolean("enableUSN",   "nations", true,  "Enable USN equipment");
        enableJMSDF = config.getBoolean("enableJMSDF", "nations", false, "Enable JMSDF equipment");
        enableNATO  = config.getBoolean("enableNATO",  "nations", false, "Enable NATO equipment");
        enableVMF   = config.getBoolean("enableVMF",   "nations", false, "Enable VMF equipment");
        enablePLAN  = config.getBoolean("enablePLAN",  "nations", false, "Enable PLAN equipment");

        // 射程
        rangeCIWS            = config.getInt("ciws",            "range", 1280,  0, 20480, "CIWS range");
        rangeMainGun         = config.getInt("mainGun",         "range", 5120,  0, 20480, "Main gun range");
        rangeVLS             = config.getInt("vls",             "range", 5120,  0, 20480, "VLS range");
        rangeAntiShipMissile = config.getInt("antiShipMissile", "range", 20480, 0, 20480, "Anti-ship missile range");
        rangeCruiseMissile   = config.getInt("cruiseMissile",   "range", 20480, 0, 20480, "Cruise missile range");
        rangeSLBM            = config.getInt("slbm",            "range", 20480, 0, 20480, "SLBM range");
        rangeRadar           = config.getInt("radar",           "range", 5120,  0, 20480, "Radar scan range");
        rangeSonar           = config.getInt("sonar",           "range", 1280,  0, 20480, "Sonar range");

        // パフォーマンス
        radarScanInterval    = config.getInt("radarScanInterval",    "performance", 20, 1, 100, "Radar scan interval");
        maxForceLoadedChunks = config.getInt("maxForceLoadedChunks", "performance", 16, 1, 256, "Max force loaded chunks");

        if (config.hasChanged()) config.save();
    }
}