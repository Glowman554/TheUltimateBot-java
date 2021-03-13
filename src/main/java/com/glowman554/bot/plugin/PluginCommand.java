package com.glowman554.bot.plugin;


import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class PluginCommand implements Command {

    private final String help;
    private final ScriptObjectMirror commandExecutor;

    public PluginCommand(String help, ScriptObjectMirror commandExecutor) {
        this.help = help;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(CommandEvent event) {
        this.commandExecutor.call(this.commandExecutor, event);
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) {
        event.sendMessage(this.help);
    }
}
