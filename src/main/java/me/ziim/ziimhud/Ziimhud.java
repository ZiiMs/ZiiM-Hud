package me.ziim.ziimhud;

import com.google.common.eventbus.EventBus;
import me.ziim.ziimhud.events.RenderCallback;
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
        WidgetManager.INSTANCE.add(new TPS());
        WidgetManager.INSTANCE.add(new Ping());


//        ModuleManager.INSTANCE = new ModuleManager();
//        ModuleManager.INSTANCE.addActive(new Ping());
//        ModuleManager.INSTANCE.addActive(new TPS(new int[]{50, 150}));

        EVENT_BUS.register(new RenderCallback());
        EVENT_BUS.register(this);
        EVENT_BUS.register(new openConfig());
        System.out.println("ZiiM Hud loaded!");
    }
}
