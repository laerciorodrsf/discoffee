package com.laerciorodrsf.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InstagramListener extends ListenerAdapter {

    private static final Pattern INSTAGRAM_URL = Pattern.compile(
            "https?://(?:www\\.)?instagram\\.com/[^\\s]+");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        List<String> links = convertInstagramUrl(message);

        for (String link : links) {
            event.getChannel().sendMessage(link).queue();
        }
    }

    public List<String> convertInstagramUrl(String message) {
        Matcher matcher = INSTAGRAM_URL.matcher(message);

        List<String> links = new ArrayList<>();

        while (matcher.find()) {
            String link = matcher.group().replace("instagram.com", "kkinstagram.com");
            links.add(link);
        }

        return links;
    }
}
