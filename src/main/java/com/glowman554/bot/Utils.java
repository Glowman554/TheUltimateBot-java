package com.glowman554.bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    public static String getTokenFromFile(String token_file) throws FileNotFoundException {
        File file = new File(token_file);
        Scanner sc = new Scanner(file);
        String token = sc.nextLine();
        sc.close();
        return token;
    }
}
