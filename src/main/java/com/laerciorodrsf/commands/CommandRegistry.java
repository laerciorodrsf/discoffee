package com.laerciorodrsf.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandRegistry {
    private static final String GUILD_ID = "1284302384545271938";

    public static void register(JDA jda) {
        Guild guild = jda.getGuildById(GUILD_ID);

        if (guild == null) {
            throw new IllegalStateException(
                    "Guild não encontrada: " + GUILD_ID);
        }

        guild.upsertCommand(
                Commands.slash("imagem", "Pesquisa imagens")
                        .addOption(
                                OptionType.STRING,
                                "query",
                                "O que você quer pesquisar",
                                true))
                .queue();
    }
}
