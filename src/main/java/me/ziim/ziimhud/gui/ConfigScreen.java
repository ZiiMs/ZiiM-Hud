package me.ziim.ziimhud.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ConfigScreen extends Screen {
    private boolean mouseDown = false;
    private Vector2D offset = null;
    private AbstractWidget curr = null;
    private MinecraftClient mc;


    public ConfigScreen() {
        super(new LiteralText(""));
        System.out.println("Opening screen");
        mc = MinecraftClient.getInstance();
    }

    public void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        Optional<AbstractWidget> foundWidget = WidgetManager.INSTANCE.getWidgetXY(mouseX, mouseY);
        foundWidget.ifPresent(abstractWidget -> abstractWidget.hovered = true);
        WidgetManager.INSTANCE.getEntries().forEach(abstractWidget -> {
            abstractWidget.renderMoveScreen(matrices);
        });
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Optional<AbstractWidget> foundWidget = WidgetManager.INSTANCE.getWidgetXY((int) Math.round(mouseX), (int) Math.round(mouseY));
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            mouseDown = true;
            if (foundWidget.isPresent()) {
                curr = foundWidget.get();
                offset = new Vector2D((int) Math.round(mouseX - curr.getX()), (int) Math.round(mouseY - curr.getY()));
                return true;
            } else {
                curr = null;
            }
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            if (foundWidget.isPresent()) {
                foundWidget.get().setEnabled(!foundWidget.get().isEnabled());
                System.out.println(foundWidget.get().isEnabled());
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        curr = null;
        mouseDown = false;
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (curr != null) {
            curr.setXY((int) mouseX - offset.getX(), (int) mouseY - offset.getY());
            return true;
        }
        return false;
    }
}
