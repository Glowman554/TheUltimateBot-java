package com.glowman554.bot;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    public static boolean isOwner(String tag) throws IOException {
        if(getOwner("config.json").equals(tag)) {
            return true;
        }
        return false;
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
