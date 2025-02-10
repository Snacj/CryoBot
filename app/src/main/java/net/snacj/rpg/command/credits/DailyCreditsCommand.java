package net.snacj.rpg.command.credits;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * This class is responsible for handling the daily credits command.
 * It allows the Member to get daily credits.
 */
public class DailyCreditsCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;
        long MemberId = member.getIdLong();
        boolean dailyCredits = dbUtil.getDailyCreditsFromMember(MemberId);
        dbUtil.updateDailyCredits(MemberId);
        if (dailyCredits) {
            event.reply("You already collected your daily Credits.\nTry again tomorrow!").queue();
        } else {
            dbUtil.updateMemberCredits(MemberId, 500);
            event.reply("You received 500 Credits!").queue();
        }
    }
}
