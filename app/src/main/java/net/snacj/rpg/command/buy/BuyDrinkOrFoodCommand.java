package net.snacj.rpg.command.buy;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.snacj.db.PostgreUtil;
import net.snacj.handler.EffectHandler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BuyDrinkOrFoodCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
   static EffectHandler effectHandler = new EffectHandler();
    static String effect = "";
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();
        String location = dbUtil.getLocationFromUser(userId);
        long gold = dbUtil.getGoldFromUser(userId);

        if(!location.equalsIgnoreCase("Bar")) {
            event.reply("Du musst zuerst zur Bar gehen, um Essen und Drinks zu kaufen!").queue();
            return;
        }

        String drinkorfood = Objects.requireNonNull(event.getOption("drinkorfood")).getAsString();

        switch (drinkorfood) {
            case "beer" -> {
                if (gold >= 7) {
                    dbUtil.updateUserGold(userId, -7);
                    effect = "drunk";
                    dbUtil.updateUserEffect(userId, effect);
                    event.reply("Du hast ein Bier getrunken und bist jetzt betrunken!").queue();
                    effectHandler.scheduleExecution(5, TimeUnit.MINUTES, userId);
                } else {
                    event.reply("Du hast nicht genug Geld um Bier zu kaufen!\nEs kostet 7 Gold").queue();
                }
            }
            case "strongbeer" -> {
                if (gold >= 7) {
                    dbUtil.updateUserGold(userId, -10);
                    effect = "drunk";
                    dbUtil.updateUserEffect(userId, effect);
                    event.reply("Du hast ein starkes Bier getrunken und bist jetzt sehr betrunken!").queue();
                    effectHandler.scheduleExecution(10, TimeUnit.MINUTES, userId);
                } else {
                    event.reply("Du hast nicht genug Geld um Bier zu kaufen!\nEs kostet 10 Gold").queue();
                }
            }
            case "lightbeer" -> {
                if (gold >= 7) {
                    dbUtil.updateUserGold(userId, -5);
                    effect = "drunk";
                    dbUtil.updateUserEffect(userId, effect);
                    event.reply("Du hast ein schwaches Bier getrunken und bist jetzt leicht betrunken!").queue();
                    effectHandler.scheduleExecution(1, TimeUnit.MINUTES, userId);
                } else {
                    event.reply("Du hast nicht genug Geld um Bier zu kaufen!\nEs kostet 5 Gold").queue();
                }
            }
            default -> event.reply("Das gibt es hier leider nicht!").queue();
        }

    }


}
