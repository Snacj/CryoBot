package net.snacj.module;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel channel = event.getGuild().getSystemChannel();
        if(channel != null) {
            String welcomeMessage = "Willkommen in der Bardenbude, " + event.getMember().getAsMention() + "!";
            channel.sendMessage(welcomeMessage).queue();
        } else {
            System.err.println("Welcome channel not found");
        }
    }
}
