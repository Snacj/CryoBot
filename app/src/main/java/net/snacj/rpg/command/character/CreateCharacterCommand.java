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
        String name = Objects.requireNonNull(event.getOption("name")).getAsString();
        int age = Objects.requireNonNull(event.getOption("age")).getAsInt();
        String species = Objects.requireNonNull(event.getOption("species")).getAsString();
        String rank = Objects.requireNonNull(event.getOption("rank")).getAsString();
        String profession = Objects.requireNonNull(event.getOption("profession")).getAsString();
        String shipAssignment = Objects.requireNonNull(event.getOption("ship-assignment")).getAsString();
        String location = Objects.requireNonNull(event.getOption("location")).getAsString();

        dbUtil.updateUser(userId, name, profession, species, age);
    }
}
