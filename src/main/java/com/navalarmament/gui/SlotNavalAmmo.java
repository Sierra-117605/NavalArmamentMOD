package com.navalarmament.gui;

import com.navalarmament.item.base.INavalAmmo;
import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotNavalAmmo extends Slot {

    private final TENavalWeapon weapon;

    public SlotNavalAmmo(TENavalWeapon weapon, IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
        this.weapon = weapon;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null) return false;
        if (!(stack.getItem() instanceof INavalAmmo)) return false;
        INavalAmmo ammo = (INavalAmmo) stack.getItem();
        for (Class<?> cls : ammo.getCompatibleWeapons()) {
            if (cls.isInstance(weapon)) return true;
        }
        return false;
    }
}