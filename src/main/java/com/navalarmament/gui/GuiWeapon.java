package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import com.navalarmament.network.NavalPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiWeapon extends GuiContainer {

    private final TENavalWeapon weapon;
    private final int weaponSlots;

    private static final int BG      = 0xFFC6C6C6;
    private static final int SHADOW  = 0xFF555555;
    private static final int HILIGHT = 0xFFFFFFFF;
    private static final int SLOT_BG = 0xFF8B8B8B;

    private static final int AMMO_Y = 22;
    private static final int INV_Y  = 78;
    private static final int HOT_Y  = 136;

    public GuiWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        super(new ContainerWeapon(playerInv, weapon));
        this.weapon = weapon;
        this.weaponSlots = weapon.getAmmoInventory().getSizeInventory();
        xSize = 176;
        ySize = 162;
    }

    @Override
    public void initGui() {
        super.initGui();
        // モード切替ボタン
        buttonList.add(new GuiButton(0, guiLeft + xSize - 60, guiTop + 8, 54, 12, getModeStr()));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            int next = (weapon.getEngagementMode() + 1) % 3;
            weapon.setEngagementMode(next);
            NavalPacketHandler.sendModeChange(
                weapon.xCoord, weapon.yCoord, weapon.zCoord, next);
            button.displayString = getModeStr(next);
        }
    }

    private void drawPanel(int x, int y, int w, int h) {
        drawRect(x, y, x+w, y+h, BG);
        drawRect(x, y, x+w, y+1, HILIGHT);
        drawRect(x, y, x+1, y+h, HILIGHT);
        drawRect(x, y+h-1, x+w, y+h, SHADOW);
        drawRect(x+w-1, y, x+w, y+h, SHADOW);
    }

    private void drawSlotBg(int x, int y) {
        drawRect(x, y, x+18, y+18, SLOT_BG);
        drawRect(x, y, x+18, y+1, SHADOW);
        drawRect(x, y, x+1, y+18, SHADOW);
        drawRect(x, y+17, x+18, y+18, HILIGHT);
        drawRect(x+17, y, x+18, y+18, HILIGHT);
        drawRect(x+1, y+1, x+17, y+17, SLOT_BG);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        drawPanel(guiLeft, guiTop, xSize, ySize);
        drawRect(guiLeft+4, guiTop+68,  guiLeft+xSize-4, guiTop+69,  SHADOW);
        drawRect(guiLeft+4, guiTop+132, guiLeft+xSize-4, guiTop+133, SHADOW);

        for (int i = 0; i < weaponSlots && i < 9; i++)
            drawSlotBg(guiLeft+8+i*18, guiTop+AMMO_Y);
        for (int i = 9; i < weaponSlots && i < 18; i++)
            drawSlotBg(guiLeft+8+(i-9)*18, guiTop+AMMO_Y+18);

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                drawSlotBg(guiLeft+8+col*18, guiTop+INV_Y+row*18);

        for (int col = 0; col < 9; col++)
            drawSlotBg(guiLeft+8+col*18, guiTop+HOT_Y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        String name = weapon.getBlockType() != null
            ? weapon.getBlockType().getLocalizedName() : "Weapon";
        fontRendererObj.drawString(name, 8, 8, 0x404040);

        if (weaponSlots <= 2) {
            int ix = 8 + weaponSlots * 18 + 6;
            for (int i = 0; i < weaponSlots; i++) {
                ItemStack s = weapon.getAmmoInventory().getStackInSlot(i);
                if (s != null)
                    fontRendererObj.drawString(s.getDisplayName() + " x" + s.stackSize,
                        ix, AMMO_Y + i * 11, 0x404040);
                else
                    fontRendererObj.drawString("Slot " + (i+1) + ": empty",
                        ix, AMMO_Y + i * 11, 0x888888);
            }
        }
        fontRendererObj.drawString("Inventory", 8, 70, 0x404040);
    }

    private String getModeStr() {
        return getModeStr(weapon.getEngagementMode());
    }

    private String getModeStr(int mode) {
        switch (mode) {
            case 0: return "MANUAL";
            case 1: return "SEMI";
            case 2: return "AUTO";
            default: return "?";
        }
    }
}