package com.glowman554.bot.commands;

import com.glowman554.bot.Command;
import com.glowman554.bot.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class ExecCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String command = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        e.getChannel().sendMessage("Executing command: " + command).queue();
        try {
            Process child = Runtime.getRuntime().exec(command);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #exec [command]").queue();
    }
}
