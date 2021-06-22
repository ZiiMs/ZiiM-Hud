package me.ziim.ziimhud.mixins;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import me.ziim.ziimhud.events.FpsTickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow private static int currentFps;

    @Inject(method = "tick", at = @At("HEAD"))
    public void fpsUpdate(CallbackInfo ci) {
        FpsTickEvent event = EventManager.fpsUpdateEvent(currentFps);

        Ziimhud.EVENT_BUS.post(event);
    }
}
