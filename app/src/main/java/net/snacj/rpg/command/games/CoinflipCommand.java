package net.snacj.rpg.command.games;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.snacj.util.LogConstants;

import java.util.Objects;

/**
 * This class is responsible for handling the coinflip command.
 * It allows the Member to play a coinflip game.
 */
public class CoinflipCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        long MemberId = member.getIdLong();
        long currentCredits = dbUtil.getCreditsFromMember(MemberId);
        int rand = (int)(Math.random() * 2);
        String seite = "";
        int wonCredits;

        if (rand == 0) {
            seite = "heads";
        } else if (rand == 1) {
            seite = "tails";
        } else {
            System.out.println(LogConstants.E + "Error with Random number");
        }
        String choice = Objects.requireNonNull(event.getOption("side")).getAsString();
        int bet = Objects.requireNonNull(event.getOption("bet")).getAsInt();

        if (currentCredits < bet) {
            event.reply("You dont have enough credits!").queue();
        } else if (choice.equalsIgnoreCase(seite)) {
            wonCredits = bet;
            dbUtil.updateMemberCredits(MemberId, wonCredits);
            event.reply("You win! \nCurrent Credits -> " + dbUtil.getCreditsFromMember(MemberId) + " Credits.").queue();
        } else {
            wonCredits = bet - (bet * 2);
            dbUtil.updateMemberCredits(MemberId, wonCredits);
            event.reply("You lost! \nCurrent Credits -> " + dbUtil.getCreditsFromMember(MemberId) + " Credits.").queue();
        }
    }
}
