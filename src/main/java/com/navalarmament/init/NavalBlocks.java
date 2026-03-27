package com.navalarmament.init;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.block.usn.BlockADS;
import com.navalarmament.block.usn.BlockAuxProcessor;
import com.navalarmament.block.usn.BlockCandD;
import com.navalarmament.block.usn.BlockDataLink;
import com.navalarmament.block.usn.BlockSPY1Radar;
import com.navalarmament.block.usn.BlockWCS;
import com.navalarmament.tileentity.common.TENavalCable;
import com.navalarmament.tileentity.common.TENavalDummy;
import com.navalarmament.tileentity.usn.TEADS;
import com.navalarmament.tileentity.usn.TEAuxProcessor;
import com.navalarmament.tileentity.usn.TECandD;
import com.navalarmament.tileentity.usn.TEDataLink;
import com.navalarmament.tileentity.usn.TESPY1Radar;
import com.navalarmament.tileentity.usn.TEWCS;
import cpw.mods.fml.common.registry.GameRegistry;

public class NavalBlocks {

    public static BlockNavalDummy NAVAL_DUMMY;
    public static BlockNavalCable NAVAL_CABLE;
    public static BlockSPY1Radar SPY1_RADAR;
    public static BlockCandD CANDD;
    public static BlockWCS WCS;
    public static BlockADS ADS;
    public static BlockDataLink DATALINK;
    public static BlockAuxProcessor AUX_PROCESSOR;

    public static void register() {
        NAVAL_DUMMY = registerBlock(new BlockNavalDummy(), "naval_dummy");
        GameRegistry.registerTileEntity(TENavalDummy.class, "naval_dummy_te");

        NAVAL_CABLE = registerBlock(new BlockNavalCable(), "naval_cable");
        GameRegistry.registerTileEntity(TENavalCable.class, "naval_cable_te");

        SPY1_RADAR = registerBlock(new BlockSPY1Radar(), "spy1_radar");
        GameRegistry.registerTileEntity(TESPY1Radar.class, "spy1_radar_te");

        CANDD = registerBlock(new BlockCandD(), "candd");
        GameRegistry.registerTileEntity(TECandD.class, "candd_te");

        WCS = registerBlock(new BlockWCS(), "wcs");
        GameRegistry.registerTileEntity(TEWCS.class, "wcs_te");

        ADS = registerBlock(new BlockADS(), "ads");
        GameRegistry.registerTileEntity(TEADS.class, "ads_te");

        DATALINK = registerBlock(new BlockDataLink(), "datalink");
        GameRegistry.registerTileEntity(TEDataLink.class, "datalink_te");

        AUX_PROCESSOR = registerBlock(new BlockAuxProcessor(), "aux_processor");
        GameRegistry.registerTileEntity(TEAuxProcessor.class, "aux_processor_te");
    }

    private static <T extends net.minecraft.block.Block> T registerBlock(T block, String name) {
        GameRegistry.registerBlock(block, name);
        return block;
    }
}