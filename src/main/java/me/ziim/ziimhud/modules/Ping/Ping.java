package me.ziim.ziimhud.modules.Ping;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class Ping extends AbstractWidget {
    public static final Identifier ID = new Identifier("ziimhud", "ping");

    public Ping() {
        super();
    }

    @Override
    public String getData() {
        PlayerListEntry playerListEntry = Ziimhud.mc.getNetworkHandler().getPlayerListEntry(Ziimhud.mc.player.getUuid());
        if (playerListEntry != null) {
            return String.format("Ping: %d", playerListEntry.getLatency());
        }
        return "Ping 0";
    }

    @Override
    public void renderWidget(MatrixStack matrices) {
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

    @Override
    public String getText() {
        return "Ping";
    }

    @Override
    public Identifier getID() {
        return ID;
    }
}
