package me.ziim.ziimhud.modules.Ping;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractHudWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;

public class Ping extends AbstractHudWidget {
    public static final Identifier ID = new Identifier("ziimhud", "ping");

    public Ping() {
        super();
    }

    @Override
    public String getData() {
        PlayerListEntry playerListEntry = Ziimhud.mc.getNetworkHandler().getPlayerListEntry(Ziimhud.mc.player.getUuid());
        if (playerListEntry != null) {
            return String.format("Ping: %d", playerListEntry.getLatency());
        }
        return "Ping 0";
    }

    @Override
    public String getText() {
        return "Ping";
    }

    @Override
    public Identifier getID() {
        return ID;
    }
}
