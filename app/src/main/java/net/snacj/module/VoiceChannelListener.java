package net.snacj.module;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.snacj.db.PostgreUtil;
import net.snacj.handler.CreditsHandler;
import net.snacj.handler.LevelHandler;
import net.snacj.handler.XpHandler;
import net.snacj.util.LogConstants;

/**
 * This class listens for voice channel events.
 * It starts and stops the count for XP and credits when a user joins or leaves
 * a channel.
 */
public class VoiceChannelListener extends ListenerAdapter {
    XpHandler xpHandler = new XpHandler();
    LevelHandler levelHandler = new LevelHandler();
    CreditsHandler creditsHandler = new CreditsHandler();
    PostgreUtil dbUtil = new PostgreUtil();

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Member member = event.getEntity();
        long userId = member.getIdLong();
        AudioChannelUnion channelJoined = event.getChannelJoined();
        AudioChannelUnion channelLeft = event.getChannelLeft();
        // Someone joined a channel
        if (channelJoined != null && channelLeft == null) {
            System.out.println(
                    LogConstants.I + member.getUser().getName() + " joined audio channel: " + channelJoined.getName());
            xpHandler.startCount(member);
            creditsHandler.startCount(member);
            levelHandler.convert(member, dbUtil.getXpFromMember(userId));
        }
        // Someone left a channel
        if (channelJoined == null && channelLeft != null) {
            System.out.println(
                    LogConstants.I + member.getUser().getName() + " left audio channel: " + channelLeft.getName());
            xpHandler.stopCount(member);
            creditsHandler.stopCount(member);
        }
        // Someone switched channels
        if (channelJoined != null && channelLeft != null) {
            System.out.println(LogConstants.I + member.getUser().getName() + " moved between audio channels: "
                    + channelLeft.getName() + " -> " + channelJoined.getName());
        }
    }
}
