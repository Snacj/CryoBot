package net.snacj.rpg.command.credits;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * This class is responsible for handling the my credits command.
 * It allows the user to see their credits.
 */
public class MyCreditsCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    /**
     * This method executes the my credits command.
     * It allows the user to see their credits.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        long userId = member.getIdLong();
        long credits = dbUtil.getCreditsFromUser(userId);
        event.reply("You currently have " + credits + " credits!").setEphemeral(true).queue();
    }
}
