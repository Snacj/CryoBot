package net.snacj.module;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This class listens for new members joining the server.
 * It sends a welcome message to the system channel.
 */
public class WelcomeListener extends ListenerAdapter {
    /**
     * This method is called when a new member joins the server.
     * It sends a welcome message to the system channel.
     *
     * @param event
     */
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel channel = event.getGuild().getSystemChannel();
        if(channel != null) {
            String welcomeMessage = "Welcome to Orion Space Station, " + event.getMember().getAsMention() + "!";
            channel.sendMessage(welcomeMessage).queue();
        } else {
            System.err.println("Welcome channel not found");
        }
    }
}
