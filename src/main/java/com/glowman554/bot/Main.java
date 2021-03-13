package com.glowman554.bot;

import com.glowman554.bot.command.CommandManager;
import com.glowman554.bot.command.impl.*;
import com.glowman554.bot.permission.PermissionManager;
import com.glowman554.bot.plugin.PluginLoader;
import com.glowman554.bot.utils.FileUtils;
import com.glowman554.bot.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

    public static JDA jda;

    public static CommandManager commandManager = new CommandManager("#");
    public static PermissionManager permissionManager = new PermissionManager("roles.json", "users.json");
    private static final MessageReceiver messageReceiver = new MessageReceiver();
    public static PluginLoader pluginLoader;

    public static void main(String[] args) throws Exception {

        pluginLoader = new PluginLoader();

        JDABuilder jdaBuilder = JDABuilder.createDefault(FileUtils.readFile("token.txt"));
        jda = jdaBuilder.build();

        jda.addEventListener(new Logger());
        jda.addEventListener(messageReceiver);

        commandManager.registerCommand("exit", "Exit the bot!", new ExitCommand());
        commandManager.registerCommand("miner", "Lets mine coins!", new MinerCommand());
        commandManager.registerCommand("role", "Manage roles!", new RoleCommand());
        commandManager.registerCommand("exec", "Execute shell commands!", new ExecCommand());
        commandManager.registerCommand("status", "Set the status of the bot!", new StatusCommand());
        commandManager.registerCommand("wikipedia", "Search wikipedia!", new WikipediaCommand());
        commandManager.registerCommand("exec-js", "Execute JavaScript!", new ExecJSCommand());
        commandManager.registerCommand("plugin", "Load a JavaScript plugin!", new PluginCommand());
        commandManager.registerCommand("plugin-url", "Load a JavaScript plugin from an URL!", new PluginURLCommand());

        pluginLoader.enableAll();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            pluginLoader.disableAll();
        }));
    }

    public static void setDefaultRP() {
        jda.getPresence().setActivity(Activity.playing("Use #help"));
    }
}
