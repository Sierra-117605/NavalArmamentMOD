package com.navalarmament.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerOperatorConsole extends Container {

    public ContainerOperatorConsole(InventoryPlayer playerInventory) {
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, -10000, -10000));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return true; }
}
