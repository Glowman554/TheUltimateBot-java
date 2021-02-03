package com.glowman554.bot.commands;

import com.glowman554.bot.tools.Command;
import com.glowman554.bot.utils.Utils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().split(" ").length < 2) {
            e.getChannel().sendMessage("Please use #help").queue();
            return;
        }

        String command = Utils.getArguments(e.getMessage().getContentRaw().split(" "));
        e.getChannel().sendMessage("Executing command: " + command).queue();

        new Thread(() -> {
            runCommand(command, e.getChannel());
        }).start();


    }

    public void runCommand(String command, MessageChannel c) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                c.sendMessage(output).queue();
            }

            process.waitFor();
            bufferedReader.close();
            c.sendMessage("Execution complete with exit code: " + String.valueOf(process.exitValue())).queue();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            c.sendMessage("Something is wrong: " + e.getMessage()).queue();
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Use #exec [command]").queue();
    }
}
