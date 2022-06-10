package me.ziim.ziimhud.gui;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;

public abstract class AbstractWidget {
    public int width;
    public int height;

    public ColorHelper color = new ColorHelper(255, 255, 255, 75);
    protected boolean hovered = false;
    protected MinecraftClient client = Ziimhud.mc;

    public AbstractWidget() {
        this(47, 13);
    }

    public AbstractWidget(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public static int floatToInt(float percent, int max, int offset) {
        return MathHelper.clamp(Math.round((max - offset) * percent), 0, max);
    }

    public static float intToFloat(int current, int max, int offset) {
        return MathHelper.clamp((float) (current) / (max - offset), 0, 1);
    }

    public ColorHelper getColor() {
        return getStorage().textColor;
    }

    public void setTextColor(ColorHelper color) {
        getStorage().textColor = color;
    }

    public void setDataColor(ColorHelper color) {
        getStorage().dataColor = color;
    }

    public abstract Identifier getID();

    protected abstract void renderWidget(MatrixStack matrices);

    public abstract void render(MatrixStack matrices);

    public abstract MutableText getData();

    public abstract MutableText getText();

    public void renderHud(MatrixStack matrices) {
        if (client == null) {
            client = MinecraftClient.getInstance();
        }
        if(isEnabled()) {
            render(matrices);
        }
    }

    public void renderMoveScreen(MatrixStack matrices) {
        if (client == null) {
            client = MinecraftClient.getInstance();
        }
        renderWidget(matrices);
        render(matrices);
    }

    public void setXY(int x, int y) {
        setX(x);
        setY(y);
        try {
            Ziimhud.storageHandler.save();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return floatToInt(getStorage().x, client.getWindow().getScaledWidth(), Math.round(width * getStorage().scale));
    }

    public void setX(int x) {
        getStorage().x = intToFloat(x, client.getWindow().getScaledWidth(), Math.round(width * getStorage().scale));

    }

    public int getY() {
        return floatToInt(getStorage().y, client.getWindow().getScaledHeight(), Math.round(height * getStorage().scale));
    }

    public void setY(int y) {
        getStorage().y = intToFloat(y, client.getWindow().getScaledHeight(), Math.round(height * getStorage().scale));
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector2D getScaledPos() {
        return getScaledPos(getStorage().scale);
    }

    public Vector2D getScaledPos(float scale) {
        int scaledX = Math.round(getX() / scale);
        int scaledY = Math.round(getY() / scale);
        return new Vector2D(scaledX, scaledY);
    }

    public boolean isEnabled() {
        return getStorage().enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.color = new ColorHelper(255, 255, 255, 75);
        } else {
            this.color = new ColorHelper(255, 0, 0, 75);
        }
        getStorage().enabled = enabled;
        try {
            Ziimhud.storageHandler.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getScale() {
        return getStorage().scale;
    }

    public void setScale(float scale) {
        getStorage().scale = scale;
    }

    public abstract <E extends AbstractStorage> E getStorage();

    public static class AbstractStorage {
        public float x = 0;
        public float y = 0;
        public float scale = 1;
        public boolean enabled = true;
        public ColorHelper textColor = new ColorHelper(255, 255, 255, 255);
        public ColorHelper dataColor = new ColorHelper(142, 142, 142, 255);
    }

}
