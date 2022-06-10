package me.ziim.ziimhud.modules.CoordModules;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.awt.*;

public class Pos extends AbstractWidget {
    private final Identifier ID = new Identifier("ziimhud", "pos");

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
        getText().styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));
        getData().styled(style -> style.withColor(TextColor.fromRgb(getStorage().dataColor.getPacked())));
        DrawableHelper.drawCenteredText(matrices, client.textRenderer, getText().append(getData()), pos.getX() + (Math.round(width) / 2), pos.getY() + (Math.round((float) height / 2)) - 4, getStorage().textColor.getPacked());
        matrices.pop();
    }

    @Override
    public MutableText getData() {
        MutableText data;

        if (client.player != null) {
            data = Text.literal(String.format("%s", getCoords(client.player)));
        } else {
            data = Text.literal("0,0,0");
        }
        data.styled(style -> style.withColor(TextColor.fromRgb(getStorage().dataColor.getPacked())));
        return data;
    }

    private String getCoords(ClientPlayerEntity player) {
        double x = player.getX(), z = player.getZ();

        if (player.getEntityWorld().getRegistryKey().equals(World.NETHER)) {
            x = player.getX() * 8;
            z = player.getZ() * 8;
        }

        return String.format("%.1f %.1f %.1f", x, player.getY(), z);
    }

    @Override
    public MutableText getText() {
        MutableText text = Text.literal("Pos: ");
        text.styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));
        return text;
    }

    @Override
    public Storage getStorage() {
        return Ziimhud.storage.posStorage;
    }

    public static class Storage extends AbstractStorage {
        public Storage() {
            x = 1f;
            y = 0.96f;
            scale = 1f;
            enabled = true;
            textColor = new ColorHelper(Color.WHITE);
            dataColor = new ColorHelper(Color.GRAY);
        }
    }
}
