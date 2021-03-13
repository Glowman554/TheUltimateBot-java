package com.glowman554.bot.utils;

public class ArrayUtils {
    public static String arrayToString(String[] array) {
        String result = "";
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                result = result + " ";
            }
            String item = array[i];
            result = result + item;
        }
        return result;
    }
}
