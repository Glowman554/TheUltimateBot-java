package com.glowman554.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        String token = Utils.getTokenFromFile("token.txt");

        JDABuilder jdaBuilder =  JDABuilder.createDefault(token);
        JDA jda = jdaBuilder.build();

        jda.addEventListener(new Main());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(), event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }

        if(event.getMessage().getContentRaw().contains("\uD83D\uDD25")) {
            event.getMessage().addReaction("\uD83D\uDE92").queue();
            event.getMessage().addReaction("\uD83E\uDDEF").queue();
        }

        MessageChannel channel = event.getChannel();

        if(event.getMessage().getContentRaw().equals("!ping")) {
            channel.sendMessage("Pong!").queue();
        }

        if(event.getMessage().getContentRaw().equals("!hello")) {
            channel.sendMessage("Hello " + event.getMessage().getAuthor().getName()).queue();
        }
    }
}
