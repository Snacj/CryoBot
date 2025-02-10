package net.snacj.rpg.command.location;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

/**
 * This class is responsible for handling the change location command.
 * It allows the Member to change their location.
 */
public class ChangeLocationCommand {
    static PostgreUtil dbUtil = new PostgreUtil();

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;
        long MemberId = member.getIdLong();
        String dLocation = Objects.requireNonNull(event.getOption("location")).getAsString();
        String sLocation = dbUtil.getLocationFromMember(MemberId);
        dbUtil.updateMemberLocation(MemberId, dLocation);
        if (dLocation.equalsIgnoreCase(sLocation)) {
            event.reply("You are already here: " + dbUtil.getLocationFromMember(MemberId)).queue();
        } else {
            event.reply("You are now here: " + dbUtil.getLocationFromMember(MemberId)).queue();
        }
    }
}
