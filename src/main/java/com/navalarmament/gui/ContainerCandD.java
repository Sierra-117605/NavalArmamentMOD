package com.navalarmament.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCandD extends Container {

    public ContainerCandD(InventoryPlayer playerInventory) {
        // Hotbar slots required so NetHandlerPlayServer.processPlayerBlockPlacement
        // can resolve the held-item slot without NPE (line 657 slot.slotNumber).
        // Positioned off-screen since this is a display-only GUI.
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, -10000, -10000));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return true; }
}
