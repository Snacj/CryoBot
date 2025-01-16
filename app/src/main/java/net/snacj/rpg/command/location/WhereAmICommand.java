package net.snacj.rpg.command.location;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

/**
 * This class is responsible for handling the where am I command.
 * It allows the user to see their location.
 */
public class WhereAmICommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    /**
     * This method executes the where am I command.
     * It allows the user to see their location.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();
        event.reply("You are here: " + dbUtil.getLocationFromUser(userId)).queue();
    }
}
