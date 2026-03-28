package com.navalarmament.gui;

import com.navalarmament.tileentity.base.TENavalWeapon;
import com.navalarmament.network.NavalPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiWeapon extends GuiContainer {

    private final TENavalWeapon weapon;
    private final int weaponSlots;
    private final int ammoRows;
    private final int sep1Y;
    private final int invY;
    private final int sep2Y;
    private final int hotY;

    private static final int BG      = 0xFFC6C6C6;
    private static final int SHADOW  = 0xFF555555;
    private static final int HILIGHT = 0xFFFFFFFF;
    private static final int SLOT_BG = 0xFF8B8B8B;

    public GuiWeapon(InventoryPlayer playerInv, TENavalWeapon weapon) {
        super(new ContainerWeapon(playerInv, weapon));
        this.weapon = weapon;
        int size = weapon.getAmmoInventory().getSizeInventory();
        this.weaponSlots = size;
        int cols = weapon.getGuiColumns();
        this.ammoRows = (weaponSlots + cols - 1) / cols;
        this.sep1Y = 22 + ammoRows * 18 + 10;
        this.invY  = sep1Y + 10;
        this.sep2Y = invY + 54;
        this.hotY  = sep2Y + 4;
        xSize = 176;
        ySize = hotY + 26;
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
        drawRect(guiLeft+4, guiTop+sep1Y, guiLeft+xSize-4, guiTop+sep1Y+1, SHADOW);
        drawRect(guiLeft+4, guiTop+sep2Y, guiLeft+xSize-4, guiTop+sep2Y+1, SHADOW);

        int cols = weapon.getGuiColumns();
        for (int i = 0; i < weaponSlots; i++)
            drawSlotBg(guiLeft+8+(i%cols)*18, guiTop+22+(i/cols)*18);

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                drawSlotBg(guiLeft+8+col*18, guiTop+invY+row*18);

        for (int col = 0; col < 9; col++)
            drawSlotBg(guiLeft+8+col*18, guiTop+hotY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        String name = weapon.getBlockType() != null
            ? weapon.getBlockType().getLocalizedName() : "Weapon";
        fontRendererObj.drawString(name, 8, 8, 0x404040);
        fontRendererObj.drawString("Inventory", 8, sep1Y + 2, 0x404040);
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