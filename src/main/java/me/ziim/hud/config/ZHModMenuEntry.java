package me.ziim.hud.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.ziim.hud.ZiiMHud;

public class ZHModMenuEntry implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ZiiMHudConfigController.getConfigScreen(ZiiMHud.config(), parent);
    }

}

