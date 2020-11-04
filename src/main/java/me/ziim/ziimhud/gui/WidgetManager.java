package me.ziim.ziimhud.gui;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.events.Render2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WidgetManager {
    public static WidgetManager INSTANCE;
    private final HashMap<Identifier, AbstractWidget> entries;
    private final MinecraftClient mc;

    public WidgetManager() {
        this.entries = new HashMap<>();
        mc = MinecraftClient.getInstance();
    }

    public WidgetManager add(AbstractWidget widget) {
        entries.put(widget.getID(), widget);
        return this;
    }

    public List<AbstractWidget> getEntries() {
        if (entries.size() > 0) {
            return new ArrayList<>(entries.values());
        }
        return new ArrayList<>();
    }

    public AbstractWidget get(Identifier identifier) {
        return entries.get(identifier);
    }

    @Subscribe
    public void render(Render2DEvent e) {
        if (!mc.options.debugEnabled) {
            for (AbstractWidget widget : getEntries()) {
                if (widget.isEnabled()) {
                    widget.renderHud(e.matrix);
                }
            }
        }
    }

    public Optional<AbstractWidget> getWidgetXY(int x, int y) {
        for (AbstractWidget widget : getEntries()) {
            if (widget.getX() <= x && widget.getX() + widget.width * widget.scale >= x && widget.getY() + widget.height * widget.scale >= y) {
                return Optional.of(widget);
            }
        }
        return Optional.empty();
    }

}
