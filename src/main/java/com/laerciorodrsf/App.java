package com.laerciorodrsf;

import com.laerciorodrsf.commands.CommandRegistry;
import com.laerciorodrsf.commands.ImageSearch;
import com.laerciorodrsf.listeners.InstagramListener;
import com.laerciorodrsf.listeners.TwitterListener;
import com.laerciorodrsf.services.BraveImageScraper;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App {
    public static void main(String[] args) throws InterruptedException {
        BraveImageScraper scraper = new BraveImageScraper();

        JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new InstagramListener(), new TwitterListener(), new ImageSearch(scraper))
                .build();

        jda.awaitReady();

        CommandRegistry.register(jda);
    }
}
