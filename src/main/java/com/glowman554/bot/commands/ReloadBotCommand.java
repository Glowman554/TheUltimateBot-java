package com.glowman554.bot.commands;

import com.glowman554.bot.tools.Command;
import com.glowman554.bot.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReloadBotCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length != 1) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        Main.bot.setup("alice", "resources");
        e.getChannel().sendMessage("Reloaded ChatBot successfully").queue();
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #reload-bot").queue();
    }
}
