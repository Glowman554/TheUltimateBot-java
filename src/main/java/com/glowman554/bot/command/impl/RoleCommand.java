package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class RoleCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {
        if(event.args.length < 1) {
            event.CommandFail();
            return;
        }

        switch (event.args[0]) {
            case "get":
                if(event.args.length != 1) {
                    event.CommandFail();
                    return;
                }

                String role = Main.permissionManager.getRole(event.event.getAuthor());

                event.sendMessage("`Your role is: " + role + "!`");
                break;

            case "set":
                if(event.args.length != 3) {
                    event.CommandFail();
                    return;
                }

                if(!Main.permissionManager.hasPermission("role", event.event.getAuthor())) {
                    event.PermFail();
                    return;
                }

                String id = event.args[1].replace("<@!", "").replace(">", "");

                event.sendMessage("`Setting role of " + event.args[1] + " to " + event.args[2] + "!`");

                Main.permissionManager.setRole(event.args[2], id);
                break;

            default:
                event.CommandFail();
                break;
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Role help");

        embedBuilder.addField("get", "Get your role!", false);
        embedBuilder.addField("set", "Set a role!", false);

        event.event.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
