package me.ziim.hud.config;

public class ZiiMHudConfig {
    //World
    public boolean biome = true;
    public boolean pos = true;
    public boolean netherPos = true;
    public boolean direction = true;

    //Player
    public boolean effects = true;
    public boolean armor = true;
    public boolean xp = true;

    //Network
    public boolean ip = true;
    public boolean ping = true;

    //Client
    public boolean fps = true;
    public boolean time = true;

    public MenuPositionOptions menuPositionLeft = MenuPositionOptions.TOP;
    public MenuPositionOptions menuPositionRight = MenuPositionOptions.BOTTOM;

    public enum MenuPositionOptions {
        TOP,
        BOTTOM
    }

}
