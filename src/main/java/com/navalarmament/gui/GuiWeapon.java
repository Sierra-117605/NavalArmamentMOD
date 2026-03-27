package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiWeapon extends GuiContainer {

    private final TENavalWeapon weapon;

    public GuiWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        super(new ContainerWeapon(playerInv, weapon));
        this.weapon = weapon;
        xSize = 176;
        ySize = 156;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFFC6C6C6);
        // 外枠
        drawRect(guiLeft,              guiTop,              guiLeft + xSize, guiTop + 1,          0xFF555555);
        drawRect(guiLeft,              guiTop + ySize - 1,  guiLeft + xSize, guiTop + ySize,       0xFF555555);
        drawRect(guiLeft,              guiTop,              guiLeft + 1,     guiTop + ySize,        0xFF555555);
        drawRect(guiLeft + xSize - 1,  guiTop,              guiLeft + xSize, guiTop + ySize,        0xFF555555);
        // 区切り線（弾薬エリアとインベントリの間）
        drawRect(guiLeft + 4, guiTop + 55, guiLeft + xSize - 4, guiTop + 56, 0xFF888888);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        String name = "Weapon";
        if (weapon.getBlockType() != null) {
            name = weapon.getBlockType().getLocalizedName();
        }
        fontRendererObj.drawString(name,                              8,  6, 0x404040);
        fontRendererObj.drawString("Ammo: " + weapon.getAmmoCount(), 8, 17, 0x404040);
        fontRendererObj.drawString("Mode: " + getModeStr(),         90, 17, 0x404040);
        fontRendererObj.drawString("Inventory",                      8, 58, 0x404040);
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