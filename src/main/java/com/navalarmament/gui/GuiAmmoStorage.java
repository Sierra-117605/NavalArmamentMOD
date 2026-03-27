package com.navalarmament.gui;

import com.navalarmament.tileentity.common.TEAmmoStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAmmoStorage extends GuiContainer {

    private static final ResourceLocation CHEST_GUI = new ResourceLocation(
        "textures/gui/container/generic_54.png");

    public GuiAmmoStorage(InventoryPlayer playerInv, TEAmmoStorage storage) {
        super(new ContainerAmmoStorage(playerInv, storage));
        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(CHEST_GUI);
        // 上部（弾薬庫6行）
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 126);
        // 下部（プレイヤーインベントリ）
        drawTexturedModalRect(guiLeft, guiTop + 126, 0, 126, xSize, 96);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        fontRendererObj.drawString("Ammo Storage", 8, 6, 0x404040);
        fontRendererObj.drawString("Inventory",    8, 128, 0x404040);
    }
}