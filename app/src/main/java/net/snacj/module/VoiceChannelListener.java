package net.snacj.module;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.snacj.db.PostgreUtil;
import net.snacj.handler.GoldHandler;
import net.snacj.handler.LevelHandler;
import net.snacj.handler.XpHandler;
import net.snacj.util.LogConstants;

public class VoiceChannelListener extends ListenerAdapter {
    XpHandler xpHandler = new XpHandler();
    LevelHandler levelHandler = new LevelHandler();
    GoldHandler goldHandler = new GoldHandler();
    PostgreUtil dbUtil = new PostgreUtil();

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Member member = event.getEntity();
        long userId = member.getIdLong();
        AudioChannelUnion channelJoined = event.getChannelJoined();
        AudioChannelUnion channelLeft = event.getChannelLeft();
        //Someone joined a channel
        if (channelJoined != null && channelLeft == null){
            System.out.println(LogConstants.I + member.getUser().getName() + " joined audio channel: " + channelJoined.getName());
            xpHandler.startCount(member);
            goldHandler.startCount(member);
            levelHandler.convert(member, dbUtil.getXpFromUser(userId));
        }
        //Someone left a channel
        if (channelJoined == null && channelLeft != null){
            System.out.println(LogConstants.I + member.getUser().getName() + " left audio channel: " + channelLeft.getName());
            xpHandler.stopCount(member);
            goldHandler.stopCount(member);
        }
        //Someone switched channels
        if (channelJoined != null && channelLeft != null){
            System.out.println(LogConstants.I + member.getUser().getName() + " moved between audio channels: "
                    + channelLeft.getName() + " -> " + channelJoined.getName());
        }
    }
}
