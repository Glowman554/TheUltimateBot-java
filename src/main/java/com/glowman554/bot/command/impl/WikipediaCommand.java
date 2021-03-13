package com.glowman554.bot.command.impl;

import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;
import com.glowman554.bot.utils.Wikipedia;

public class WikipediaCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {

        if(event.args.length < 1) {
            event.CommandFail();
            return;
        }

        String query = ArrayUtils.arrayToString(event.args);

        Wikipedia wiki = new Wikipedia(query);
        if(wiki.getData()) {
            String result = wiki.getExtractText();
            event.sendMessage(result);
        } else {
            event.sendMessage("OMG, Something went terribly wrong.");
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        event.sendMessage("Use wikipedia [what] to search wikipedia!");
    }
}
