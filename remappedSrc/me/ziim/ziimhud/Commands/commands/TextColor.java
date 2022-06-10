package me.ziim.ziimhud.Commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.ziim.ziimhud.Commands.Command;
import me.ziim.ziimhud.gui.AbstractWidget;
import me.ziim.ziimhud.gui.WidgetManager;
import me.ziim.ziimhud.utils.ColorHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class TextColor extends Command {

    public TextColor() {
        super("textcolor", "Changes color of text");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", StringArgumentType.string())
                .then(argument("r", IntegerArgumentType.integer())
                        .then(argument("g", IntegerArgumentType.integer())
                                .then(argument("b", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            String module = context.getArgument("module", String.class);
                                            int r = context.getArgument("r", Integer.class);
                                            int g = context.getArgument("g", Integer.class);
                                            int b = context.getArgument("b", Integer.class);
                                            AbstractWidget widget = null;
                                            for (AbstractWidget widgets : WidgetManager.INSTANCE.getEntries()) {
                                                if (widgets.getID().getPath().equalsIgnoreCase(module)) {
                                                    widget = widgets;
                                                    break;
                                                }
                                            }
                                            if (widget == null) {
                                                mc.inGameHud.getChatHud().addMessage(Text.literal("Widget not found!!"));
                                                return SINGLE_SUCCESS;
                                            }
                                            widget.setTextColor(new ColorHelper(r, g, b));
                                            mc.inGameHud.getChatHud().addMessage(Text.literal(widget.getText().asString() + " color changed to: ").append(Text.literal(r + " " + g + " " + b).styled(style -> style.withColor(net.minecraft.text.TextColor.fromRgb(new ColorHelper(r, g, b).getPacked())))));
                                            return SINGLE_SUCCESS;
                                        })))));
    }
}

