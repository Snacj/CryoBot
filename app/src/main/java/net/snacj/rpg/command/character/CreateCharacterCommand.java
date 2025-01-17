package net.snacj.rpg.command.character;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Objects;

/**
 * This class is responsible for handling the create character command.
 * It allows the user to create a character.
 */
public class CreateCharacterCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    /**
     * This method executes the create character command.
     * It allows the user to create a character.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();
        int age = Objects.requireNonNull(event.getOption("age")).getAsInt();
        String name = Objects.requireNonNull(event.getOption("name")).getAsString();
        String race = Objects.requireNonNull(event.getOption("race")).getAsString();
        String profession = Objects.requireNonNull(event.getOption("class")).getAsString();

        dbUtil.updateCharacter(userId, name, profession, race, age);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("Your Character:");
        embedBuilder.setFooter("Powered by Snacj.com");
        embedBuilder.addField("Name:", name, false);
        embedBuilder.addField("Age:", String.valueOf(age), false);
        embedBuilder.addField("Profession:", profession, false);
        embedBuilder.addField("Race:", race, false);
        event.deferReply().queue(hook -> {
            hook.editOriginalEmbeds(embedBuilder.build()).queue();
        });
    }
}
