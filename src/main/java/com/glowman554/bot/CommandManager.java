package com.glowman554.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.HashMap;

public class CommandManager extends ListenerAdapter {

    public HashMap<String, Command> commands = new HashMap<>();
    public HashMap<String, String> helps = new HashMap<>();
    public HashMap<String, Integer> securities = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().getName().equals(event.getJDA().getSelfUser().getName())) {
            return;
        }

        if(event.getMessage().getContentRaw().split(" ")[0].equals("#help")) {
            switch(event.getMessage().getContentRaw().split(" ").length) {
                case 1:
                    helps.forEach((key, value) -> {
                        event.getChannel().sendMessage(key + " -> " + value).queue();
                    });
                    break;

                case 2:
                    if(commands.get(event.getMessage().getContentRaw().split(" ")[1]) != null) {
                        commands.get(event.getMessage().getContentRaw().split(" ")[1]).on_help(event);
                    } else {
                        event.getChannel().sendMessage("Command not found").queue();
                    }
                    break;

                default:
                    event.getChannel().sendMessage("Please use #help").queue();
                    break;
            }
        }

        if(commands.get(event.getMessage().getContentRaw().split(" ")[0]) != null) {
            if(securities.get(event.getMessage().getContentRaw().split(" ")[0]) == 1) {
                try {
                    if(Utils.isOwner(event.getAuthor().getAsTag())) {
                        commands.get(event.getMessage().getContentRaw().split(" ")[0]).execute(event);
                    } else {
                        event.getChannel().sendMessage("You can't do that").queue();
                    }
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            commands.get(event.getMessage().getContentRaw().split(" ")[0]).execute(event);
        }
    }

    public void registerCommand(String what, String help, Integer level, Command c) {
        c.on_register();

        commands.put(what, c);
        helps.put(what, help);
        securities.put(what, level);
        System.out.printf("[Startup] [%s] Command register complete\n", what);
    }
}
