package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.snacj.module.BotStartupListener;

import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.snacj.util.LogConstants;

/*
 * This class is responsible for handling the role system.
 * It updates the user's role based on their level.
 */
public class RoleHandler {
    private final int moderationRoles = 2;
    private final int specialRoles = 3;
    PostgreUtil dbUtil = new PostgreUtil();

    /*
     * updates the user's role based on their level.
     */
    public void updateRole(Member member) {

        Guild guild = BotStartupListener.getGuild();

        List<Role> roles = guild.getRoles();

        long userId = member.getUser().getIdLong();
        int currLevel = dbUtil.getLevelFromMember(userId);
        if (currLevel < 4 && isNotAdminOrMod(member, guild)) {
            assignRole(guild, member, roles.get(5 + moderationRoles + specialRoles));
        }
        if (currLevel >= 4 && currLevel < 25 && isNotAdminOrMod(member, guild)) {
            removeRole(guild, member, roles.get(5 + moderationRoles + specialRoles));
            assignRole(guild, member, roles.get(4 + moderationRoles + specialRoles));
        }
        if (currLevel >= 25 && currLevel < 40 && isNotAdminOrMod(member, guild)) {
            removeRole(guild, member, roles.get(4 + moderationRoles + specialRoles));
            assignRole(guild, member, roles.get(3 + moderationRoles + specialRoles));
        }
        if (currLevel >= 40 && isNotAdminOrMod(member, guild)) {
            removeRole(guild, member, roles.get(3 + moderationRoles + specialRoles));
            assignRole(guild, member, roles.get(2 + moderationRoles + specialRoles));
        }
        if (currLevel >= 60 && isNotAdminOrMod(member, guild)) {
            removeRole(guild, member, roles.get(2 + moderationRoles + specialRoles));
            assignRole(guild, member, roles.get(1 + moderationRoles + specialRoles));
        }
        if (currLevel >= 90 && isNotAdminOrMod(member, guild)) {
            removeRole(guild, member, roles.get(1 + moderationRoles + specialRoles));
            assignRole(guild, member, roles.get(0 + moderationRoles + specialRoles));
        }
    }

    /*
     * removes a role from a user.
     */
    public void removeRole(Guild guild, Member member, Role role) {
        if (member != null) {
            guild.removeRoleFromMember(member, role).queue();
            System.out.println(LogConstants.K + member + " got role removed: " + role);
        } else {
            System.out.println(LogConstants.E + "Member not found.");
        }
    }

    /*
     * assigns a role to a user.
     */
    public void assignRole(Guild guild, Member member, Role role) {
        if (member != null) {
            guild.addRoleToMember(member, role).queue();
            System.out.println(LogConstants.K + member + " got role removed: " + role);
        } else {
            System.out.println(LogConstants.E + "Member not found.");
        }
    }

    /*
     * checks if a user is not an admin or mod.
     */
    public boolean isNotAdminOrMod(Member member, Guild guild) {
        List<Role> roles = guild.getRoles();
        for (int i = 0; i < moderationRoles + specialRoles; i++) {
            if (member.getRoles().contains(roles.get(i))) {
                return false;
            }
        }
        return true;
    }
}
