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
            event.reply("Du hast dein Credits heute schon eingesammelt.\nVersuche es morgen erneut!").queue();
        } else {
            dbUtil.updateUserCredits(userId, 500);
            event.reply("Du hast 500 Credits erhalten!").queue();
        }
    }
}
