package me.ziim.ziimhud.modules;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.OnDisconnect;
import me.ziim.ziimhud.events.OnJoinEvent;
import me.ziim.ziimhud.gui.ModuleButton;
import me.ziim.ziimhud.modules.Ping.Ping;
import me.ziim.ziimhud.modules.TPS.TPS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleManager {
    public static ModuleManager INSTANCE;

    //    private final Map<Class<? extends ModuleButton>, ModuleButton> modules = new HashMap<>();
    private final List<ModuleButton> modules = new ArrayList<>();
    private final List<ModuleButton> active = new ArrayList<>();

    public ModuleManager() {
        Ziimhud.EVENT_BUS.register(this);
        initModules();
    }

    @Subscribe
    public void onGameJoined(OnJoinEvent e) {
        activateModules();
    }

    private void activateModules() {
        for (ModuleButton module : this.getAll()) {
            module.init();
            this.addActive(module);
        }
    }

    public void initModules() {
        modules.add(new TPS(50, 150));
        modules.add(new Ping());
    }

    @Subscribe
    public void onGameDisconnect(OnDisconnect e) {
        deActivateModules();
    }

    private void deActivateModules() {
        for (ModuleButton module : this.getAll()) {
            this.removeActive(module);
        }
    }

    public void addActive(ModuleButton module) {
        synchronized (active) {
            if (!active.contains(module)) {
//                modules.put(module.getClass(), module);
                active.add(module);
                module.toggle();
            }
        }
    }

    public ModuleButton get(String name) {
        for (ModuleButton module : modules) {
            if (module.getTitle().equalsIgnoreCase(name)) return module;
        }
        return null;
    }

    public Collection<ModuleButton> getAll() {
        return modules;
    }

//    public <T extends Module> T get(Class<T> klass) {
//        return (T) modules.get(klass);
//    }

    public void removeActive(ModuleButton module) {
        synchronized (active) {
            if (!active.remove(module)) {
                active.remove(module);
                module.toggle();
            }
        }
    }
}
