package com.laerciorodrsf.commands;

import java.util.List;

import com.laerciorodrsf.services.BraveImageScraper;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImageSearch extends ListenerAdapter {

    private final BraveImageScraper scraper;
    private int currentIndex;
    private List<String> images;

    public ImageSearch(BraveImageScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("imagem")) {
            return ;
        }

        String query = event.getOption("query").getAsString();

        try {
            images = scraper.search(query);

            if (images.isEmpty()) {
                event.reply("Nenhuma imagem encontrada.").queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Resultado")
                    .setImage(getCurrentImage());

            Button previous = Button.secondary("imagem:previous", "◀️");
            Button next = Button.primary("imagem:next", "▶️");

            event.replyEmbeds(embed.build()).addComponents(
                ActionRow.of(
                    previous,
                    next
                )
            ).queue();

        } catch (Exception e) {
            e.printStackTrace();

            event.reply("Ocorreu um erro ao pesquisar as imagens.").queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("imagem:next")) {
            next();

            EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Resultado")
                .setImage(getCurrentImage());

            event.editMessageEmbeds(embed.build()).queue();
        }

        if (event.getComponentId().equals("imagem:previous")) {
            previous();

            EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Resultado")
                .setImage(getCurrentImage());

            event.editMessageEmbeds(embed.build()).queue();
        }
    }

    private String getCurrentImage() {
        return images.get(currentIndex);
    }

    private String next() {
        if (currentIndex < images.size() - 1) {
            currentIndex++;
        }

        return getCurrentImage();
    }

    private String previous() {
        if (currentIndex > 0) {
            currentIndex--;
        }

        return getCurrentImage();
    }

    private int getCurrentIndex() {
        return currentIndex;
    }
}
