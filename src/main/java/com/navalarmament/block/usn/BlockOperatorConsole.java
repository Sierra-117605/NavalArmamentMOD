package com.navalarmament.block.usn;

import com.navalarmament.NavalArmamentMod;
import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.gui.GuiHandler;
import com.navalarmament.tileentity.usn.TEOperatorConsole;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOperatorConsole extends BlockNavalBase {
    public BlockOperatorConsole() {
        super(Material.iron);
        setBlockName("operator_console");
        setHardness(2.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) { return new TEOperatorConsole(); }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(NavalArmamentMod.instance, GuiHandler.GUI_CIC, world, x, y, z);
        }
        return true;
    }

    @Override public String getNationId() { return "usn"; }
}