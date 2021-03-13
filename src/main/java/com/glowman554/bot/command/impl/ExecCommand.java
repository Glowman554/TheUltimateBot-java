package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ExecCommand implements Command {
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

        String command = ArrayUtils.arrayToString(event.args);
        event.sendMessage("Executing command: " + command);

        new Thread(() -> {
            runCommand(command, event);
        }).start();

    }

    public void runCommand(String command, CommandEvent event) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                event.sendMessage(output);
            }

            process.waitFor();
            bufferedReader.close();
            event.sendMessage("Execution complete with exit code: " + String.valueOf(process.exitValue()));
            process.destroy();

        } catch (IOException | InterruptedException e) {
            event.sendMessage("Something is wrong: " + e.getMessage());
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        event.sendMessage("Use exec [what] to execute a shell command!");
    }
}
