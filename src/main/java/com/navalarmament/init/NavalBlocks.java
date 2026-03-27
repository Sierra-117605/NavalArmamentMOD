package com.navalarmament.init;

import com.navalarmament.block.common.BlockNavalDummy;
import com.navalarmament.tileentity.common.TENavalDummy;
import cpw.mods.fml.common.registry.GameRegistry;

public class NavalBlocks {

    // ブロックのインスタンス
    public static BlockNavalDummy NAVAL_DUMMY;

    // ブロック登録（PreInitで呼ぶ）
    public static void register() {
        NAVAL_DUMMY = registerBlock(new BlockNavalDummy(), "naval_dummy");
        GameRegistry.registerTileEntity(TENavalDummy.class, "naval_dummy_te");
    }

    private static <T extends net.minecraft.block.Block> T registerBlock(T block, String name) {
        GameRegistry.registerBlock(block, name);
        return block;
    }
}