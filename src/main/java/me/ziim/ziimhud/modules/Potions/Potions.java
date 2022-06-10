package me.ziim.ziimhud.modules.Potions;

import me.ziim.ziimhud.Ziimhud;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.Vector2D;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.text.DecimalFormat;

public class Potions extends AbstractWidget {
    public static final Identifier ID = new Identifier("ziimhud", "potions");
    protected TextFieldWidget textFieldWidget;

    public Potions() {
        super();
//        setScale(0.5f);
//        setXY(0,0);
    }

    @Override
    public void renderWidget(MatrixStack matrices) {
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
//        setDimensions(client.textRenderer.getWidth(getText().append(getData())) + 2, client.textRenderer.fontHeight + 2);
        matrices.push();
        matrices.scale(getStorage().scale, getStorage().scale, 1);

//        width = 0;
//        height = 0;
        int i = 1;
        for (StatusEffectInstance statusEffectInstance : client.player.getStatusEffects()) {
            Vector2D pos = getScaledPos();
            String duration;

            if(statusEffectInstance.isPermanent()) {
                duration = "Permanent";
            } else {
                var a = statusEffectInstance.getDuration() / 20;
                float b = (float)a / 60;
                a %= 60;
                if(a < 10) duration = String.format("%s:0%d", new DecimalFormat("#").format(b), a); else duration = String.format("%s:%d", new DecimalFormat("#").format(b), a);
            }
            MutableText string = Text.literal(String.format("%s %d (%s)", statusEffectInstance.getEffectType().getName().getString(), statusEffectInstance.getAmplifier(), duration));
//            width = Math.max(width, client.textRenderer.getWidth(string));
//            height += client.textRenderer.fontHeight;
            string.styled(style -> style.withColor(TextColor.fromRgb(statusEffectInstance.getEffectType().getColor())));
            setDimensions(Math.max(width, client.textRenderer.getWidth(string)), (client.textRenderer.fontHeight) + 2);
            DrawableHelper.drawTextWithShadow(matrices, client.textRenderer, string, (pos.getX() - client.textRenderer.getWidth(string)) + width - 1 , pos.getY() + (Math.round((float) height / 2)) - 4 * i, getStorage().textColor.getPacked());
            i += 2;
        }
//        setDimensions(width, (height * i));
        matrices.pop();
    }

    @Override
    public MutableText getData() {
        MutableText data = Text.literal("");
        height = 2;
        width = 0;
        for (StatusEffectInstance statusEffectInstance : client.player.getStatusEffects()) {
            MutableText string = Text.literal(String.format("%s %d (%s)\n", statusEffectInstance.getEffectType().getName().getString(), statusEffectInstance.getAmplifier(), statusEffectInstance.getDuration()));
            width = Math.max(width, client.textRenderer.getWidth(string));
            height += client.textRenderer.fontHeight;
        }


        return data;
    }

    @Override
    public MutableText getText() {
        MutableText text = Text.literal("Potions (0:00)");
        text.styled(style -> style.withColor(TextColor.fromRgb(getStorage().textColor.getPacked())));

        return text;
    }

    @Override
    public Storage getStorage() {
        return Ziimhud.storage.potionStorage;
    }

    @Override
    public Identifier getID() {
        return ID;
    }

    public static class Storage extends AbstractStorage {
        public Storage() {
            x = 1f;
            y = 0.92f;
            scale = 1f;
            enabled = true;
            textColor = new ColorHelper(Color.WHITE);
            dataColor = new ColorHelper(Color.GRAY);
        }
    }
}
