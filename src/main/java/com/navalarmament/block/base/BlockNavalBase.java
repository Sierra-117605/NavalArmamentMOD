package com.navalarmament.block.base;

import com.navalarmament.gui.GuiHandler;
import com.navalarmament.init.NavalCreativeTabs;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockNavalBase extends Block {

    public BlockNavalBase(Material material) {
        super(material);
        setCreativeTab(NavalCreativeTabs.NAVAL_TAB);
    }

    @Override
    public boolean hasTileEntity(int metadata) { return true; }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TENavalWeapon) {
            player.openGui(com.navalarmament.NavalArmamentMod.instance,
                GuiHandler.GUI_WEAPON, world, x, y, z);
            return true;
        }
        return false;
    }

    public String getNationId() { return "common"; }
}