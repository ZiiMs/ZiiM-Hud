package me.ziim.ziimhud.modules.Time;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time extends AbstractWidget {
    public static final Identifier ID = new Identifier("ziimhud", "time");

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
        setDimensions(client.textRenderer.getWidth(getText().append(getData())) + 2, client.textRenderer.fontHeight + 2);
        matrices.push();
        matrices.scale(getStorage().scale, getStorage().scale, 1);
        Vector2D pos = getScaledPos();
        DrawableHelper.drawCenteredText(matrices, client.textRenderer, getText().append(getData()), pos.getX() + (Math.round(width) / 2), pos.getY() + (Math.round((float) height / 2)) - 4, getStorage().textColor.getPacked());
        matrices.pop();
    }

    @Override
    public LiteralText getData() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        Date date = new Date();
        LiteralText data = new LiteralText(formatter.format(date));
        data.styled(style -> style.withColor(TextColor.fromRgb(getStorage().dataColor.getPacked())));
        return data;
    }

    @Override
    public LiteralText getText() {
        LiteralText text = new LiteralText("Time: ");
        text.styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));

        return text;
    }

    @Override
    public Storage getStorage() {
        return Ziimhud.storage.timeStorage;
    }

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Storage extends AbstractStorage {
        public Storage() {
            x = 1f;
            y = 0.90f;
            scale = 1f;
            enabled = true;
            textColor = new ColorHelper(Color.WHITE);
            dataColor = new ColorHelper(Color.GRAY);
        }
    }
}
