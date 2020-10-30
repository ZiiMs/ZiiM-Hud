package me.ziim.ziimhud.modules.Ping;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.Render2DEvent;
import me.ziim.ziimhud.gui.ModuleButton;
import me.ziim.ziimhud.utils.Color;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

public class Ping extends ModuleButton {
    public Ping() {
        super("Ping", "Shows current ping of the server", "Ping: ", 10, 125, new Color(255, 255, 255, 255), true);
    }

    @Subscribe
    public void Render2DEvent(Render2DEvent e) {
        PlayerListEntry playerListEntry = Ziimhud.mc.getNetworkHandler().getPlayerListEntry(Ziimhud.mc.player.getUuid());
        if (playerListEntry != null) {
            super.renderText(new MatrixStack(), getText() + playerListEntry.getLatency());
        }
    }
}
