package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import com.navalarmament.tileentity.common.TEAmmoStorage;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_WEAPON       = 0;
    public static final int GUI_AMMO_STORAGE = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player,
                                       World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (id == GUI_WEAPON && te instanceof TENavalWeapon)
            return new ContainerWeapon(player.inventory, (TENavalWeapon) te);
        if (id == GUI_AMMO_STORAGE && te instanceof TEAmmoStorage)
            return new ContainerAmmoStorage(player.inventory, (TEAmmoStorage) te);
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player,
                                       World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (id == GUI_WEAPON && te instanceof TENavalWeapon)
            return new GuiWeapon(player.inventory, (TENavalWeapon) te);
        if (id == GUI_AMMO_STORAGE && te instanceof TEAmmoStorage)
            return new GuiAmmoStorage(player.inventory, (TEAmmoStorage) te);
        return null;
    }
}