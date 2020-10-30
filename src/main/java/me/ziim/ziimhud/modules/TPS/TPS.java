package me.ziim.ziimhud.modules.TPS;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.events.Render2DEvent;
import me.ziim.ziimhud.gui.ModuleButton;
import me.ziim.ziimhud.utils.Color;
import me.ziim.ziimhud.utils.TickRate;
import net.minecraft.client.util.math.MatrixStack;

public class TPS extends ModuleButton {

    public TPS(int x, int y) {
        super("TPS", "Shows the TPS of the world", "TPS: ", x, y, new Color(19, 170, 235, 255), true);
    }

    @Subscribe
    public void Render2DEvent(Render2DEvent e) {
//        System.out.println("TickRate: " + TickRate.INSTANCE.getTickRate());
        super.renderText(new MatrixStack(), getText() + TickRate.INSTANCE.getTickRate());

    }
}
