package com.glowman554.bot.command.impl;

import com.glowman554.bot.Main;
import com.glowman554.bot.command.Command;
import com.glowman554.bot.command.CommandEvent;
import com.glowman554.bot.minerd.Miner;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;

public class MinerCommand implements Command {
    public Miner miner = null;

    Thread minerCallback;


    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.args.length < 1) {
            event.CommandFail();
            return;
        }

        if (!Main.permissionManager.hasPermission("miner", event.event.getAuthor())) {
            event.PermFail();
            return;
        }

        switch (event.args[0]) {
            case "connect":
                if (event.args.length != 2) {
                    event.CommandFail();
                    return;
                }

                if (miner != null) {
                    event.sendMessage("Already connected to miner!");
                    return;
                }
                miner = new Miner("http://" + event.args[1]);

                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setTitle("Miner info");

                embedBuilder.addField("Algorithm", miner.minerConnection.algo_name, false);
                embedBuilder.addField("Pool", miner.minerConnection.rpc_url, false);
                embedBuilder.addField("User", miner.minerConnection.rpc_user, false);
                embedBuilder.addField("Cpu count", String.valueOf(miner.minerConnection.num_processors), true);
                embedBuilder.addField("Thread count", String.valueOf(miner.minerConnection.opt_n_threads), true);

                embedBuilder.setThumbnail("https://th.bing.com/th/id/OIF.jUfuHljOgkQRr1ooYoq6RA");

                event.event.getChannel().sendMessage(embedBuilder.build()).queue();

                this.initCallbackThread(event);

                this.minerCallback.start();

                break;

            case "disconnect":
                if (event.args.length != 1) {
                    event.CommandFail();
                    return;
                }

                if (miner == null) {
                    event.sendMessage("Not connected to miner!");
                    return;
                }
                event.sendMessage("Stopping miner!");

                Main.setDefaultRP();

                if (!miner.remote) {
                    miner.process.destroy();
                }

                miner = null;
                minerCallback.stop();

                break;

            case "start":
                if (event.args.length != 1) {
                    event.CommandFail();
                    return;
                }

                if (miner != null) {
                    event.sendMessage("Already connected to miner!");
                    return;
                }
                miner = new Miner();

                EmbedBuilder embedBuilder2 = new EmbedBuilder();

                embedBuilder2.setTitle("Miner info");

                embedBuilder2.addField("Algorithm", miner.minerConnection.algo_name, false);
                embedBuilder2.addField("Pool", miner.minerConnection.rpc_url, false);
                embedBuilder2.addField("User", miner.minerConnection.rpc_user, false);
                embedBuilder2.addField("Cpu count", String.valueOf(miner.minerConnection.num_processors), true);
                embedBuilder2.addField("Thread count", String.valueOf(miner.minerConnection.opt_n_threads), true);

                embedBuilder2.setThumbnail("https://th.bing.com/th/id/OIF.jUfuHljOgkQRr1ooYoq6RA");

                event.event.getChannel().sendMessage(embedBuilder2.build()).queue();

                this.initCallbackThread(event);

                this.minerCallback.start();
                break;

            default:
                event.CommandFail();
                break;
        }
    }

    public void initCallbackThread(CommandEvent event) {
        minerCallback = new Thread("Miner callback") {

            int retry_count = 10;

            @Override
            public void run() {
                while (true) {
                    try {
                        miner.minerConnection.update();

                        Main.jda.getPresence().setActivity(Activity.playing(String.format("%.02f khash/s - %.02f khash/s", miner.minerConnection.khash_thread_avg, miner.minerConnection.khash_total)));

                        Thread.sleep(2000);
                    } catch (Exception e) {
                        retry_count--;

                        if (retry_count == 0) {
                            event.sendMessage("Miner died!");

                            if (!miner.remote) {
                                miner.process.destroy();
                            }

                            miner = null;

                            Main.setDefaultRP();

                            minerCallback.stop();
                        } else {
                            Main.jda.getPresence().setActivity(Activity.playing(String.format("Miner ded retry's left: %d", retry_count)));
                        }
                    }
                }
            }
        };
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) throws Exception {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Miner help");
        embedBuilder.setThumbnail("https://th.bing.com/th/id/OIF.jUfuHljOgkQRr1ooYoq6RA");

        embedBuilder.addField("connect", "Connect to a remote miner!", false);
        embedBuilder.addField("disconnect", "Disconnect from remote miner or kill local miner!", false);
        embedBuilder.addField("start", "Start local miner!", false);

        event.event.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
