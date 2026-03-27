package com.navalarmament.init;

import com.navalarmament.block.common.BlockNavalCable;
import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.block.usn.BlockSPY1Radar;
import com.navalarmament.tileentity.common.TENavalCable;
import com.navalarmament.tileentity.common.TENavalDummy;
import com.navalarmament.tileentity.usn.TESPY1Radar;
import cpw.mods.fml.common.registry.GameRegistry;

public class NavalBlocks {

    public static BlockNavalDummy NAVAL_DUMMY;
    public static BlockNavalCable NAVAL_CABLE;
    public static BlockSPY1Radar SPY1_RADAR;

    public static void register() {
        NAVAL_DUMMY = registerBlock(new BlockNavalDummy(), "naval_dummy");
        GameRegistry.registerTileEntity(TENavalDummy.class, "naval_dummy_te");

        NAVAL_CABLE = registerBlock(new BlockNavalCable(), "naval_cable");
        GameRegistry.registerTileEntity(TENavalCable.class, "naval_cable_te");

        SPY1_RADAR = registerBlock(new BlockSPY1Radar(), "spy1_radar");
        GameRegistry.registerTileEntity(TESPY1Radar.class, "spy1_radar_te");
    }

    private static <T extends net.minecraft.block.Block> T registerBlock(T block, String name) {
        GameRegistry.registerBlock(block, name);
        return block;
    }
}