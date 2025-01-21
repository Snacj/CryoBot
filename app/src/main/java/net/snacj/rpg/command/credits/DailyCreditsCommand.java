package net.snacj.rpg.command.credits;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

/**
 * This class is responsible for handling the daily credits command.
 * It allows the user to get daily credits.
 */
public class DailyCreditsCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    /**
     * This method executes the daily credits command.
     * It allows the user to get daily credits.
     * @param event
     */
    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;
        long userId = member.getIdLong();
        boolean dailyCredits = dbUtil.getDailyCreditsFromUser(userId);
        dbUtil.updateDailyCredits(userId);
        if (dailyCredits) {
            event.reply("You already collected your daily Credits.\nTry again tomorrow!").queue();
        } else {
            dbUtil.updateUserCredits(userId, 500);
            event.reply("You received 500 Credits!").queue();
        }
    }
}
