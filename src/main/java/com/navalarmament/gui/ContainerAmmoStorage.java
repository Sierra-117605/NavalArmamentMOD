package com.navalarmament.gui;

import com.navalarmament.tileentity.common.TEAmmoStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAmmoStorage extends Container {

    private final TEAmmoStorage storage;
    private static final int STORAGE_SLOTS = 54;

    public ContainerAmmoStorage(InventoryPlayer playerInv, TEAmmoStorage storage) {
        this.storage = storage;

        // 弾薬庫スロット（6行×9列）
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(storage, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // プレイヤーインベントリ
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInv,
                    col + row * 9 + 9, 8 + col * 18, 138 + row * 18));
            }
        }

        // ホットバー
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInv, col, 8 + col * 18, 196));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return true; }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack result = null;
        Slot slot = (Slot) inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            result = stack.copy();
            if (index < STORAGE_SLOTS) {
                if (!mergeItemStack(stack, STORAGE_SLOTS, inventorySlots.size(), true)) return null;
            } else {
                if (!mergeItemStack(stack, 0, STORAGE_SLOTS, false)) return null;
            }
            if (stack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();
        }
        return result;
    }
}