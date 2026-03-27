package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWeapon extends Container {

    private final TENavalWeapon weapon;
    private final int weaponSlots = 2;

    public ContainerWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        this.weapon = weapon;

        // 弾薬スロット2つ（Y=22）
        for (int i = 0; i < weaponSlots; i++) {
            addSlotToContainer(new SlotNavalAmmo(weapon,
                weapon.getAmmoInventory(), i, 8 + i * 18, 22));
        }

        // プレイヤーインベントリ3行（Y=78から）
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInv,
                    col + row * 9 + 9, 8 + col * 18, 78 + row * 18));
            }
        }

        // ホットバー（Y=136）
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInv, col, 8 + col * 18, 136));
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
                if (!mergeItemStack(stack, weaponSlots, inventorySlots.size(), true)) return null;
            } else {
                if (!mergeItemStack(stack, 0, weaponSlots, false)) return null;
            }
            if (stack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();
        }
        return result;
    }
}