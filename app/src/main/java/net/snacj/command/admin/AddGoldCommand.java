package net.snacj.command.admin;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class AddGoldCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        if(member.hasPermission(Permission.ADMINISTRATOR)){
            Member chosenMember = Objects.requireNonNull(event.getOption("user")).getAsMember();
            assert chosenMember != null;
            long userId = chosenMember.getIdLong();
            int gold = Objects.requireNonNull(event.getOption("gold")).getAsInt();

            dbUtil.updateUserGold(userId, gold);

            event.reply(gold + " gold added to member: " + chosenMember.getUser().getName()).setEphemeral(true).queue();
        } else {
            event.reply("You don't have the right permissions!").setEphemeral(true).queue();
        }

    }
}
