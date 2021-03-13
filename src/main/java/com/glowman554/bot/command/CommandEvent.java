package com.glowman554.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandEvent {
    public final String message;
    public final String command;
    public final String[] args;

    public final MessageReceivedEvent event;

    public CommandEvent(String message, String command, String[] args, MessageReceivedEvent event) {
        this.message = message;
        this.command = command;
        this.args = args;
        this.event = event;
    }

    public static String[] getArguments(String[] array) {

        if (array.length < 2) {
            return new String[0];
        }

        String result = "";
        for (int i = 1; i < array.length; i++) {
            if (i > 1) {
                result = result + " ";
            }
            String item = array[i];
            result = result + item;
        }
        return result.split(" ");
    }

    public void CommandFail() {
        this.sendMessage("Something is wrong!\nPlease try help.");
    }

    public void PermFail() {
        this.sendMessage("You cant do that!");
    }

    public void sendMessage(String what) {
        this.event.getChannel().sendMessage(what).queue();
    }
}
