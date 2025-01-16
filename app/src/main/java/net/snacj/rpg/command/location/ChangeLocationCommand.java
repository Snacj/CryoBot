package net.snacj.rpg.command.location;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class ChangeLocationCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;
        long userId = member.getIdLong();
        String dLocation = Objects.requireNonNull(event.getOption("location")).getAsString();
        String sLocation = dbUtil.getLocationFromUser(userId);
        dbUtil.updateUserLocation(userId, dLocation);
        if (dLocation.equalsIgnoreCase(sLocation)) {
            event.reply("Du bist schon hier: " + dbUtil.getLocationFromUser(userId)).queue();
        } else {
            event.reply("Du bist jetzt hier: " + dbUtil.getLocationFromUser(userId)).queue();
        }
    }
}
