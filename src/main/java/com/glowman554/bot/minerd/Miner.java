package com.glowman554.bot.minerd;

import java.io.IOException;

public class Miner {
    public MinerConnection minerConnection;

    public Process process;

    public Thread miner_auto_restart;
    public boolean lock;

    public boolean remote;

    public Miner() throws IOException, InterruptedException {

        this.remote = false;
        this.lock = false;

        this.process = Runtime.getRuntime().exec("./minerd -c miner_config.json");
        Thread.sleep(1000);

        this.minerConnection = new MinerConnection("http://localhost:8800");

        if(!remote) {
            miner_auto_restart = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000 * 60 * 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[miner] Time to restart!!!\n[miner] Locking miner update thread!");
                    lock = true;
                    process.destroy();
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        process = Runtime.getRuntime().exec("./minerd -c miner_config.json");
                        Thread.sleep(1000);
                        System.out.println("[miner] Unlocking update thread!");
                        lock = false;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            miner_auto_restart.start();
        }
    }


    public Miner(String url) throws IOException, InterruptedException {
        this.remote = true;
        this.lock = false;
        this.minerConnection = new MinerConnection(url);
    }
}
