package com.navalarmament.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerOperatorConsole extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer player) { return true; }
}