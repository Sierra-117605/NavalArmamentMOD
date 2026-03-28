package com.navalarmament.gui;

import com.navalarmament.tileentity.usn.TECandD;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public class GuiCandD extends GuiScreen {

    private final TECandD candd;

    private static final int COL_BG     = 0xCC000020;
    private static final int COL_BORDER = 0xFF00AA00;
    private static final int COL_TEXT   = 0xFF00FF00;
    private static final int COL_DIM    = 0xFF007700;
    private static final int COL_ASSN   = 0xFFFF8800;

    private static final int GUI_W = 260;

    public GuiCandD(TECandD candd) {
        this.candd = candd;
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    @Override
    public void drawScreen(int mx, int my, float partial) {
        List<TECandD.ClientTargetInfo> targets = candd.getClientTargets();
        int rows   = Math.max(targets.size(), 1);
        int guiH   = 52 + rows * 10 + 14;
        int gx     = (width  - GUI_W) / 2;
        int gy     = (height - guiH)  / 2;

        drawRect(gx, gy, gx + GUI_W, gy + guiH, COL_BG);
        drawRect(gx,           gy,           gx + GUI_W, gy + 1,      COL_BORDER);
        drawRect(gx,           gy + guiH -1, gx + GUI_W, gy + guiH,   COL_BORDER);
        drawRect(gx,           gy,           gx + 1,     gy + guiH,   COL_BORDER);
        drawRect(gx + GUI_W-1, gy,           gx + GUI_W, gy + guiH,   COL_BORDER);

        fontRendererObj.drawString("** C&D SYSTEM  AN/UYK-43 **", gx + 6, gy + 5, COL_TEXT);
        drawRect(gx + 4, gy + 15, gx + GUI_W - 4, gy + 16, COL_BORDER);

        fontRendererObj.drawString("#   ENTITY            TYPE        DIST    STS", gx + 6, gy + 19, COL_DIM);
        drawRect(gx + 4, gy + 28, gx + GUI_W - 4, gy + 29, COL_BORDER);

        if (targets.isEmpty()) {
            fontRendererObj.drawString("--- NO CONTACTS TRACKED ---", gx + 60, gy + 32, COL_DIM);
        } else {
            for (int i = 0; i < targets.size(); i++) {
                TECandD.ClientTargetInfo td = targets.get(i);
                int ty = gy + 31 + i * 10;

                String name = td.entityName;
                if (name.length() > 18) name = name.substring(0, 17) + ".";

                String type = td.targetTypeName;
                String dist = td.distance + "m";
                String sts  = td.assigned ? "ASSN" : "TRCK";
                int stsColor = td.assigned ? COL_ASSN : COL_TEXT;

                fontRendererObj.drawString(
                    pad(i + 1, 3) + " " + pad(name, 18) + " " + pad(type, 11) + pad(dist, 7),
                    gx + 6, ty, COL_TEXT);
                fontRendererObj.drawString(sts, gx + GUI_W - 34, ty, stsColor);
            }
        }

        int fy = gy + guiH - 12;
        drawRect(gx + 4, fy - 2, gx + GUI_W - 4, fy - 1, COL_BORDER);
        fontRendererObj.drawString(
            "CONTACTS: " + targets.size() + "   [ESC] CLOSE",
            gx + 6, fy, COL_DIM);

        super.drawScreen(mx, my, partial);
    }

    private String pad(int n, int len) {
        String s = Integer.toString(n);
        while (s.length() < len) s = " " + s;
        return s;
    }

    private String pad(String s, int len) {
        while (s.length() < len) s = s + " ";
        if (s.length() > len) s = s.substring(0, len);
        return s;
    }
}
