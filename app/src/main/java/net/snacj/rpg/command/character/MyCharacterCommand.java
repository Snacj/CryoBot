package net.snacj.rpg.command.character;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MyCharacterCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    public static void execute (SlashCommandInteractionEvent event){
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();

        String[] character;
        character = dbUtil.getChracterFromUser(userId);
        long id = Long.parseLong(character[0]);
        String name= character[1];
        int age = Integer.parseInt(character[2]);
        String klasse = character[3];
        String race = character[4];

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Your Character:");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setFooter("Powered by Snacj.com");
        embedBuilder.addField("Name:", character[1], false);
        embedBuilder.addField("Age:", character[2], false);
        embedBuilder.addField("Class:", character[3], false);
        embedBuilder.addField("Race:", character[4], false);
        event.deferReply().queue(hook -> {
            hook.editOriginalEmbeds(embedBuilder.build()).queue();
        });
    }
}
