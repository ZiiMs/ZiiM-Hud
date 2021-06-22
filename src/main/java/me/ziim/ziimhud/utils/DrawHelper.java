package me.ziim.ziimhud.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class DrawHelper {


    public void drawStringConcat(MatrixStack stack, TextRenderer textRenderer, String text1, String text2, int x, int y, ColorHelper color1, ColorHelper color2) {
        DrawableHelper.drawCenteredText(stack, textRenderer, text1, x, y, color1.getPacked());
        DrawableHelper.drawCenteredText(stack, textRenderer, text2, x + textRenderer.getWidth(text2), y, color2.getPacked());
    }

}
