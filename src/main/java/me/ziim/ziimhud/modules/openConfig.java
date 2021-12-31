package me.ziim.ziimhud.modules;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.OnKeyPressEvent;
import me.ziim.ziimhud.gui.MoveScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class openConfig {

    @Subscribe
    public void KeyPressEvent(OnKeyPressEvent e) {
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_F3)) return;
        if (e.key == GLFW.GLFW_KEY_P && Ziimhud.mc.currentScreen == null) {
            MoveScreen abstactWindow = new MoveScreen();
            MinecraftClient.getInstance().setScreen(abstactWindow);
        }
    }
}
