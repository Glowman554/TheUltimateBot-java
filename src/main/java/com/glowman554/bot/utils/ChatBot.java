package com.glowman554.bot.utils;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatBot extends ListenerAdapter {
    public HashMap<String, Chat> chats = new HashMap<>();
    public Bot bot = new Bot(BotConfiguration.builder().name("alice").path("resources").build());


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.getAuthor().isBot()) {
            return;
        }

        if(event.isFromType(ChannelType.PRIVATE) || event.getTextChannel().getName().contains("chatbot")) {
            if(chats.containsKey(event.getChannel().getId())) {
                event.getChannel().sendMessage(chats.get(event.getChannel().getId()).multisentenceRespond(event.getMessage().getContentRaw())).queue();
            } else {
                //event.getChannel().sendMessage("Making new chat bot session please give me up to 1 minute!").queue();

                chats.put(event.getChannel().getId(), new Chat(bot));

                event.getChannel().sendMessage(chats.get(event.getChannel().getId()).multisentenceRespond(event.getMessage().getContentRaw())).queue();
            }
        }
    }
}
