package me.ziim.hud.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
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
    private static final ZiiMHudConfig defaults = new ZiiMHudConfig();

    static {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "ziimhud.properties");
        try {
            if (configFile.createNewFile()) {
                persist(new ZiiMHudConfig());
            }
        } catch (IOException e) {
            log.warn("Could not create configuration file");
        }
    }

    public ZiiMHudConfigController() {
    }

    public static Screen getConfigScreen(ZiiMHudConfig config, Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("ziimhud.config.title"))
                .setSavingRunnable(() -> persist(config));
        builder.getOrCreateCategory(new TranslatableText("ziimhud.config.category.general"))
                .addEntry(ConfigEntryBuilder.create()
                        .startSelector(new TranslatableText("ziimhud.config.option.menuPositionLeft"), ZiiMHudConfig.MenuPositionOptions.values(), config.menuPositionLeft)
                        .setDefaultValue(ZiiMHudConfig.MenuPositionOptions.TOP)
                        .setNameProvider(value -> {
                            if(value.equals(ZiiMHudConfig.MenuPositionOptions.BOTTOM)) {
                                return new TranslatableText("ziimhud.config.option.menuSpot.bottom");
                            } else if(value.equals(ZiiMHudConfig.MenuPositionOptions.TOP)) {
                                return new TranslatableText("ziimhud.config.option.menuSpot.top");
                            }
                            return new LiteralText("Error");
                        })
                        .setSaveConsumer(value -> config.menuPositionLeft = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startSelector(new TranslatableText("ziimhud.config.option.menuPositionRight"), ZiiMHudConfig.MenuPositionOptions.values(), config.menuPositionRight)
                        .setDefaultValue(ZiiMHudConfig.MenuPositionOptions.BOTTOM)
                        .setNameProvider(value -> {
                            if(value.equals(ZiiMHudConfig.MenuPositionOptions.BOTTOM)) {
                                return new TranslatableText("ziimhud.config.option.menuSpot.bottom");
                            } else if(value.equals(ZiiMHudConfig.MenuPositionOptions.TOP)) {
                                return new TranslatableText("ziimhud.config.option.menuSpot.top");
                            }
                            return new LiteralText("Error");
                        })
                        .setSaveConsumer(value -> config.menuPositionRight = value)
                        .build());
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
                        .build());
        builder.getOrCreateCategory(new TranslatableText("ziimhud.config.category.client"))
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showTime"), config.time)
                        .setDefaultValue(defaults.time)
                        .setSaveConsumer(value -> config.time = value)
                        .build())
                .addEntry(ConfigEntryBuilder.create()
                        .startBooleanToggle(new TranslatableText("ziimhud.config.option.showFPS"), config.fps)
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
            config.xp = Boolean.parseBoolean(props.getProperty("player.xp"));
            config.effects = Boolean.parseBoolean(props.getProperty("player.effects"));
            config.armor = Boolean.parseBoolean(props.getProperty("player.armor"));

            //Network
            config.ip = Boolean.parseBoolean(props.getProperty("network.ip"));
            config.ping = Boolean.parseBoolean(props.getProperty("network.ping"));

            //Client
            config.fps = Boolean.parseBoolean(props.getProperty("client.fps"));
            config.time = Boolean.parseBoolean(props.getProperty("client.time"));

            config.menuPositionLeft = ZiiMHudConfig.MenuPositionOptions.valueOf(props.getProperty("general.leftposition", "TOP"));
            config.menuPositionRight = ZiiMHudConfig.MenuPositionOptions.valueOf(props.getProperty("general.rightposition", "BOTTOM"));
        } catch (IOException e) {
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
        props.setProperty("player.xp", String.valueOf(config.xp));
        props.setProperty("player.effects", String.valueOf(config.effects));
        props.setProperty("player.armor", String.valueOf(config.armor));

        //Network
        props.setProperty("network.ip", String.valueOf(config.ip));
        props.setProperty("network.ping", String.valueOf(config.ping));

        //Client
        props.setProperty("client.fps", String.valueOf(config.fps));
        props.setProperty("client.time", String.valueOf(config.time));

        props.setProperty("general.leftposition", String.valueOf(config.menuPositionLeft));
        props.setProperty("general.rightposition", String.valueOf(config.menuPositionRight));
        try {
            configFile.createNewFile();
            props.store(new FileOutputStream(configFile), "ZiiM Hud Config");
        } catch (IOException e) {
            log.warn("Could not save configuration settings");
        }
    }

}
