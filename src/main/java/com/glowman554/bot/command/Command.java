package com.glowman554.bot.command;

public interface Command {
    void execute(CommandEvent event) throws Exception;

    void on_register();

    void on_help(CommandEvent event) throws Exception;
}
