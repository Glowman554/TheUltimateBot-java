package com.glowman554.bot.utils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FireExtinguisher extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().contains("\uD83D\uDD25")) {
            event.getMessage().addReaction("\uD83D\uDE92").queue();
            event.getMessage().addReaction("\uD83E\uDDEF").queue();
        }
    }
}
