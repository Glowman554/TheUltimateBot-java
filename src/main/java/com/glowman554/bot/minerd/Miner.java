package com.glowman554.bot.minerd;

import java.io.IOException;

public class Miner {
    public MinerConnection minerConnection;

    public Process process;

    public boolean remote;

    public Miner() throws IOException, InterruptedException {

        this.remote = false;

        this.process = Runtime.getRuntime().exec("./minerd -c miner_config.json");
        Thread.sleep(1000);

        this.minerConnection = new MinerConnection("http://localhost:5050");
    }


    public Miner(String url) throws IOException, InterruptedException {
        this.remote = true;
        this.minerConnection = new MinerConnection(url);
    }
}
