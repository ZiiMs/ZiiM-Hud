package me.ziim.ziimhud.mixins;


import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.EventManager;
import me.ziim.ziimhud.events.OnKeyPressEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "INVOKE"), cancellable = true)
    private void onKeyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo info) {
        if (key != -1) {
            OnKeyPressEvent event = EventManager.keyPressEvent(key, scanCode);

            Ziimhud.EVENT_BUS.post(event);
            if (event.isCancelled()) info.cancel();
        }
    }
}
