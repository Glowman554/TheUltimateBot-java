package com.glowman554.bot.commands;

import com.glowman554.bot.tools.Command;
import com.glowman554.bot.utils.Utils;
import com.glowman554.bot.utils.Wikipedia;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WikipediaCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String query = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        Wikipedia wiki = new Wikipedia(query);
        if(wiki.getData()) {
            String result = wiki.getExtractText();
            e.getChannel().sendMessage(result).queue();
        } else {
            e.getChannel().sendMessage("OMG, Something went terribly wrong.").queue();
        }

    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #wikipedia [what]").queue();
    }
}
