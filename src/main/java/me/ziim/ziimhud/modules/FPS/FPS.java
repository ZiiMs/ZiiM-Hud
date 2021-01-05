package me.ziim.ziimhud.modules.FPS;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import me.ziim.ziimhud.utils.EventHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.awt.*;

public class FPS extends AbstractWidget {
    private final Identifier ID = new Identifier("ziimhud", "fps");

    @Override
    public Identifier getID() {
        return ID;
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
        setDimensions(client.textRenderer.getWidth(getText().append(getData())) + 2, client.textRenderer.fontHeight + 2);
        matrices.push();
        matrices.scale(getStorage().scale, getStorage().scale, 1);
        Vector2D pos = getScaledPos();
        getData().styled(style -> style.withColor(TextColor.fromRgb(getStorage().dataColor.getPacked())));
        getText().styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));
        DrawableHelper.drawCenteredText(matrices, client.textRenderer, getText().append(getData()), pos.getX() + (Math.round(width) / 2), pos.getY() + (Math.round((float) height / 2)) - 4, getStorage().textColor.getPacked());
        matrices.pop();
    }


    @Override
    public LiteralText getData() {
        LiteralText data = new LiteralText(Integer.toString(EventHelper.getFps()));
        data.styled(style -> style.withColor(TextColor.fromRgb(getStorage().dataColor.getPacked())));
        return data;
    }

    @Override
    public LiteralText getText() {
        LiteralText text = new LiteralText("FPS: ");
        text.styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));
        return text;
    }

    @Override
    public Storage getStorage() {
        return Ziimhud.storage.fpsStorage;
    }

    public static class Storage extends AbstractWidget.AbstractStorage {
        public Storage() {
            x = 1f;
            y = 0.88f;
            scale = 1f;
            enabled = true;
            textColor = new ColorHelper(Color.WHITE);
            dataColor = new ColorHelper(Color.GRAY);
        }
    }
}
