package me.ziim.hud.client;


import com.google.common.collect.Lists;
import me.ziim.hud.ZiiMHud;
import me.ziim.hud.config.ZiiMHudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class DrawUI implements Drawable {
    private final MinecraftClient client;
    private final TextRenderer text;
    int height;
    private ClientPlayerEntity player;

    public DrawUI(MinecraftClient client) {
        this.client = client;
        this.text = client.textRenderer;
    }

    public void draw() {
        this.player = this.client.player;

        this.drawRightStats();
        this.drawLeftStats();

        this.client.getProfiler().pop();
    }

    private void drawRightStats() {

        String coords = "Pos:§7 " + getCoords();
        String netherCoords = "Nether Pos:§7 " + getNetherCoords();
        String direction = getDirection();
        if (ZiiMHud.config().armor) {
            drawEquipmentInfo();
        }

        height = this.text.fontHeight + 2;
        int scaleHeight = this.client.getWindow().getScaledHeight();
        int scaleWidth = this.client.getWindow().getScaledWidth();
        MatrixStack stack = new MatrixStack();
        if (ZiiMHud.config().direction) {
            this.text.drawWithShadow(stack, direction, scaleWidth - this.text.getWidth(direction) - 2, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if (ZiiMHud.config().netherPos) {
            this.text.drawWithShadow(stack, netherCoords, scaleWidth - this.text.getWidth(netherCoords) - 2, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if (ZiiMHud.config().pos) {
            this.text.drawWithShadow(stack, coords, scaleWidth - this.text.getWidth(coords) - 2, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if (ZiiMHud.config().biome) {
            String biome = "Biome: §7" + this.player.getEntityWorld().getBiome(this.player.getBlockPos()).getName().getString();
            this.text.drawWithShadow(stack, biome, scaleWidth - this.text.getWidth(biome) - 2, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if (ZiiMHud.config().xp) {
            float percent = this.player.experienceProgress * 100;
            String xp = String.format("XP:§7 %.0f%%", percent);
            this.text.drawWithShadow(stack, xp, scaleWidth - this.text.getWidth(xp) - 2, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }

        if (this.client.player != null && ZiiMHud.config().effects) {
            Map<StatusEffect, StatusEffectInstance> effects = this.client.player.getActiveStatusEffects();
            for (Map.Entry<StatusEffect, StatusEffectInstance> effect : effects.entrySet()) {
                String effectName = I18n.translate(effect.getKey().getTranslationKey());
                String effectLvl = "";
                if (effect.getValue().getAmplifier() >= 1 && effect.getValue().getAmplifier() <= 9) {
                    effectLvl = I18n.translate("enchantment.level." + (effect.getValue().getAmplifier() + 1));
                }
                String effectDuration = secondsToString(effect.getValue().getDuration() / 20);

                int color = effect.getKey().getColor();

                String effectString = String.format("%s §7%s %s", effectDuration, effectName, effectLvl);

                this.text.drawWithShadow(stack, effectString, scaleWidth - this.text.getWidth(effectString) - 2, scaleHeight - height, color);
                scaleHeight -= height;
            }
        }
    }

    private void drawLeftStats() {
        MatrixStack stack = new MatrixStack();

        height = this.text.fontHeight + 2;
        int scaleHeight = this.client.getWindow().getScaledHeight();
        int scaleWidth = 2;
        String ip = "IP: Local";
        if (this.player.) {
            ip = this.player.getServer().getServerIp();
        }

        String fps = "FPS: " + this.client.fpsDebugString.substring(0, this.client.fpsDebugString.indexOf(" "));

        if(ZiiMHud.config().ip) {
            this.text.drawWithShadow(stack, ip, scaleWidth, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if(ZiiMHud.config().fps) {
            this.text.drawWithShadow(stack, fps, scaleWidth, scaleHeight - height, 0xFFFFFF);
            scaleHeight -= height;
        }
        if(ZiiMHud.config().ping) {
            if (this.player.getEntityWorld() instanceof ServerWorld) {
                String ping = "Ping: " + MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(MinecraftClient.getInstance().player.getUuid()).getLatency();
                this.text.drawWithShadow(stack, ping, scaleWidth, scaleHeight - height, 0xFFFFFF);
                scaleHeight -= height;
            }
        }
    }

    private void drawEquipmentInfo() {

        List<ItemStack> equippedItems = new ArrayList<>(this.player.inventory.armor);


        int itemWidth = this.client.getWindow().getScaledWidth() / 2 + 10;

        int itemSize = this.text.fontHeight + 4;
        int i = 0;
        // Draw in order Helmet -> Breastplate -> Leggings -> Boots
        for (ItemStack equippedItem : Lists.reverse(equippedItems)) {
            if (equippedItem.getItem().equals(Blocks.AIR.asItem())) {
                continue;
            }
            i++;
            int x = itemWidth + i * 8 - 9;
            int height = this.client.getWindow().getScaledHeight() - 55;
            this.client.getItemRenderer().renderInGuiWithOverrides(this.player, equippedItem, x, height);
            this.client.getItemRenderer().renderGuiItemOverlay(this.text, equippedItem, x, height);

            itemWidth += itemSize;
        }
    }

    private String zeroPadding(int number) {
        return (number >= 10) ? Integer.toString(number) : String.format("0%s", number);
    }

    private String secondsToString(int pTime) {
        final int min = pTime / 60;
        final int sec = pTime - (min * 60);

        final String strMin = zeroPadding(min);
        final String strSec = zeroPadding(sec);
        return String.format("%s:%s", strMin, strSec);
    }

    private String getCoords() {
        double x = player.getX(), z = player.getZ();

        if (player.getEntityWorld().getRegistryKey().equals(World.NETHER)) {
            x = player.getX() * 8;
            z = player.getZ() * 8;
        }

        return String.format("%.1f %.1f %.1f", x, player.getY(), z);
    }

    private String getDirection() {
        Direction facing = this.player.getHorizontalFacing();
        float yaw = this.player.yaw;
        float pitch = this.player.pitch;
        String dir;
        switch (facing) {
            case NORTH:
                dir = "-Z";
                break;
            case SOUTH:
                dir = "+Z";
                break;
            case WEST:
                dir = "-X";
                break;
            case EAST:
                dir = "+X";
                break;
            default:
                dir = "Invalid";
        }

        return String.format("%s %s §7(%.1f, %.1f)", facing.name(), dir, MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch));
    }

    private String getNetherCoords() {
        double x = player.getX(), z = player.getZ();
        if (player.getEntityWorld().getRegistryKey().equals(World.OVERWORLD)) {
            x = player.getX() / 8;
            z = player.getZ() / 8;
        }

        return String.format("%.1f %.1f %.1f", x, player.getY(), z);
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }
}
