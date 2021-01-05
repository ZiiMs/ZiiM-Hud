package me.ziim.ziimhud.mixins;


import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import me.ziim.ziimhud.events.Render2DEvent;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

//    @Shadow
//    @Final
//    MinecraftClient client;
//
//    @Shadow
//    private int scaledWidth;
//
//    @Shadow
//    private int scaledHeight;

    @Inject(at = @At(value = "RETURN"), method = "render", cancellable = true)
    private void render(MatrixStack matrixStack, float float_1, CallbackInfo info) {
//        System.out.println("Rendering!");
        Render2DEvent event = EventManager.render2DEvent(matrixStack);
        Ziimhud.EVENT_BUS.post(event);

        if (event.isCancelled()) info.cancel();
    }
}
