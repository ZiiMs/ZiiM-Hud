package me.ziim.ziimhud.Commands;

import com.google.common.eventbus.Subscribe;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ziim.ziimhud.Commands.commands.DataColor;
import me.ziim.ziimhud.Commands.commands.TextColor;
import me.ziim.ziimhud.events.SendMessageEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;

public class CommandManager {
    private static final CommandDispatcher<CommandSource> DISPATCHER = new CommandDispatcher<>();
    private static final CommandSource COMMAND_SOURCE = new ChatCommandSource(MinecraftClient.getInstance());

    public static void init() {
//        Ziimhud.EVENT_BUS.register(this);
        addCommand(new TextColor());
        addCommand(new DataColor());
    }

    public static void dispatch(String message) throws CommandSyntaxException {
        dispatch(message, new ChatCommandSource(MinecraftClient.getInstance()));
    }

    public static void dispatch(String message, CommandSource source) throws CommandSyntaxException {
        ParseResults<CommandSource> results = DISPATCHER.parse(message, source);
        // `results` carries information about whether or not the command failed to parse, which path was took, etc.
        // it might be useful to inspect later, before executing.
        CommandManager.DISPATCHER.execute(results);
    }

    private static void addCommand(Command command) {
        command.registerTo(DISPATCHER);
    }

    public static CommandDispatcher<CommandSource> getDispatcher() {
        return DISPATCHER;
    }

    public static CommandSource getCommandSource() {
        return COMMAND_SOURCE;
    }

    @Subscribe
    private void onMsgSend(SendMessageEvent e) {
        String msg = e.msg;
    }

    private final static class ChatCommandSource extends ClientCommandSource {
        public ChatCommandSource(MinecraftClient client) {
            super(null, client);
        }
    }


}
