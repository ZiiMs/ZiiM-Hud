package me.ziim.ziimhud.events.packets;

import me.ziim.ziimhud.events.Event;
import net.minecraft.network.Packet;

public class ReceivePacket extends Event {
    public Packet<?> packet;
}
