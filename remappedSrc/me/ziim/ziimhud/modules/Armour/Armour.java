package me.ziim.ziimhud.modules.Armour;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import me.ziim.ziimhud.utils.ItemHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Armour extends AbstractWidget {
    private final Identifier Id = new Identifier("ziimhud", "armour");

    public Armour(int width, int height) {
        super(width, height);
    }

    @Override
    public Identifier getID() {
        return Id;
    }

    @Override
    protected void renderWidget(MatrixStack matrices) {
        matrices.push();
        matrices.scale(getStorage().scale, getStorage().scale, 1);
        Vector2D pos = getScaledPos();
        DrawableHelper.fill(matrices, pos.getX(), pos.getY(), pos.getX() + width + 1, pos.getY() + height + 1, color.getPacked());
        ColorHelper borderColor = new ColorHelper(55, 55, 55, 255);
        //Horizontal
        DrawableHelper.fill(matrices, pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + 1, borderColor.getPacked());
        DrawableHelper.fill(matrices, pos.getX(), pos.getY() + height, pos.getX() + width, pos.getY() + height + 1, borderColor.getPacked());

        //Vertical
        DrawableHelper.fill(matrices, pos.getX() - 1, pos.getY(), pos.getX(), pos.getY() + height + 1, borderColor.getPacked());
        DrawableHelper.fill(matrices, pos.getX() + width, pos.getY() + height + 1, pos.getX() + width + 1, pos.getY(), borderColor.getPacked());
        matrices.pop();
    }

    @Override
    public void render(MatrixStack matrices) {
        List<ItemStack> equiped = ItemHelper.getEquiped(client);
        Collections.reverse(equiped);
        Vector2D pos = getScaledPos();
        int x = pos.getX();
        matrices.push();
        matrices.scale(getStorage().scale, getStorage().scale, 1);
        for (ItemStack stack : equiped) {
            ItemHelper.drawItem(matrices, client, stack, (int) (x / getStorage().scale), (int) (pos.getY() / getStorage().scale), true);
            x += 15;
        }
        width = Math.abs(pos.getX() - x);
        matrices.pop();
    }

    @Override
    public MutableText getData() {
        return null;
    }

    @Override
    public MutableText getText() {
        return null;
    }

    @Override
    public Storage getStorage() {
        return Ziimhud.storage.armourStorage;
    }

    public static class Storage extends AbstractStorage {

        public Storage() {
            x = 0.56f;
            y = 0.92f;
            scale = 1f;
            enabled = true;
            textColor = new ColorHelper(255, 255, 255, 255);
            dataColor = new ColorHelper(Color.white);
        }
    }
}
