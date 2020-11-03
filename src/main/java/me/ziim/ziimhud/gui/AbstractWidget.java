package me.ziim.ziimhud.gui;

import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public abstract class AbstractWidget {
    public int width;
    public int height;
    public float x = 0;
    public float y = 0;
    public float scale = 1;
    protected boolean hovered = false;
    protected MinecraftClient client = MinecraftClient.getInstance();
    public boolean enabled = true;
    public ColorHelper color = new ColorHelper(Color.white);

    public AbstractWidget(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static int floatToInt(float percent, int max, int offset) {
        return MathHelper.clamp(Math.round((max - offset) * percent), 0, max);
    }

    public static float intToFloat(int current, int max, int offset) {
        return MathHelper.clamp((float) (current) / (max - offset), 0, 1);
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public abstract Identifier getID();

    public void renderButton(MatrixStack matrices) {
        if (client == null) {
            client = MinecraftClient.getInstance();
        }
        renderWidget(matrices);
    }

    public ColorHelper getColor() {
        return color;
    }

    public void setColor(ColorHelper color) {
        this.color = color;
    }

    protected abstract void renderWidget(MatrixStack matrices);

    public void renderHud(MatrixStack matrices) {
        if (client == null) {
            client = MinecraftClient.getInstance();
        }
        render(matrices);
    }

    public abstract void render(MatrixStack matrices);

    public void setXY(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return floatToInt(this.x, client.getWindow().getScaledWidth(), Math.round(width * this.scale));
    }

    public void setX(int x) {
        this.x = intToFloat(x, client.getWindow().getScaledWidth(), Math.round(width * this.scale));

    }

    public int getY() {
        return floatToInt(this.y, client.getWindow().getScaledHeight(), Math.round(height * this.scale));
    }

    public void setY(int y) {
        this.y = intToFloat(y, client.getWindow().getScaledHeight(), Math.round(height * this.scale));
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector2D getScaledPos() {
        return getScaledPos(this.scale);
    }

    public Vector2D getScaledPos(float scale) {
        int scaledX = Math.round(getX() / scale);
        int scaledY = Math.round(getY() / scale);
        return new Vector2D(scaledX, scaledY);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
