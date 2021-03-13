package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;
import net.dv8tion.jda.api.entities.Activity;

public class StatusCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {
        if(event.args.length < 1) {
            event.CommandFail();
            return;
        }

        if(!Main.permissionManager.hasPermission("status", event.event.getAuthor())) {
            event.PermFail();
            return;
        }

        String status = ArrayUtils.arrayToString(event.args);
        event.sendMessage("Setting status: " + status);

        Main.jda.getPresence().setActivity(Activity.playing(status));
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception{
        event.sendMessage("Use status [what] to set the status of the bot!");
    }
}
