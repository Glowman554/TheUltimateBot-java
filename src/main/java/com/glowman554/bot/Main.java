package com.glowman554.bot;

import com.glowman554.bot.commands.*;
import com.glowman554.bot.tools.ChatBot;
import com.glowman554.bot.tools.CommandManager;
import com.glowman554.bot.utils.FireExtinguisher;
import com.glowman554.bot.utils.Logger;
import com.glowman554.bot.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {

    public static ChatBot bot = new ChatBot();
    public static CommandManager manager = new CommandManager();
    public static JDA jda;

    public static void main(String[] args) throws Exception {
        String token = Utils.getToken("config.json");

        JDABuilder jdaBuilder =  JDABuilder.createDefault(token);
        jda = jdaBuilder.build();


        manager.registerCommand("#ping", "Chek if bot is online", 0, new PingCommand());
        manager.registerCommand("#hello", "Get a greeting", 0, new HelloCommand());
        manager.registerCommand("#exec", "Execute a command", 1, new ExecCommand());
        manager.registerCommand("#b", "Talk to the ChatBot", 0, new ChatBotCommand());
        manager.registerCommand("#reload-bot", "Reload ChatBot", 1, new ReloadBotCommand());
        manager.registerCommand("#status", "Set the status of the bot", 0, new StatusCommand());
        manager.registerCommand("#message", "Send message to owner", 0, new MessageCommand());
        manager.registerCommand("#wikipedia", "Search wikipedia", 0, new WikipediaCommand());

        jda.addEventListener(manager);
        jda.addEventListener(new Logger());
        jda.addEventListener(new FireExtinguisher());

        jda.getPresence().setActivity(Activity.playing("Use #help"));

        Utils.sendOwnerMessage("Initialized successfully", "config.json");
    }
}
