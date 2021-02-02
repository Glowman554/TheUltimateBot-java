package com.glowman554.bot.commands;

import com.glowman554.bot.Command;
import com.glowman554.bot.Main;
import com.glowman554.bot.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ChatBotCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String what = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        e.getChannel().sendMessage(Main.bot.get_answer(what)).queue();
    }

    @Override
    public void on_register() {
        Main.bot.setup("alice", "resources");
    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #b [what]").queue();
    }
}
