package me.ziim.ziimhud.utils;

import com.google.common.eventbus.Subscribe;
import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.events.FpsTickEvent;
import me.ziim.ziimhud.events.OnDisconnect;
import me.ziim.ziimhud.events.OnJoinEvent;
import me.ziim.ziimhud.events.SendMessageEvent;
import me.ziim.ziimhud.events.packets.ReceivePacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

import java.io.IOException;
import java.util.Arrays;

public class EventHelper {
    public static EventHelper INSTANCE = new EventHelper();
    private static int fps = 0;
    private final float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate = -1;
    private long timeGameJoined;

    public EventHelper() {
        Ziimhud.EVENT_BUS.register(this);
    }

    public static int getFps() {
        return fps;
    }

    @Subscribe
    public void onFpsTickUpdate(FpsTickEvent e) {
//        System.out.println("Fps?!?");
        fps = e.fps;
    }

    @Subscribe
    private void onRecievePacket(ReceivePacket e) {
        if (e.packet instanceof WorldTimeUpdateS2CPacket) {
            if (timeLastTimeUpdate != -1L) {
                float timeElapsed = (float) (System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
                tickRates[(nextIndex % tickRates.length)] = Utils.clamp(20.0f / timeElapsed, 0.0f, 20.0f);

                nextIndex += 1;
            }
            timeLastTimeUpdate = System.currentTimeMillis();
        }
    }

    @Subscribe
    private void onGameJoined(OnJoinEvent e) {
        System.out.println("Joining bitch!");
        Arrays.fill(tickRates, 0);
        nextIndex = 0;
        timeLastTimeUpdate = -1;
        timeGameJoined = System.currentTimeMillis();
    }

    @Subscribe
    private void onGameDisconnect(OnDisconnect e) {
        System.out.println("Leaving bitch!");
        try {
            Ziimhud.storageHandler.save();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public float getTickRate() {
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 20;

        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                numTicks += 1.0f;
            }
        }
        return Utils.clamp(sumTickRates / numTicks, 0.0f, 20.0f);
    }
}
