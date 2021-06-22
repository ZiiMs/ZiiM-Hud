package me.ziim.ziimhud.mixins;


import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import me.ziim.ziimhud.events.packets.ReceivePacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        ReceivePacket event = EventManager.receivePacket(packet);
        Ziimhud.EVENT_BUS.post(event);

        if (event.isCancelled()) info.cancel();
    }
}
