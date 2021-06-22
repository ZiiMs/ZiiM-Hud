package me.ziim.ziimhud.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Render2DEvent extends Event {
    public int screenWidth, screenHeight;
    public float tickDelta;
    public MinecraftClient client;
    public MatrixStack matrix;
}
