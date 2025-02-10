package net.snacj.rpg.command.buy;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.snacj.db.PostgreUtil;
import net.snacj.handler.EffectHandler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for handling the buy drink or food command.
 * It allows the Member to buy drinks or food at the bar.
 */
public class BuyDrinkOrFoodCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    static EffectHandler effectHandler = new EffectHandler();
    static String effect = "";

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null)
            return;
        long MemberId = member.getIdLong();
        String location = dbUtil.getLocationFromMember(MemberId);
        long credits = dbUtil.getCreditsFromMember(MemberId);

        if (!location.equalsIgnoreCase("Bar")) {
            event.reply("You have to go to the Bar to buy food or drinks!").queue();
            return;
        }

        String drinkorfood = Objects.requireNonNull(event.getOption("drinkorfood")).getAsString();

        switch (drinkorfood) {
            case "ale" -> {
                if (credits >= 7) {
                    dbUtil.updateMemberCredits(MemberId, -7);
                    effect = "drunk";
                    dbUtil.updateMemberEffect(MemberId, effect);
                    event.reply("You drank some Starfall Ale and are now drank!").queue();
                    effectHandler.scheduleExecution(5, TimeUnit.MINUTES, MemberId);
                } else {
                    event.reply("You dont have enough credits to buy that Starfall Ale!\nIt costs 7 credits").queue();
                }
            }
            case "strongale" -> {
                if (credits >= 10) {
                    dbUtil.updateMemberCredits(MemberId, -10);
                    effect = "drunk";
                    dbUtil.updateMemberEffect(MemberId, effect);
                    event.reply("You drank strong Starfall Ale and are very drunk now!").queue();
                    effectHandler.scheduleExecution(10, TimeUnit.MINUTES, MemberId);
                } else {
                    event.reply("You dont have enough credits to buy that Starfall Ale!\nIt costs 10 credits").queue();
                }
            }
            case "lightale" -> {
                if (credits >= 5) {
                    dbUtil.updateMemberCredits(MemberId, -5);
                    effect = "drunk";
                    dbUtil.updateMemberEffect(MemberId, effect);
                    event.reply("You drank light Starfall Ale and are a bit drunk now!").queue();
                    effectHandler.scheduleExecution(1, TimeUnit.MINUTES, MemberId);
                } else {
                    event.reply("You dont have enough credits to buy that Starfall Ale!\nIt costs 5 credits").queue();
                }
            }
            default -> event.reply("This is not available at the moment!").queue();
        }
    }
}
