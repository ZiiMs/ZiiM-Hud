package me.ziim.hud.client;


import com.google.common.collect.Lists;
import me.ziim.hud.ZiiMHud;
import me.ziim.hud.config.ZiiMHudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class DrawUI implements Drawable {
    private final MinecraftClient client;
    private final TextRenderer text;
    int height;
    private PlayerEntity player;

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
        if (ZiiMHud.config().armor) {
            drawEquipmentInfo();
        }


        int scaleWidth = this.client.getWindow().getScaledWidth();
        MatrixStack stack = new MatrixStack();

        if (ZiiMHud.config().menuPositionRight == ZiiMHudConfig.MenuPositionOptions.BOTTOM) {
            height = this.text.fontHeight + 2;
            int scaleHeight = this.client.getWindow().getScaledHeight();
            if (ZiiMHud.config().direction) {
                String direction = getDirection();
                String direction2 = String.format("(%.1f, %.1f)", MathHelper.wrapDegrees(this.player.yaw), MathHelper.wrapDegrees(this.player.pitch));
                drawWithShadowconcat(stack, direction, scaleWidth - this.text.getWidth(direction2) - 2, scaleHeight - height, Color.white, stack, direction2, Color.gray, false);
                scaleHeight -= height;
            }
            if (ZiiMHud.config().netherPos) {
                String netherCoords = "Nether Pos: ";
                String netherCoords2 = getNetherCoords();
                drawWithShadowconcat(stack, netherCoords, scaleWidth - this.text.getWidth(netherCoords2) - 2, scaleHeight - height, Color.white, stack, netherCoords2, Color.gray, false);
                scaleHeight -= height;
            }
            if (ZiiMHud.config().pos) {
                String coords = "Pos: ";
                String coords2 = getCoords();
                drawWithShadowconcat(stack, coords, scaleWidth - this.text.getWidth(coords2) - 2, scaleHeight - height, Color.white, stack, coords2, Color.gray, false);
                scaleHeight -= height;
            }
            if (ZiiMHud.config().biome) {
                String biome = "Biome: ";
                String biome2 = this.player.getEntityWorld().getBiome(this.player.getBlockPos()).getName().getString();
                drawWithShadowconcat(stack, biome, scaleWidth - this.text.getWidth(biome2) - 2, scaleHeight - height, Color.white, stack, biome2, Color.gray, false);
                scaleHeight -= height;
            }
            if (ZiiMHud.config().xp) {
                float percent = this.player.experienceProgress * 100;
                String xp = "XP: ";
                String xp2 = String.format("%.0f%%", percent);
                if (!this.player.isCreative()) {
                    drawWithShadowconcat(stack, xp, scaleWidth - this.text.getWidth(xp2) - 2, scaleHeight - height, Color.white, stack, xp2, Color.gray, false);
                    scaleHeight -= height;
                }
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

                    String effectString = effectDuration;
                    String effectString2 = String.format(" %s %s", effectName, effectLvl);

                    drawWithShadowconcat(stack, effectString, scaleWidth - this.text.getWidth(effectString2) - 2, scaleHeight - height, color, stack, effectString2, Color.gray, false);
                    scaleHeight -= height;
                }
            }
        } else if (ZiiMHud.config().menuPositionRight == ZiiMHudConfig.MenuPositionOptions.TOP) {
            height = this.text.fontHeight + 2;
            int scaleHeight = 2;
            if (ZiiMHud.config().direction) {
                String direction = getDirection();
                String direction2 = String.format("(%.1f, %.1f)", MathHelper.wrapDegrees(this.player.yaw), MathHelper.wrapDegrees(this.player.pitch));
                drawWithShadowconcat(stack, direction, scaleWidth - this.text.getWidth(direction2) - 2, scaleHeight, Color.white, stack, direction2, Color.gray, false);
                scaleHeight += height;
            }
            if (ZiiMHud.config().netherPos) {
                String netherCoords = "Nether Pos: ";
                String netherCoords2 = getNetherCoords();
                drawWithShadowconcat(stack, netherCoords, scaleWidth - this.text.getWidth(netherCoords2) - 2, scaleHeight,  Color.white, stack, netherCoords2, Color.gray, false);
                scaleHeight += height;
            }
            if (ZiiMHud.config().pos) {
                String coords = "Pos: ";
                String coords2 = getCoords();
                drawWithShadowconcat(stack, coords, scaleWidth - this.text.getWidth(coords2) - 2, scaleHeight, Color.white, stack, coords2, Color.gray, false);
                scaleHeight += height;
            }
            if (ZiiMHud.config().biome) {
                String biome = "Biome: ";
                String biome2 = this.player.getEntityWorld().getBiome(this.player.getBlockPos()).getName().getString();
                drawWithShadowconcat(stack, biome, scaleWidth - this.text.getWidth(biome2) - 2, scaleHeight, Color.white, stack, biome2, Color.gray, false);
                scaleHeight += height;
            }
            if (ZiiMHud.config().xp) {
                float percent = this.player.experienceProgress * 100;
                String xp = "XP: ";
                String xp2 = String.format("%.0f%%", percent);
                if (!this.player.isCreative()) {
                    drawWithShadowconcat(stack, xp, scaleWidth - this.text.getWidth(xp2) - 2, scaleHeight, Color.white, stack, xp2, Color.gray, false);
                    scaleHeight += height;
                }
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

                    String effectString = effectDuration;
                    String effectString2 = String.format(" %s %s", effectName, effectLvl);

                    drawWithShadowconcat(stack, effectString, scaleWidth - this.text.getWidth(effectString2) - 2, scaleHeight, color, stack, effectString2, Color.gray, false);
                    scaleHeight += height;
                }
            }
        }
    }

    private void drawLeftStats() {
        MatrixStack stack = new MatrixStack();
        if (ZiiMHud.config().menuPositionLeft == ZiiMHudConfig.MenuPositionOptions.BOTTOM) {
            height = this.text.fontHeight + 2;
            int scaledHeight = this.client.getWindow().getScaledHeight();
            int scaleWidth = 2;
            if (ZiiMHud.config().ping) {
                if (this.client.getCurrentServerEntry() != null) {
                    PlayerListEntry entry = this.client.getNetworkHandler().getPlayerListEntry(this.player.getUuid());
                    if(entry != null) {
                        String ping = "Ping: ";
                        String ping2 = String.valueOf(entry.getLatency());
                        drawWithShadowconcat(stack, ping, scaleWidth, scaledHeight - height, Color.white, stack, ping2, Color.gray, true);
                        scaledHeight -= height;
                    }
                }
            }
            if (ZiiMHud.config().ip) {
                if (this.client.getCurrentServerEntry() != null) {
                    this.player.getEntityWorld().getServer();
                    String ip = "IP: ";
                    String ip2 = this.client.getCurrentServerEntry().address;
                    drawWithShadowconcat(stack, ip, scaleWidth, scaledHeight - height, Color.white, stack, ip2, Color.gray, true);
                    scaledHeight -= height;
                }
            }
            if (ZiiMHud.config().fps) {
                
                String fps = "FPS: ";
                String fps2 = this.client.fpsDebugString.substring(0, this.client.fpsDebugString.indexOf(" "));
                drawWithShadowconcat(stack, fps, scaleWidth, scaledHeight - height, Color.white, stack, fps2, Color.gray, true);
                scaledHeight -= height;
            }
            if (ZiiMHud.config().time) {
                SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
                Date date = new Date();
                String time = "Time: ";
                String time2 = formatter.format(date);
                drawWithShadowconcat(stack, time, scaleWidth, scaledHeight - height, Color.white, stack, time2, Color.gray, true);
                scaledHeight -= height;
            }
        } else if (ZiiMHud.config().menuPositionLeft == ZiiMHudConfig.MenuPositionOptions.TOP) {
            height = this.text.fontHeight + 2;
            int scaledHeight = 2;
            int scaleWidth = 2;
            if (ZiiMHud.config().fps) {
                String fps = "FPS: ";
                String fps2 = this.client.fpsDebugString.substring(0, this.client.fpsDebugString.indexOf(" "));
                drawWithShadowconcat(stack, fps, scaleWidth, scaledHeight, Color.white, stack, fps2, Color.gray, true);
                scaledHeight += height;
            }
            if (ZiiMHud.config().ip) {
                if (this.client.getCurrentServerEntry() != null) {
                    String ip = "IP: ";
                    String ip2 = this.client.getCurrentServerEntry().address;
                    drawWithShadowconcat(stack, ip, scaleWidth, scaledHeight, Color.white, stack, ip2, Color.gray, true);
                    scaledHeight += height;
                }
            }
            if (ZiiMHud.config().ping) {
                if (this.client.getCurrentServerEntry() != null) {
                    PlayerListEntry entry = this.client.getNetworkHandler().getPlayerListEntry(this.player.getUuid());
                    if (entry != null) {
                        String ping = "Ping: ";
                        String ping2 = String.valueOf(entry.getLatency());
                        drawWithShadowconcat(stack, ping, scaleWidth, scaledHeight, Color.white, stack, ping2, Color.gray, true);
                        scaledHeight += height;
                    }
                }
            }
            if (ZiiMHud.config().time) {
                SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
                Date date = new Date();
                String time = "Time: ";
                String time2 = formatter.format(date);
                drawWithShadowconcat(stack, time, scaleWidth, scaledHeight, Color.white, stack, time2, Color.gray, true);
                scaledHeight += height;
            }
        }
    }

    private void drawWithShadowconcat(MatrixStack matrix1, String text1, float x, float y, int color1, MatrixStack matrix2, String text2, int color2, boolean left) {
        if (left) {
            this.text.drawWithShadow(matrix1, text1, x, y, color1);
            this.text.drawWithShadow(matrix2, text2, x + this.text.getWidth(text1), y, color2);
        } else {
            this.text.drawWithShadow(matrix1, text1, x - this.text.getWidth(text1), y, color1);
            this.text.drawWithShadow(matrix2, text2, x, y, color2);
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
            if (this.player.isCreative()) {
                height = this.client.getWindow().getScaledHeight() - 40;
            }

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

        return String.format("%s %s ", facing.name(), dir);
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
