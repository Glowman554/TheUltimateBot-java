package com.glowman554.bot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Wikipedia {
    final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    String subject = null;
    String displayTitle = "";
    String extractText = "";

    public Wikipedia(String subject) {
        this.subject = subject;
    }

    public boolean getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL + subject).get().build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            JsonParser jsonParser = new JsonParser();
            JsonElement main_data = jsonParser.parse(data);

            displayTitle = main_data.getAsJsonObject().get("displaytitle").getAsString();
            extractText = main_data.getAsJsonObject().get("extract").getAsString();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getExtractText() {
        return extractText;
    }
}