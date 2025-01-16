package net.snacj.rpg.command.gold;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class DailyGoldCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;
        long userId = member.getIdLong();
        boolean dailyGold = dbUtil.getDailyGoldFromUser(userId);
        dbUtil.updateDailyGold(userId);
        if (dailyGold) {
            event.reply("Du hast dein Gold heute schon eingesammelt.\nVersuche es morgen erneut!").queue();
        } else {
            dbUtil.updateUserGold(userId, 500);
            event.reply("Du hast 500 Gold erhalten!").queue();
        }
    }
}
