package com.glowman554.bot;

import com.glowman554.bot.commands.HelloCommand;
import com.glowman554.bot.commands.HelpCommand;
import com.glowman554.bot.commands.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {

    public static ChatBot bot = new ChatBot();
    public static CommandManager manager = new CommandManager();

    public static void main(String[] args) throws Exception {
        String token = Utils.getTokenFromFile("token.txt");

        JDABuilder jdaBuilder =  JDABuilder.createDefault(token);
        JDA jda = jdaBuilder.build();


        manager.registerCommand("!ping", new PingCommand());
        manager.registerCommand("!hello", new HelloCommand());
        manager.registerCommand("!help", new HelpCommand());

        jda.addEventListener(manager);
        jda.addEventListener(new Logger());
    }
}
