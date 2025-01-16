package net.snacj.rpg.command.games;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.snacj.util.LogConstants;

import java.util.Objects;

/**
 * This class is responsible for handling the coinflip command.
 * It allows the user to play a coinflip game.
 */
public class CoinflipCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    /**
     * This method executes the coinflip command.
     * It allows the user to play a coinflip game.
     * @param event
     */
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        long userId = member.getIdLong();
        long currentCredits = dbUtil.getCreditsFromUser(userId);
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
            dbUtil.updateUserCredits(userId, wonCredits);
            event.reply("You win! \nCurrent Credits -> " + dbUtil.getCreditsFromUser(userId) + " Credits.").queue();
        } else {
            wonCredits = bet - (bet * 2);
            dbUtil.updateUserCredits(userId, wonCredits);
            event.reply("You lost! \nCurrent Credits -> " + dbUtil.getCreditsFromUser(userId) + " Credits.").queue();
        }
    }
}
