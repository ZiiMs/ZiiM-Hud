package me.ziim.ziimhud.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ConfigScreen extends Screen {

    private final ConfigGui gui;
    //    Map<Module, ButtonWidget> buttonList = new HashMap<>();
    private MinecraftClient mc = MinecraftClient.getInstance();


    public ConfigScreen(ConfigGui gui) {
        super(new LiteralText(""));
        this.gui = gui;

    }

    public void init() {
        super.init();
        gui.init();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        gui.render(matrices, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        System.out.println("Clicked?!?!");
        gui.handleMouseClick((int) mouseX, (int) mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        gui.handleMouseRelease(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
