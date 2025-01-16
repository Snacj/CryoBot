package net.snacj.rpg.command.games;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.snacj.util.LogConstants;

import java.util.Objects;

public class CoinflipCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        long userId = member.getIdLong();
        long currentGold = dbUtil.getGoldFromUser(userId);
        int rand = (int)(Math.random() * 2);
        String seite = "";
        int wonGold;

        if (rand == 0) {
            seite = "heads";
        } else if (rand == 1) {
            seite = "tails";
        } else {
            System.out.println(LogConstants.E + "Error with Random number");
        }
        String choice = Objects.requireNonNull(event.getOption("side")).getAsString();
        int bet = Objects.requireNonNull(event.getOption("bet")).getAsInt();

        if (currentGold < bet) {
            event.reply("Du hast nicht genug Gold!").queue();
        } else if (choice.equalsIgnoreCase(seite)) {
            wonGold = bet;
            dbUtil.updateUserGold(userId, wonGold);
            event.reply("Du hast gewonnen! \nNeuer Goldbestand -> " + dbUtil.getGoldFromUser(userId) + " Gold.").queue();
        } else {
            wonGold = bet - (bet * 2);
            dbUtil.updateUserGold(userId, wonGold);
            event.reply("Du hast verloren! \nNeuer Goldbestand -> " + dbUtil.getGoldFromUser(userId) + " Gold.").queue();
        }
    }
}
