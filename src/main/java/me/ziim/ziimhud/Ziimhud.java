package me.ziim.ziimhud;

import com.google.common.eventbus.EventBus;
import me.ziim.ziimhud.Commands.CommandManager;
import me.ziim.ziimhud.config.StorageConfig;
import me.ziim.ziimhud.config.StorageHandler;
import me.ziim.ziimhud.gui.WidgetManager;
import me.ziim.ziimhud.modules.Armour.Armour;
import me.ziim.ziimhud.modules.Biome.Biome;
import me.ziim.ziimhud.modules.CoordModules.NetherPos;
import me.ziim.ziimhud.modules.CoordModules.Pos;
import me.ziim.ziimhud.modules.Direction.Direction;
import me.ziim.ziimhud.modules.FPS.FPS;
import me.ziim.ziimhud.modules.IP.IP;
import me.ziim.ziimhud.modules.Ping.Ping;
import me.ziim.ziimhud.modules.Potions.Potions;
import me.ziim.ziimhud.modules.TPS.TPS;
import me.ziim.ziimhud.modules.Time.Time;
import me.ziim.ziimhud.modules.XP.XP;
import me.ziim.ziimhud.modules.openConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class Ziimhud implements ClientModInitializer {
    public static final Ziimhud INSTANCE = new Ziimhud();
    public static StorageConfig storage;
    public static StorageHandler storageHandler;
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final EventBus EVENT_BUS = new EventBus();

    @Override
    public void onInitializeClient() {
        WidgetManager.INSTANCE = new WidgetManager();
        CommandManager.init();
        initHud();


        EVENT_BUS.register(this);
        EVENT_BUS.register(new openConfig());
        System.out.println("ZiiM Hud loaded!");
    }

    private void initHud() {
        storageHandler = new StorageHandler();
        WidgetManager.INSTANCE.add(new TPS());
        WidgetManager.INSTANCE.add(new Armour(47, 13));
        WidgetManager.INSTANCE.add(new Ping());
        WidgetManager.INSTANCE.add(new Biome());
        WidgetManager.INSTANCE.add(new Pos());
        WidgetManager.INSTANCE.add(new NetherPos());
        WidgetManager.INSTANCE.add(new Direction());
        WidgetManager.INSTANCE.add(new FPS());
        WidgetManager.INSTANCE.add(new Time());
        WidgetManager.INSTANCE.add(new XP());
        WidgetManager.INSTANCE.add(new IP());
        WidgetManager.INSTANCE.add(new Potions());

        EVENT_BUS.register(WidgetManager.INSTANCE);
    }
}
