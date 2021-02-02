package com.glowman554.bot.tools;


import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;

public class ChatBot {

    public static Chat chatSession;

    public void setup(String bot_name, String path) {
        Bot bot = new Bot(BotConfiguration.builder().name(bot_name).path(path).build());
        chatSession = new Chat(bot);
    }

    public String get_answer(String what) {
        return chatSession.multisentenceRespond(what);
    }

}
