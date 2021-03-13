package com.glowman554.bot.permission;

import com.glowman554.bot.utils.FileUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;

public class PermissionManager {

    public final String role_file;
    public final String user_file;

    public PermissionManager(String role_file, String user_file) {
        this.role_file = role_file;
        this.user_file = user_file;
    }

    public boolean hasPermission(String perm, User user) throws IOException {
        String user_file_read = FileUtils.readFile(this.user_file);
        JsonParser jsonParser = new JsonParser();
        JsonElement user_data = jsonParser.parse(user_file_read);

        if (user_data.getAsJsonObject().has(user.getId())) {
            String role = user_data.getAsJsonObject().get(user.getId()).getAsString();

            String role_file_read = FileUtils.readFile(this.role_file);

            JsonElement role_data = jsonParser.parse(role_file_read);

            if(role_data.getAsJsonObject().has(role)) {

                if (role_data.getAsJsonObject().get(role).getAsJsonObject().has(perm)) {
                    return role_data.getAsJsonObject().get(role).getAsJsonObject().get(perm).getAsBoolean();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getRole(User user) throws IOException {
        String user_file_read = FileUtils.readFile(this.user_file);
        JsonParser jsonParser = new JsonParser();
        JsonElement user_data = jsonParser.parse(user_file_read);

        if (user_data.getAsJsonObject().has(user.getId())) {
            String role = user_data.getAsJsonObject().get(user.getId()).getAsString();
            return role;
        } else {
            return "None";
        }
    }

    public void setRole(String role, String who) throws IOException {
        JsonParser p = new JsonParser();
        JsonElement e = p.parse(FileUtils.readFile(this.user_file));

        e.getAsJsonObject().addProperty(who, role);

        FileUtils.writeFile(e.getAsJsonObject().toString(), this.user_file);
    }
}
