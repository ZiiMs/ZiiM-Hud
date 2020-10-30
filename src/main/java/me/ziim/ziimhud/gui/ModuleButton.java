package me.ziim.ziimhud.gui;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.utils.Color;
import me.ziim.ziimhud.utils.Math;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleButton {
    private final MinecraftClient mc = Ziimhud.mc;
    boolean active;
    private int safeX;
    private int safeY;
    private String title;
    private String description;
    private String text;
    private int x;
    private int y;
    private int width = 10;
    private int height = 10;
    private int maxHeight;
    private int maxWidth;
    private Color color;
    private boolean visible;
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    public ModuleButton(String title, String description, String text, int x, int y, Color color, boolean visible) {
//        this.module = module;
        this.title = title;
        this.description = description;
        this.x = x;
        this.y = y;
        this.text = text;
        this.visible = visible;
        this.color = color;
    }

    public void init() {
        this.width = mc.textRenderer.getWidth(text);
        this.height = mc.textRenderer.fontHeight;
        this.safeX = this.getMaxWidth() - x;
        this.safeY = this.getMaxHeight() - y;
        this.dragging = false;
        this.dragOffsetY = 0;
        this.dragOffsetX = 0;
    }

    public void toggle() {
        if (!active) {
            active = true;
            Ziimhud.EVENT_BUS.register(this);

        } else {
            active = false;
            Ziimhud.EVENT_BUS.unregister(this);

        }
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSafeX() {
        return safeX;
    }

    public int getSafeY() {
        return safeY;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMaxHeight() {
        return this.maxHeight = mc.getWindow().getScaledHeight() - this.height;
    }

    public int getMaxWidth() {
        return this.maxWidth = mc.getWindow().getScaledWidth() - this.width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public final void startDragging(int mouseX, int mouseY) {
        dragging = true;
        dragOffsetX = x - mouseX;
        dragOffsetY = y - mouseY;
    }

    public final void dragTo(int mouseX, int mouseY) {
        x = mouseX + dragOffsetX;
        y = mouseY + dragOffsetY;
        x = Math.clamp(x, 0, getMaxWidth());
        y = Math.clamp(y, 0, getMaxHeight());
        updatePos(x, y);
    }

    private void updatePos(int x, int y) {
        this.maxHeight = mc.getWindow().getScaledHeight() - this.height;
        this.maxWidth = mc.getWindow().getScaledWidth() - this.width;
        this.safeY = y;
        this.safeX = x;
    }

    public final void stopDragging() {
        dragging = false;
        dragOffsetX = 0;
        dragOffsetY = 0;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void renderText(MatrixStack matrixStack, String s) {
//        System.out.println("Rendering text?: " + this.safeX + "/" + this.safeY);
        this.width = mc.textRenderer.getWidth(s);
        getMaxHeight();
        getMaxWidth();
        mc.textRenderer.drawWithShadow(matrixStack, s, this.safeX, this.safeY, this.color.getPacked());
    }
}
