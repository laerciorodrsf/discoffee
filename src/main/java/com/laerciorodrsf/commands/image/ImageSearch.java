package com.laerciorodrsf.commands.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.laerciorodrsf.services.BraveImageScraper;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImageSearch extends ListenerAdapter {

    private final BraveImageScraper scraper;
    private final Map<String, ImageSession> sessions = new HashMap<>();

    public ImageSearch(BraveImageScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("imagem")) {
            return;
        }

        String query = event.getOption("query").getAsString();

        try {
            List<String> images = scraper.search(query);

            if (images.isEmpty()) {
                event.reply("Nenhuma imagem encontrada.").queue();
                return;
            }

            long userId = event.getUser().getIdLong();
            
            ImageSession session = new ImageSession(images, query, userId);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(session.getQuery())
                    .setImage(images.get(0))
                    .setFooter(session.getCurrentIndex() + 1 + "/" + session.getTotalImages());

            Button previous = Button.secondary("imagem:previous", "◀️");
            Button next = Button.primary("imagem:next", "▶️");

            event.replyEmbeds(embed.build()).addComponents(
                    ActionRow.of(
                            previous,
                            next))
                    .queue(hook -> {
                        hook.retrieveOriginal().queue(message -> {
                            String messageId = message.getId();

                            sessions.put(messageId, session);
                        });
                    });

        } catch (Exception e) {
            e.printStackTrace();

            event.reply("Ocorreu um erro ao pesquisar as imagens.").queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String messageId = event.getMessageId();

        ImageSession session = sessions.get(messageId);

        if (event.getUser().getIdLong() != session.getUserId()) {
            event.reply("Você não pode controlar esta pesquisa.")
                .setEphemeral(true)
                .queue();

            return;
        }

        if (event.getComponentId().equals("imagem:next")) {
            session.next();

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(session.getQuery())
                    .setImage(session.getCurrentImage())
                    .setFooter(session.getCurrentIndex() + 1 + "/" + session.getTotalImages());

            event.editMessageEmbeds(embed.build()).queue();
        }

        if (event.getComponentId().equals("imagem:previous")) {
            session.previous();

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(session.getQuery())
                    .setImage(session.getCurrentImage())
                    .setFooter(session.getCurrentIndex() + 1 + "/" + session.getTotalImages());

            event.editMessageEmbeds(embed.build()).queue();
        }
    }
}
