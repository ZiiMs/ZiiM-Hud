package me.ziim.ziimhud.gui;

import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AbstractHudWidget extends AbstractWidget {

    public AbstractHudWidget() {
        super(47, 13);
    }

    public AbstractHudWidget(int width, int height) {
        super(width, height);
    }

    @Override
    protected void renderWidget(MatrixStack matrices) {
        matrices.push();
        matrices.scale(scale, scale, 1);
        Vector2D pos = getScaledPos();
        DrawableHelper.fill(matrices, pos.getX(), pos.getY(), pos.getX() + width, pos.getY() + height, color.getPacked());
        DrawableHelper.drawCenteredString(matrices, client.textRenderer, getText(), pos.getX() + (Math.round(width) / 2), pos.getY() + (Math.round((float) height / 2)) - 4, ColorHelper.fromRGBA(100, 100, 100, 255));
        matrices.pop();
    }

    @Override
    public void render(MatrixStack matrices) {
        setDimensions(client.textRenderer.getWidth(getData()) + 2, client.textRenderer.fontHeight + 2);
        matrices.push();
        matrices.scale(scale, scale, 1);
        Vector2D pos = getScaledPos();
        DrawableHelper.drawCenteredString(matrices, client.textRenderer, getData(), pos.getX() + (Math.round(width) / 2), pos.getY() + (Math.round((float) height / 2)) - 4, ColorHelper.fromRGBA(100, 100, 100, 255));
        matrices.pop();
    }

    public abstract String getData();

    public abstract String getText();
}
