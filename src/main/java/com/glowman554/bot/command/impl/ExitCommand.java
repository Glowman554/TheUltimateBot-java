package com.glowman554.bot.command.impl;

import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;

public class ExitCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.args.length != 0) {
            event.CommandFail();
            return;
        }


        System.exit(0);
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {

    }
}
