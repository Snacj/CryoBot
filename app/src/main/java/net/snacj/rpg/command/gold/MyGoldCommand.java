package net.snacj.rpg.command.gold;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MyGoldCommand {
    static PostgreUtil dbUtil = new PostgreUtil();
    public static void execute (SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        long userId = member.getIdLong();
        long gold = dbUtil.getGoldFromUser(userId);
        event.reply("You currently have " + gold + " gold!").setEphemeral(true).queue();
    }
}
