package net.snacj.rpg.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class RpgEmbedCommand {

    public static void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) {
            event.reply("An error occurred while executing this command.").setEphemeral(true).queue();
            return;
        }
        if (member.hasPermission(Permission.ADMINISTRATOR)) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Snacj's World RPG");
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Powered by Snacj.com");
            embedBuilder.setDescription("This is Snacj's World, a RPG game inside of Discord.");
            event.deferReply().queue(hook -> {
                hook.editOriginalEmbeds(embedBuilder.build()).queue();
            });
            return;
        }
        event.reply("You don't have permission to do this command").setEphemeral(true).queue();
    }
}
