package me.ziim.ziimhud.events;

import me.ziim.ziimhud.events.packets.ReceivePacket;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EventManager {
    private static final OnJoinEvent onJoinEvent = new OnJoinEvent();
    private static final OnDisconnect onDisconnect = new OnDisconnect();
    private static final ReceivePacket onRecievePacket = new ReceivePacket();
    private static final Render2DEvent render2DEvent = new Render2DEvent();
    private static final OnKeyPressEvent keyPressEvent = new OnKeyPressEvent();
    private static final ResolutionChangeEvent resolutionChangeEvent = new ResolutionChangeEvent();
    private static final FpsTickEvent fpsTickEvent = new FpsTickEvent();
    private static final SendMessageEvent sendMessageEvent = new SendMessageEvent();

    public static OnJoinEvent gameJoinedEvent() {
        return onJoinEvent;
    }

    public static OnDisconnect gameDisconnectEvent() {
        return onDisconnect;
    }

    public static Render2DEvent render2DEvent(MatrixStack matrixStack) {
        render2DEvent.matrix = matrixStack;
        return render2DEvent;
    }

    public static ReceivePacket receivePacket(Packet<?> packet) {
        onRecievePacket.packet = packet;
        return onRecievePacket;
    }

    public static OnKeyPressEvent keyPressEvent(int key, int scanCode) {
        keyPressEvent.key = key;
        keyPressEvent.scanCode = scanCode;
        return keyPressEvent;
    }

    public static ResolutionChangeEvent resolutionChangeEvent(CallbackInfo ci) {
        resolutionChangeEvent.callbackInfo = ci;
        return resolutionChangeEvent;
    }

    public static FpsTickEvent fpsUpdateEvent(int fps) {
//        System.out.println("FPS2?");
        fpsTickEvent.fps = fps;
        return fpsTickEvent;
    }

    public static SendMessageEvent sendMsgEvent(String msg) {
        sendMessageEvent.msg = msg;
        return sendMessageEvent;
    }
}
