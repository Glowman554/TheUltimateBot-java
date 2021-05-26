package com.glowman554.bot.minerd;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MinerConnection {

    public final String url;

    public int opt_n_threads;
    public int num_processors;
    public String rpc_url;
    public String rpc_user;
    public String algo_name;

    public float khash_total;
    public float khash_thread_avg;

    public MinerConnection(String url) throws IOException {
        this.url = url;

        String data = this.getData("/miner_info");

        JsonParser jsonParser = new JsonParser();
        JsonElement main_data = jsonParser.parse(data);

        this.opt_n_threads = main_data.getAsJsonObject().get("opt_n_threads").getAsInt();
        this.num_processors = main_data.getAsJsonObject().get("num_processors").getAsInt();
        this.rpc_url = main_data.getAsJsonObject().get("rpc_url").getAsString();
        this.rpc_user = main_data.getAsJsonObject().get("rpc_user").getAsString();
        this.algo_name = main_data.getAsJsonObject().get("algo_name").getAsString();
    }

    public String getData(String path) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(this.url + path).get().build();
        Response response = okHttpClient.newCall(request).execute();
        String data = response.body().string();
        return data;
    }

    public void update() throws IOException {
        String data = this.getData("/miner_data");

        JsonParser jsonParser = new JsonParser();
        JsonElement main_data = jsonParser.parse(data);

        this.khash_total = main_data.getAsJsonObject().get("khash_total").getAsFloat();
        this.khash_thread_avg = main_data.getAsJsonObject().get("khash_thread_avg").getAsFloat();
    }
}
