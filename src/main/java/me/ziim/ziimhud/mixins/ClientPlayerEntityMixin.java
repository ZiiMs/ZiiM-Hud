package me.ziim.ziimhud.mixins;


import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ziim.ziimhud.Commands.CommandManager;
import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import me.ziim.ziimhud.events.SendMessageEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow
    public abstract void sendChatMessage(String string);

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String msg, CallbackInfo info) {
        if (msg.startsWith(",")) {
            try {
                CommandManager.dispatch(msg.substring(1));
            } catch (CommandSyntaxException e) {
                Ziimhud.mc.player.sendChatMessage(e.getMessage());
            }
            info.cancel();
        }
        SendMessageEvent event = EventManager.sendMsgEvent(msg);
        Ziimhud.EVENT_BUS.post(event);
    }

}
