package com.glowman554.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class CommandManager extends ListenerAdapter {

    public HashMap<String, Command> commands = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().getName().equals(event.getJDA().getSelfUser().getName())) {
            return;
        }

        if(commands.get(event.getMessage().getContentRaw().split(" ")[0]) != null) {
            commands.get(event.getMessage().getContentRaw().split(" ")[0]).execute(event);
        }
    }

    public void registerCommand(String what, Command c) {
        commands.put(what, c);
    }
}
