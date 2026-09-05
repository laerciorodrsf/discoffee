package com.laerciorodrsf.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TwitterListener extends ListenerAdapter {
    private static final Pattern TWITTER_URL = Pattern.compile(
        "https?://(?:www\\.)?(?:x\\.com|twitter\\.com)/[^\\s]+"
    );

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        String content = message.getContentRaw();

        if (!TWITTER_URL.matcher(content).find()) {
            return;
        }

        message.suppressEmbeds(true).queue();

        List<String> links = convertTwitterUrl(content);

        for (String link : links) {
            event.getChannel().sendMessage(link).queue();
        }
    }

    public List<String> convertTwitterUrl(String message) {
        Matcher matcher = TWITTER_URL.matcher(message);
        
        List<String> links = new ArrayList<>();

        while (matcher.find()) {
            String link = matcher.group();

            if (link.contains("x.com")) {
                link = link.replace("x.com", "fxtwitter.com");
            } else {
                link = link.replace("twitter.com", "fxtwitter.com");
            }

            links.add(link);
        }

        return links;
    }
}
