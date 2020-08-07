package me.ziim.hud;

import me.ziim.hud.config.ZiiMHudConfig;
import me.ziim.hud.config.ZiiMHudConfigController;
import net.fabricmc.api.ClientModInitializer;

public class ZiiMHud implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZiiMHudConfigController.load(config);
    }

    private static final ZiiMHudConfig config = new ZiiMHudConfig();

    public static ZiiMHudConfig config() {
        return config;
    }
}
