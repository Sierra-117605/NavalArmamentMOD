package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWeapon extends Container {

    private final TENavalWeapon weapon;
    private final int weaponSlots;

    public ContainerWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        this.weapon = weapon;
        this.weaponSlots = Math.min(weapon.getAmmoInventory().getSizeInventory(), 8);

        // 弾薬スロット（Y=30）
        for (int i = 0; i < weaponSlots; i++) {
            addSlotToContainer(new Slot(weapon.getAmmoInventory(), i,
                8 + i * 18, 30));
        }

        // プレイヤーインベントリ（Y=68から3行）
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInv,
                    col + row * 9 + 9,
                    8 + col * 18, 68 + row * 18));
            }
        }

        // ホットバー（Y=126）
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInv, col,
                8 + col * 18, 126));
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
            if (index < weaponSlots) {
                if (!mergeItemStack(stack, weaponSlots, inventorySlots.size(), true))
                    return null;
            } else {
                if (!mergeItemStack(stack, 0, weaponSlots, false))
                    return null;
            }
            if (stack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();
        }
        return result;
    }
}