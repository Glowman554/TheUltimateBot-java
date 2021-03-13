package com.glowman554.bot.command;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashMap;

public class CommandManager {
    public final String prefix;
    public HashMap<String, Command> commands = new HashMap<>();
    public HashMap<String, String> helps = new HashMap<>();

    public CommandManager(String prefix) {
        this.prefix = prefix;
    }


    public void onCommand(CommandEvent event) throws Exception {

        if (event.command.equals(prefix + "help")) {
            switch (event.args.length) {
                case 0:

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("TheUltimateBot help");

                    helps.forEach((key, value) -> {
                        embedBuilder.addField(key, value, false);
                    });

                    event.event.getChannel().sendMessage(embedBuilder.build()).queue();

                    break;

                case 1:
                    if (commands.get(event.args[0]) != null) {
                        commands.get(event.args[0]).on_help(event);
                    } else {
                        event.sendMessage("Command not found");
                    }
                    break;

                default:
                    event.sendMessage("Please use " + prefix + "help");
                    break;
            }
        }

        if (commands.get(event.command) != null) {
            commands.get(event.command).execute(event);
        }
    }

    public void registerCommand(String what, String help, Command c) {
        c.on_register();

        what = prefix + what;

        commands.put(what, c);
        helps.put(what, help);
        System.out.printf("[%s] Command register complete\n", what);
    }
}
