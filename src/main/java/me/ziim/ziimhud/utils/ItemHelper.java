package me.ziim.ziimhud.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.ziim.ziimhud.Ziimhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper {
    MinecraftClient mc = Ziimhud.mc;

    public static int getCount(MinecraftClient client, ItemStack stack) {
        if (client.player == null) return 0;
        PlayerInventory inv = client.player.getInventory();
        ItemStack compare = stack.copy();
        compare.setCount(0);
        for (int i = 0; i < inv.size(); i++) {
//            System.out.println(inv.getStack(i) + " : " + stack);
            if (inv.getStack(i).isItemEqualIgnoreDamage(stack)) {
//                System.out.println("Equals");
                compare.setCount(compare.getCount() + inv.getStack(i).getCount());
            }
        }
        return compare.getCount();
    }

    public static List<ItemStack> getArmour(MinecraftClient client) {
        List<ItemStack> armour = new ArrayList<>();
        if (client.player == null) return armour;
        PlayerInventory inv = client.player.getInventory();
        inv.armor.forEach(stack -> {
            if (!stack.isItemEqualIgnoreDamage(new ItemStack(Items.AIR))) {
                armour.add(stack);
            }
        });
        return armour;
    }

    public static List<ItemStack> getEquiped(MinecraftClient client) {
        List<ItemStack> combined = new ArrayList<>();
        List<ItemStack> equiped = new ArrayList<>();
        if (client.player == null) return equiped;
        PlayerInventory inv = client.player.getInventory();
        combined.addAll(inv.armor);
        combined.add(inv.getMainHandStack());
        combined.addAll(inv.offHand);
        for (ItemStack stack : combined) {
            if (stack.isItemEqualIgnoreDamage(new ItemStack(Items.AIR))) {
                continue;
            }
            equiped.add(stack);
        }
        return equiped;
    }

    public static void drawItem(MatrixStack matrices, MinecraftClient client, ItemStack item, int x, int y, boolean text) {
        client.getItemRenderer().renderInGuiWithOverrides(item, x, y);
        if (text) {
            client.getItemRenderer().renderGuiItemOverlay(client.textRenderer, item, x, y);
        }
    }
}
