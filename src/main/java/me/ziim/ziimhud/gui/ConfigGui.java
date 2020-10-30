package me.ziim.ziimhud.gui;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.modules.ModuleManager;
import me.ziim.ziimhud.utils.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class ConfigGui {
    private static final MinecraftClient mc = Ziimhud.mc;
    private final ArrayList<ModuleButton> buttonList = new ArrayList<>();
    private boolean background = false;
    private boolean leftMouseButtonPressed;


    public ConfigGui() {
    }

    public void init() {
        buttonList.addAll(ModuleManager.INSTANCE.getAll());
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
//        System.out.println("Clicked?!?!?");
        if (mouseButton == 0)
            leftMouseButtonPressed = true;

        for (int i = buttonList.size() - 1; i >= 0; i--) {
            ModuleButton button = buttonList.get(i);
            if (!button.isVisible()) continue;

            int x1 = button.getSafeX();
            int y1 = button.getSafeY();
            int x2 = x1 + button.getWidth();
            int y2 = y1 + button.getHeight();

            if (mouseX < x1 || mouseY < y1) continue;
            if (mouseX >= x2 || mouseY >= y2)
                continue;

            button.startDragging(mouseX, mouseY);
        }
    }

    public void handleMouseRelease(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            leftMouseButtonPressed = false;
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        for (ModuleButton button : buttonList) {
            if (!button.isVisible()) continue;

            if (button.isDragging()) {
                if (isLeftMouseButtonPressed()) {
                    button.dragTo(mouseX, mouseY);
                } else {
                    System.out.println("Mouse stopped?");
                    button.stopDragging();
                }
            }
//            button.updateButton();
            renderButton(matrixStack, button, mouseX, mouseY, partialTicks);
        }
    }

    public void renderButton(MatrixStack matrixStack, ModuleButton button, int mouseX, int mouseY, float partialTicks) {
        int x1 = button.getSafeX();
        int y1 = button.getSafeY();
        int x2 = x1 + button.getWidth();
        int y2 = y1 + button.getHeight();

        drawButton(matrixStack, button.getTitle(), x1, y1, x2, y2, button.getColor());
    }

    public void drawButton(MatrixStack stack, String text, int x1, int y1, int x2, int y2, Color color) {
        DrawableHelper.fill(stack, x1 - 2, y1 - 2, x2 + 2, y2 + 2, Color.fromRGBA(100, 100, 100, 100));
        DrawableHelper.fill(stack, x1, y1, x2, y2, color.getPacked());
        DrawableHelper.drawCenteredString(stack, mc.textRenderer, text, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2 - 4, -1);

    }

    public boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }
}
