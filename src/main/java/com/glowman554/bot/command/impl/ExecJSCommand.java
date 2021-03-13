package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExecJSCommand implements Command {

    @Override
    public void execute(CommandEvent event) throws Exception {
        if(event.args.length < 1) {
            event.CommandFail();
            return;
        }

        if(!Main.permissionManager.hasPermission("exec", event.event.getAuthor())) {
            event.PermFail();
            return;
        }

        String js = ArrayUtils.arrayToString(event.args);
        event.sendMessage("Executing JS: \n```\n" + js + "\n```");



        new Thread(() -> {
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
            try {
                scriptEngine.eval(js);
            } catch (ScriptException e) {
                event.sendMessage("OMG something went terribly wrong: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        event.sendMessage("Use exec-js [what] to execute a js script!");
    }
}
