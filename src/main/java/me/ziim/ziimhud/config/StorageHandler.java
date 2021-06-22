package me.ziim.ziimhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ziim.ziimhud.Ziimhud;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class StorageHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile = new File(FabricLoader.getInstance().getConfigDir() + "/ZiiMHud/config.json");

    public StorageHandler() {
        System.out.println("Init storage handler");
        if(!configFile.exists() || !configFile.canRead()) {
            System.out.println("Not exist");
            Ziimhud.storage = new StorageConfig();
        }
        try{
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException{
        configFile.getParentFile().mkdirs();
        if(!configFile.exists() && !configFile.createNewFile()) {
            System.out.println("Not exist and cant make file");
            Ziimhud.storage = new StorageConfig();
            return;
        }
        try {
            String result = GSON.toJson(Ziimhud.storage);
            if(!configFile.exists()) configFile.createNewFile();
            FileOutputStream out = new FileOutputStream(configFile, false);

            out.write(result.getBytes());
            out.flush();
            out.close();
        } catch (Exception e ){
            e.printStackTrace();
            Ziimhud.storage = new StorageConfig();
        }
    }

    public void loadConfig() throws IOException {
        configFile.getParentFile().mkdirs();
        System.out.println("Loading config!!");
        boolean failed = false;
        try {
            Ziimhud.storage = GSON.fromJson(new FileReader(configFile), StorageConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            failed = true;
        }
        if(failed || Ziimhud.storage == null) {
            Ziimhud.storage = new StorageConfig();
        }
        save();
    }
}
