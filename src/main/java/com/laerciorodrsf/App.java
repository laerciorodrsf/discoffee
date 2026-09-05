package com.laerciorodrsf;

import com.laerciorodrsf.listeners.InstagramListener;
import com.laerciorodrsf.listeners.TwitterListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App
{
    public static void main( String[] args )
    {
        JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(new InstagramListener(), new TwitterListener())
            .build(); 
    }
}
