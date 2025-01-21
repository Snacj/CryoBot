package net.snacj.rpg.command.character;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

/**
 * This class is responsible for handling the my character command.
 * It allows the Member to see their character.
 */
public class MyCharacterCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    /**
     * This method executes the my character command.
     * It allows the Member to see their character.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event){
        Member member = event.getMember();
        if(member == null) return;
        long MemberId = member.getIdLong();

        String[] character;
        character = dbUtil.getCharacterFromMember(MemberId);
        long id = Long.parseLong(character[0]);
        String name = character[1];
        int age = Integer.parseInt(character[2]);
        String level = character[3];
        String species = character[4];
        String status = character[5];
        String shipAssignment = character[6];
        String location = character[7];

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Your Character:");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setFooter("Powered by Snacj.com");
        embedBuilder.addField("Name:", name, false);
        embedBuilder.addField("Age:", ""+age, false);
        embedBuilder.addField("Level:", ""+level, false);
        embedBuilder.addField("Species:", species, false);
        embedBuilder.addField("Status:", status, false);
        embedBuilder.addField("Ship Assignment:", shipAssignment, false);
        embedBuilder.addField("Location:", location, false);
        event.deferReply().queue(hook -> {
            hook.editOriginalEmbeds(embedBuilder.build()).queue();
        });
    }
}
