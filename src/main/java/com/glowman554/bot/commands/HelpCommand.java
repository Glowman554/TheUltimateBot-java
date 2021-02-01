package com.glowman554.bot.commands;

import com.glowman554.bot.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length != 1) {
            e.getChannel().sendMessage("Please use !help").queue();
            return;
        }

        e.getChannel().sendMessage("!help\n" +
                "!ping\n" +
                "!hello").queue();
    }

    @Override
    public void on_register() {

    }
}
