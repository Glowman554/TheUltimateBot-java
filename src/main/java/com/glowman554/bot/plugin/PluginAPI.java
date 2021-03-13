package com.glowman554.bot.plugin;

import com.glowman554.bot.Main;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class PluginAPI {
    public static void addCommand(String command, String help, String help_long, ScriptObjectMirror command_executor) {
        Main.commandManager.registerCommand(command, help, new PluginCommand(help_long, command_executor));
    }

    public static void addCommand(String command, String help, ScriptObjectMirror command_executor) {
        Main.commandManager.registerCommand(command, help, new PluginCommand("Not specified!", command_executor));
    }
}


