package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;

public class PluginCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {
        if(event.args.length < 1) {
            event.CommandFail();
            return;
        }

        if(!Main.permissionManager.hasPermission("plugin", event.event.getAuthor())) {
            event.PermFail();
            return;
        }

        String js = ArrayUtils.arrayToString(event.args);

        js = js.replace("`", "");

        event.sendMessage("Loading plugin: \n```\n" + js + "\n```");


        String finalJs = js;
        new Thread(() -> {
            try {
                Main.pluginLoader.loadString(finalJs);
            } catch (Exception e) {
                event.sendMessage("OMG something went terribly wrong: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        event.sendMessage("Use plugin [code] to load a plugin!");
    }
}
