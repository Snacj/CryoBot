package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.snacj.module.BotStartupListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.snacj.util.LogConstants;

public class RoleHandler {
    PostgreUtil dbUtil = new PostgreUtil();

    public void updateRole(Member member){

        Guild guild = BotStartupListener.getGuild();
        Role reisender  = guild.getRoleById(1245801721788235858L);
        Role weise  = guild.getRoleById(1245801575637586083L);
        Role dorfbewohner  = guild.getRoleById(715262657732673677L);
        Role knappe  = guild.getRoleById(1014249941901774871L);


        long userId = member.getUser().getIdLong();
        int currLevel = dbUtil.getLevelFromUser(userId);
        if(currLevel < 4 && isNotAdminOrMod(member, guild)) {
            //assign reisender
            assignRole(guild, member, reisender);
        }
        if(currLevel >= 4 && currLevel < 25 && isNotAdminOrMod(member, guild)) {
            //assign weise
            assignRole(guild, member, weise);
            removeRole(guild, member, reisender);
        }
        if(currLevel >= 25 && currLevel < 40 && isNotAdminOrMod(member, guild)) {
            //assign dorfbewohner
            assignRole(guild, member, dorfbewohner);
            removeRole(guild, member, weise);
        }
        if(currLevel >= 40 && isNotAdminOrMod(member, guild)) {
            //assign knappe
            assignRole(guild, member, knappe);
            removeRole(guild, member, dorfbewohner);
        }
    }
    public void removeRole(Guild guild, Member member, Role role) {
        if (member != null) {
            guild.removeRoleFromMember(member, role).queue();
            System.out.println(LogConstants.K + member + " got role removed: " + role);
        } else {
            System.out.println( LogConstants.E + "Member not found.");
        }
    }
    public void assignRole(Guild guild, Member member, Role role) {
        if (member != null) {
            guild.addRoleToMember(member, role).queue();
            System.out.println(LogConstants.K + member + " got role removed: " + role);
        } else {
            System.out.println( LogConstants.E + "Member not found.");
        }
    }
    public boolean isNotAdminOrMod(Member member, Guild guild) {
        Role ritter = guild.getRoleById(715263117268877390L);
        Role koenig = guild.getRoleById(715280287776505947L);
        boolean admin = member.getRoles().contains(koenig);
        boolean mod = member.getRoles().contains(ritter);
        return !admin && !mod;

    }

}
