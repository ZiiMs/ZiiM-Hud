package me.ziim.ziimhud;

import com.google.common.eventbus.EventBus;
import me.ziim.ziimhud.gui.WidgetManager;
import me.ziim.ziimhud.modules.Ping.Ping;
import me.ziim.ziimhud.modules.TPS.TPS;
import me.ziim.ziimhud.modules.openConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class Ziimhud implements ClientModInitializer {
    public static final Ziimhud INSTANCE = new Ziimhud();
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final EventBus EVENT_BUS = new EventBus();

    @Override
    public void onInitializeClient() {
        WidgetManager.INSTANCE = new WidgetManager();
        initHud();


        EVENT_BUS.register(this);
        EVENT_BUS.register(new openConfig());
        System.out.println("ZiiM Hud loaded!");
    }

    private void initHud() {
        WidgetManager.INSTANCE.add(new TPS());
        WidgetManager.INSTANCE.add(new Ping());

        EVENT_BUS.register(WidgetManager.INSTANCE);
    }
}
