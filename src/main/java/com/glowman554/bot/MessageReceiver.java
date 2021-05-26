package com.glowman554.bot;

import com.glowman554.bot.command.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceiver extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.getAuthor().isBot()) {
            return;
        }

        CommandEvent commandEvent = new CommandEvent(event.getMessage().getContentRaw(), event.getMessage().getContentRaw().split(" ")[0], CommandEvent.getArguments(event.getMessage().getContentRaw().split(" ")), event);

        try {
            Main.commandManager.onCommand(commandEvent);
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("Something is wrong: " + e.getMessage()).queue();
        }
    }
}
