package net.snacj.rpg.command.location;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class WhereAmICommand {

    static PostgreUtil dbUtil = new PostgreUtil();
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member == null) return;
        long userId = member.getIdLong();
        event.reply("Du bist hier: " + dbUtil.getLocationFromUser(userId)).queue();
    }
}
