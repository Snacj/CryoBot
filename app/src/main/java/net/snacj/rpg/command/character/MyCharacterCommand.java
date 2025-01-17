package net.snacj.rpg.command.character;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * This class is responsible for handling the my character command.
 * It allows the user to see their character.
 */
public class MyCharacterCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    /**
     * This method executes the my character command.
     * It allows the user to see their character.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event){
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();

        String[] character;
        character = dbUtil.getChracterFromUser(userId);
        long id = Long.parseLong(character[0]);
        String name= character[1];
        int age = Integer.parseInt(character[2]);
        String species = character[3];
        String rank = character[4];
        String profession = character[5];
        String shipAssignment = character[6];
        String location = character[7];

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Your Character:");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setFooter("Powered by Snacj.com");
        embedBuilder.addField("Name:", character[1], false);
        embedBuilder.addField("Age:", character[2], false);
        embedBuilder.addField("Species:", character[3], false);
        embedBuilder.addField("Rank:", character[4], false);
        embedBuilder.addField("Profession:", character[5], false);
        embedBuilder.addField("Ship Assignment:", character[6], false);
        embedBuilder.addField("Location:", character[7], false);
        event.deferReply().queue(hook -> {
            hook.editOriginalEmbeds(embedBuilder.build()).queue();
        });
    }
}
