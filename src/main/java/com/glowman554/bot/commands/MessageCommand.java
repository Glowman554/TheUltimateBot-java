package com.glowman554.bot.commands;

import com.glowman554.bot.Utils;
import com.glowman554.bot.tools.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class MessageCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String message = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        e.getChannel().sendMessage("Sending message: " + message).queue();

        try {
            Utils.sendOwnerMessage("[" + e.getAuthor().getAsTag() + "] " + message, "config.json");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #message [what]").queue();
    }
}
