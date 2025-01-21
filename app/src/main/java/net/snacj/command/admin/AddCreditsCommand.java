package net.snacj.command.admin;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;
/*
 * AddCreditsCommand class is a command that allows administrators to add credits to a user.
 * This command is only available to users with the Administrator permission.
 */

public class AddCreditsCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        if(member.hasPermission(Permission.ADMINISTRATOR)){
            Member chosenMember = Objects.requireNonNull(event.getOption("user")).getAsMember();
            assert chosenMember != null;
            long userId = chosenMember.getIdLong();
            int credits = Objects.requireNonNull(event.getOption("credits")).getAsInt();

            dbUtil.updateMemberCredits(userId, credits);

            event.reply(credits + " Universal Credits added to member: " + chosenMember.getUser().getName()).setEphemeral(true).queue();
        } else {
            event.reply("You don't have the right permissions!").setEphemeral(true).queue();
        }

    }
}
