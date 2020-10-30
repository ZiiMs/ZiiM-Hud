package me.ziim.ziimhud.mixins;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onGameJoin", at = @At("TAIL"))
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
        Ziimhud.EVENT_BUS.post(EventManager.gameJoinedEvent());
    }

    @Inject(method = "onDisconnect", at = @At("TAIL"))
    private void onDisconnect(DisconnectS2CPacket packet, CallbackInfo info) {
        Ziimhud.EVENT_BUS.post(EventManager.gameDisconnectEvent());
    }
}
