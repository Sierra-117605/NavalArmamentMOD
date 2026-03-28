package com.navalarmament.gui;

import com.navalarmament.network.NavalPacketHandler;
import com.navalarmament.tileentity.base.TENavalWeapon;
import com.navalarmament.tileentity.usn.TEOperatorConsole;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiOperatorConsole extends GuiContainer {

    private final TEOperatorConsole console;
    private List<TENavalWeapon> weapons = new ArrayList<TENavalWeapon>();

    private static final int BG      = 0xFFC6C6C6;
    private static final int SHADOW  = 0xFF555555;
    private static final int HILIGHT = 0xFFFFFFFF;
    private static final int ROW_H   = 24;
    private static final int HEADER_Y = 18;
    private static final int ROW_Y0  = 28;

    private static final int COL_NAME    = 8;
    private static final int COL_AMMO    = 100;
    private static final int COL_MODE    = 125;
    private static final int COL_AMMOBTN = 162;
    private static final int COL_TARGET  = 200;

    public GuiOperatorConsole(InventoryPlayer playerInv, TEOperatorConsole console) {
        super(new ContainerOperatorConsole(playerInv));
        this.console = console;
        xSize = 240;
        ySize = 180;
    }

    @Override
    public void initGui() {
        super.initGui();
        weapons = console.findAllWeaponsViaBFS();
        ySize = Math.max(80, ROW_Y0 + weapons.size() * ROW_H + 10);
        buttonList.clear();

        for (int i = 0; i < weapons.size(); i++) {
            TENavalWeapon w = weapons.get(i);
            int y = guiTop + ROW_Y0 + i * ROW_H + 4;
            // モードボタン
            buttonList.add(new GuiButton(i * 10 + 0, guiLeft + COL_MODE,    y, 34, 14, getModeStr(w.getEngagementMode())));
            // 弾種ボタン
            buttonList.add(new GuiButton(i * 10 + 1, guiLeft + COL_AMMOBTN, y, 36, 14, getAmmoStr(w)));
            // 目標タイプボタン
            buttonList.add(new GuiButton(i * 10 + 2, guiLeft + COL_TARGET,  y, 36, 14, getTargetStr(w)));
        }
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        int wi   = btn.id / 10;
        int type = btn.id % 10;
        if (wi >= weapons.size()) return;
        TENavalWeapon w = weapons.get(wi);

        if (type == 0) {
            int next = (w.getEngagementMode() + 1) % 3;
            w.setEngagementMode(next);
            btn.displayString = getModeStr(next);
            sendSetting(w);
        } else if (type == 1) {
            String next = cycleAmmo(w);
            w.setPreferredAmmoClass(next);
            btn.displayString = getAmmoStr(w);
            sendSetting(w);
        } else if (type == 2) {
            String next = cycleTarget(w);
            w.setPreferredTargetType(next);
            btn.displayString = getTargetStr(w);
            sendSetting(w);
        }
    }

    private void sendSetting(TENavalWeapon w) {
        NavalPacketHandler.sendWeaponSetting(
            w.xCoord, w.yCoord, w.zCoord,
            w.getEngagementMode(),
            w.getPreferredAmmoClass(),
            w.getPreferredTargetType());
    }

    private String cycleAmmo(TENavalWeapon w) {
        List<String> classes = new ArrayList<String>();
        classes.add("");
        for (int i = 0; i < w.getAmmoInventory().getSizeInventory(); i++) {
            ItemStack s = w.getAmmoInventory().getStackInSlot(i);
            if (s != null) {
                String cls = s.getItem().getClass().getName();
                if (!classes.contains(cls)) classes.add(cls);
            }
        }
        String cur = w.getPreferredAmmoClass();
        int idx = classes.indexOf(cur);
        if (idx < 0) idx = 0;
        return classes.get((idx + 1) % classes.size());
    }

    private String cycleTarget(TENavalWeapon w) {
        String[] types = {"", "AIR", "SURFACE", "SUBSURFACE"};
        String cur = w.getPreferredTargetType();
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(cur)) return types[(i + 1) % types.length];
        }
        return "";
    }

    private String getModeStr(int mode) {
        switch (mode) { case 0: return "MANUAL"; case 1: return "SEMI"; case 2: return "AUTO"; default: return "?"; }
    }

    private String getAmmoStr(TENavalWeapon w) {
        String cls = w.getPreferredAmmoClass();
        if (cls == null || cls.isEmpty()) return "AUTO";
        String[] parts = cls.split("\\.");
        String name = parts[parts.length - 1].replace("Item", "");
        return name.length() > 6 ? name.substring(0, 6) : name;
    }

    private String getTargetStr(TENavalWeapon w) {
        String t = w.getPreferredTargetType();
        if (t == null || t.isEmpty()) return "ALL";
        if (t.equals("SUBSURFACE")) return "SUB";
        return t;
    }

    private void drawPanel(int x, int y, int w, int h) {
        drawRect(x, y, x+w, y+h, BG);
        drawRect(x, y, x+w, y+1, HILIGHT);
        drawRect(x, y, x+1, y+h, HILIGHT);
        drawRect(x, y+h-1, x+w, y+h, SHADOW);
        drawRect(x+w-1, y, x+w, y+h, SHADOW);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1, 1, 1, 1);
        drawPanel(guiLeft, guiTop, xSize, ySize);
        for (int i = 1; i < weapons.size(); i++) {
            int ly = guiTop + ROW_Y0 + i * ROW_H;
            drawRect(guiLeft + 4, ly, guiLeft + xSize - 4, ly + 1, SHADOW);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        fontRendererObj.drawString("CIC Operator Console", 8, 6, 0x404040);

        // ヘッダー
        fontRendererObj.drawString("Weapon",  COL_NAME,    HEADER_Y, 0x606060);
        fontRendererObj.drawString("Ammo",    COL_AMMO,    HEADER_Y, 0x606060);
        fontRendererObj.drawString("Mode",    COL_MODE,    HEADER_Y, 0x606060);
        fontRendererObj.drawString("Type",    COL_AMMOBTN, HEADER_Y, 0x606060);
        fontRendererObj.drawString("Target",  COL_TARGET,  HEADER_Y, 0x606060);

        if (weapons.isEmpty()) {
            fontRendererObj.drawString("No weapons connected", COL_NAME, ROW_Y0 + 4, 0x888888);
            return;
        }

        for (int i = 0; i < weapons.size(); i++) {
            TENavalWeapon w = weapons.get(i);
            int y = ROW_Y0 + i * ROW_H + 6;

            String name = w.getBlockType() != null ? w.getBlockType().getLocalizedName() : "Weapon";
            if (name.length() > 13) name = name.substring(0, 13);
            fontRendererObj.drawString(name, COL_NAME, y, 0x404040);

            // 残弾数（サーバー側と同期していないがクライアント側キャッシュを表示）
            int ammo = w.getAmmoCount();
            int color = ammo > 10 ? 0x206020 : ammo > 0 ? 0xCC6600 : 0xCC2020;
            fontRendererObj.drawString("x" + ammo, COL_AMMO, y, color);
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}