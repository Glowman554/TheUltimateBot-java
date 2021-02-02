package com.glowman554.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    public void execute(MessageReceivedEvent e);
    public void on_register();
    public void on_help(MessageReceivedEvent event);
}
