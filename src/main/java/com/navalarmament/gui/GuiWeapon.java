package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiWeapon extends GuiContainer {

    private static final ResourceLocation CHEST_GUI = new ResourceLocation(
        "textures/gui/container/generic_54.png");
    private final TENavalWeapon weapon;

    public GuiWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        super(new ContainerWeapon(playerInv, weapon));
        this.weapon = weapon;
        xSize = 176;
        ySize = 168;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(CHEST_GUI);
        drawTexturedModalRect(guiLeft, guiTop,      0,   0, xSize,  54);
        drawTexturedModalRect(guiLeft, guiTop + 54, 0, 126, xSize, 114);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        String name = weapon.getBlockType() != null
            ? weapon.getBlockType().getLocalizedName() : "Weapon";
        fontRendererObj.drawString(name,                             8,  6, 0x404040);
        fontRendererObj.drawString("Ammo:" + weapon.getAmmoCount(), 8, 57, 0x404040);
        fontRendererObj.drawString("Mode:" + getModeStr(),         90, 57, 0x404040);
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