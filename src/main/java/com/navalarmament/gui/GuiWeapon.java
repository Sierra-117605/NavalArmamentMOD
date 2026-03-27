package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiWeapon extends GuiContainer {

    private final TENavalWeapon weapon;

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
        xSize = 176;
        ySize = 162;
    }

    private void drawPanel(int x, int y, int w, int h) {
        drawRect(x, y, x+w, y+h, BG);
        drawRect(x, y, x+w, y+1, HILIGHT);
        drawRect(x, y, x+1, y+h, HILIGHT);
        drawRect(x, y+h-1, x+w, y+h, SHADOW);
        drawRect(x+w-1, y, x+w, y+h, SHADOW);
    }

    // Minecraftのスロットはx,yから18x18の領域
    // 背景はx-1,y-1から20x20で描画するとぴったり合う
    private void drawSlotBg(int x, int y) {
        drawRect(x-1, y-1, x+17, y+17, SHADOW);
        drawRect(x-1, y-1, x+17, y-1+1, SHADOW);
        drawRect(x-1, y-1, x-1+1, y+17, SHADOW);
        drawRect(x-1, y+16, x+17, y+17, HILIGHT);
        drawRect(x+16, y-1, x+17, y+17, HILIGHT);
        drawRect(x, y, x+16, y+16, SLOT_BG);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        drawPanel(guiLeft, guiTop, xSize, ySize);
        drawRect(guiLeft+4, guiTop+68,  guiLeft+xSize-4, guiTop+69,  SHADOW);
        drawRect(guiLeft+4, guiTop+132, guiLeft+xSize-4, guiTop+133, SHADOW);

        drawSlotBg(guiLeft+8,    guiTop+AMMO_Y);
        drawSlotBg(guiLeft+8+18, guiTop+AMMO_Y);

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

        int ix = 8 + 2*18 + 6;
        ItemStack s0 = weapon.getAmmoInventory().getStackInSlot(0);
        ItemStack s1 = weapon.getAmmoInventory().getStackInSlot(1);
        if (s0 != null) fontRendererObj.drawString(s0.getDisplayName() + " x" + s0.stackSize, ix, AMMO_Y,    0x404040);
        else            fontRendererObj.drawString("Slot 1: empty",                             ix, AMMO_Y,    0x888888);
        if (s1 != null) fontRendererObj.drawString(s1.getDisplayName() + " x" + s1.stackSize, ix, AMMO_Y+11, 0x404040);
        else            fontRendererObj.drawString("Slot 2: empty",                             ix, AMMO_Y+11, 0x888888);
        fontRendererObj.drawString("Mode: " + getModeStr(), ix, AMMO_Y+28, 0x404040);
        fontRendererObj.drawString("Inventory", 8, 70, 0x404040);
    }

    private String getModeStr() {
        switch (weapon.getEngagementMode()) {
            case 0: return "MANUAL";
            case 1: return "SEMI";
            case 2: return "AUTO";
            default: return "?";
        }
    }
}