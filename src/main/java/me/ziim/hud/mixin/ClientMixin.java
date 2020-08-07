package me.ziim.hud.mixin;

import me.ziim.hud.client.DrawUI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = InGameHud.class)
public abstract class ClientMixin {
    private DrawUI uiInfo;
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;)V", at = @At(value = "RETURN"))
    private void onInit(MinecraftClient client, CallbackInfo ci) {
        // Start Mixin
        System.out.println("Init ZiiM-hud Mixin");
        this.uiInfo = new DrawUI(this.client);
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;hudHidden:Z", ordinal = 2))
    private void onDraw(MatrixStack matrixStack, float f, CallbackInfo ci) {
        if (!this.client.options.debugEnabled) {
            this.uiInfo.draw();
        }
    }

    @Inject(method = "resetDebugHudChunk", at = @At(value = "RETURN"))
    private void onReset(CallbackInfo ci) {
    }



}
