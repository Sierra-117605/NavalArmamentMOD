package com.navalarmament.block.common;

import com.navalarmament.block.base.BlockNavalBase;
import com.navalarmament.gui.GuiHandler;
import com.navalarmament.system.CableNetwork;
import com.navalarmament.tileentity.common.TEAmmoStorage;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAmmoStorage extends BlockNavalBase {

    public BlockAmmoStorage() {
        super(Material.iron);
        setBlockName("ammo_storage");
        setHardness(5.0F);
        setBlockTextureName("iron_block");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEAmmoStorage();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            CableNetwork.getInstance().onDeviceConnected(world, x, y, z);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int side, float hx, float hy, float hz) {
        if (world.isRemote) return true;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TEAmmoStorage) {
            player.openGui(com.navalarmament.NavalArmamentMod.instance,
                GuiHandler.GUI_AMMO_STORAGE, world, x, y, z);
            return true;
        }
        return false;
    }
}