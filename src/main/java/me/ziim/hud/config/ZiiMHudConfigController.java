package me.ziim.hud.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class ZiiMHudConfigController {
    private static final Logger log = LogManager.getLogger();

    private static final File configFile;

    public ZiiMHudConfigController() {
    }

    private static final ZiiMHudConfig defaults = new ZiiMHudConfig();

    public static Screen getConfigScreen(ZiiMHudConfig config, Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("ziimhud.config.title"))
                .setSavingRunnable(() -> persist(config));
        builder.getOrCreateCategory(new TranslatableText("ziimhud.config.category.world"))
                .addEntry(ConfigEntryBuilder.create()
                    .startBooleanToggle(new TranslatableText("ziimhud.config.option.showBiome"), config.biome)
                    .setDefaultValue(defaults.biome)
                    .setSaveConsumer(value -> config.biome = value)
                    .build())
                .addEntry(ConfigEntryBuilder.create()
                    .startBooleanToggle(new TranslatableText("ziimhud.config.option.showPos"), config.pos)
                    .setDefaultValue(defaults.pos)
                    .setSaveConsumer(value -> config.pos = value)
                    .build())
                .addEntry(ConfigEntryBuilder.create()
                    .startBooleanToggle(new TranslatableText("ziimhud.config.option.showNetherPos"), config.netherPos)
                    .setDefaultValue(defaults.netherPos)
                    .setSaveConsumer(value -> config.netherPos = value)
                    .build())
                .addEntry(ConfigEntryBuilder.create()
                    .startBooleanToggle(new TranslatableText("ziimhud.config.option.showDirection"), config.direction)
                    .setDefaultValue(defaults.direction)
                    .setSaveConsumer(value -> config.direction = value)
                    .build());
        builder.getOrCreateCategory(new TranslatableText("ziimhud.config.category.player"))
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showEffects"), config.effects)
                        .setDefaultValue(defaults.effects)
                        .setSaveConsumer(value -> config.effects = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showXP"), config.xp)
                        .setDefaultValue(defaults.xp)
                        .setSaveConsumer(value -> config.xp = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showArmor"), config.armor)
                        .setDefaultValue(defaults.armor)
                        .setSaveConsumer(value -> config.armor = value)
                        .build());
        builder.getOrCreateCategory(new TranslatableText("ziimhud.config.category.network"))
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showIP"), config.ip)
                        .setDefaultValue(defaults.ip)
                        .setSaveConsumer(value -> config.ip = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showPing"), config.ping)
                        .setDefaultValue(defaults.ping)
                        .setSaveConsumer(value -> config.ping = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.ShowFPS"), config.fps)
                        .setDefaultValue(defaults.fps)
                        .setSaveConsumer(value -> config.fps = value)
                        .build());
        return builder.build();
    }

    public static void load(ZiiMHudConfig config) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(configFile));
            //World
            config.biome = Boolean.parseBoolean(props.getProperty("world.biome"));
            config.pos = Boolean.parseBoolean(props.getProperty("world.pos"));
            config.netherPos = Boolean.parseBoolean(props.getProperty("world.netherPos"));
            config.direction = Boolean.parseBoolean(props.getProperty("world.direction"));

            //Player
            config.effects = Boolean.parseBoolean(props.getProperty("player.effects"));
            config.armor = Boolean.parseBoolean(props.getProperty("player.armor"));

            //Network
            config.ip = Boolean.parseBoolean(props.getProperty("network.ip"));
            config.ping = Boolean.parseBoolean(props.getProperty("network.ping"));
            config.fps = Boolean.parseBoolean(props.getProperty("network.fps"));
        } catch(IOException e) {
            log.warn("Could not load configuration settings");
        }
    }

    private static void persist(ZiiMHudConfig config) {
        Properties props = new Properties();
        //World
        props.setProperty("world.biome", String.valueOf(config.biome));
        props.setProperty("world.pos", String.valueOf(config.pos));
        props.setProperty("world.netherPos", String.valueOf(config.netherPos));
        props.setProperty("world.direction", String.valueOf(config.direction));

        //Player
        props.setProperty("player.effects", String.valueOf(config.effects));
        props.setProperty("player.armor", String.valueOf(config.armor));

        //Network/client
        props.setProperty("network.ip", String.valueOf(config.ip));
        props.setProperty("network.ping", String.valueOf(config.ping));
        props.setProperty("network.fps", String.valueOf(config.fps));
        try {
            configFile.createNewFile();
            props.store(new FileOutputStream(configFile), "ZiiM Hud Config");
        } catch(IOException e) {
            log.warn("Could not save configuration settings");
        }
    }

    static {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "ziimhud.properties");
        try {
            if(configFile.createNewFile()) {
                persist(new ZiiMHudConfig());
            }
        } catch(IOException e) {
            log.warn("Could not create configuration file");
        }
    }

}
