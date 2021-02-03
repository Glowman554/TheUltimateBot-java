package com.glowman554.bot.utils;

import com.glowman554.bot.Main;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.util.Scanner;

public class Utils {
    public static String readFile(String read_file) throws IOException {
        FileReader fileReader = new FileReader(read_file);

        String file = "";

        int i;
        while((i = fileReader.read()) != -1) {
            file += (char) i;
        }
        return file;
    }

    public static String getToken(String read_file) throws IOException {
        JsonParser p = new JsonParser();
        JsonElement e = p.parse(readFile(read_file));
        return e.getAsJsonObject().get("token").getAsString();
    }

    public static String getOwner(String read_file) throws IOException {
        JsonParser p = new JsonParser();
        JsonElement e = p.parse(readFile(read_file));
        return e.getAsJsonObject().get("owner").getAsString();
    }

    public static String getOwnerId(String read_file) throws IOException {
        JsonParser p = new JsonParser();
        JsonElement e = p.parse(readFile(read_file));
        return e.getAsJsonObject().get("owner_id").getAsString();
    }

    public static boolean isOwner(String tag, String read_file) throws IOException {
        if(getOwner(read_file).equals(tag)) {
            return true;
        }
        return false;
    }

    public static void sendOwnerMessage(String what, String config_file) throws IOException {
        User ra = Main.jda.retrieveUserById(getOwnerId(config_file)).complete();
        ra.openPrivateChannel().complete().sendMessage(what).queue();
    }

    public static String getArguments(String[] array) {
        String result = "";
        for (int i = 1; i < array.length; i++) {
            if (i > 1) {
                result = result + " ";
            }
            String item = array[i];
            result = result + item;
        }
        return result;
    }
}
