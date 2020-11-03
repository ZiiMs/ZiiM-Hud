package me.ziim.ziimhud.modules.TPS;

import me.ziim.ziimhud.gui.AbstractHudWidget;
import me.ziim.ziimhud.utils.ColorHelper;
import me.ziim.ziimhud.utils.TickRate;
import net.minecraft.util.Identifier;

import java.awt.*;

public class TPS extends AbstractHudWidget {
    public static final Identifier ID = new Identifier("ziimhud", "tps");

    public TPS() {
        super();
        setColor(new ColorHelper(Color.BLUE));
    }

    @Override
    public String getData() {
        return String.format("TickRate: %.1f", TickRate.INSTANCE.getTickRate());
    }

    @Override
    public String getText() {
        return "TickRate";
    }

    @Override
    public Identifier getID() {
        return ID;
    }
}
