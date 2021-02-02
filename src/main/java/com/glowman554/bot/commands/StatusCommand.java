package com.glowman554.bot.commands;

import com.glowman554.bot.Main;
import com.glowman554.bot.Utils;
import com.glowman554.bot.tools.Command;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatusCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String status = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        e.getChannel().sendMessage("Setting status: " + status).queue();

        Main.jda.getPresence().setActivity(Activity.playing(status));
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #status [what]").queue();
    }
}
