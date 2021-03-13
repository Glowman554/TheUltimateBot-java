package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.utils.ArrayUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class PluginURLCommand implements Command {
    @Override
    public void execute(CommandEvent event) throws Exception {
        if(event.args.length != 1) {
            event.CommandFail();
            return;
        }

        if(!Main.permissionManager.hasPermission("plugin", event.event.getAuthor())) {
            event.PermFail();
            return;
        }


        String js = this.getData(event.args[0]);

        try {

            event.sendMessage("Loading plugin: \n```\n" + js + "\n```");
        } catch (Exception e) {
            event.sendMessage("Plugin probably to big to send!");
        }

        new Thread(() -> {
            try {
                Main.pluginLoader.loadString(js);
            } catch (Exception e) {
                event.sendMessage("OMG something went terribly wrong: " + e.getMessage());
            }
        }).start();
    }

    public String getData(String path) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(path).get().build();
        Response response = okHttpClient.newCall(request).execute();
        String data = response.body().string();
        return data;
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        event.sendMessage("Use plugin-url [url] to load a plugin from an url!");
    }
}
