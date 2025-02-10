package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.snacj.util.LogConstants;

/*
 * This class is responsible for handling the level system.
 * It converts the user's XP to a level and updates the user's role accordingly.
 */
public class LevelHandler {
    PostgreUtil dbUtil = new PostgreUtil();
    RoleHandler roleHandler = new RoleHandler();

    /*
     * converts the user's XP to a level and updates the user's role accordingly.
     */
    public void convert (Member member, long currentXp) {
        long userId = member.getUser().getIdLong();
        int currentLevel = dbUtil.getLevelFromMember(userId);
        int level = currentLevel;
        long xpForNextLevel = calcXpForLevel(currentLevel + 1);
        System.out.println(LogConstants.I + "current level is: " + currentLevel);
        while (xpForNextLevel <= currentXp) {
            level ++;
            xpForNextLevel = calcXpForLevel(level + 1);
            System.out.println(LogConstants.I + "level increased to: " + level);
        }
        if(currentLevel != level) {
            System.out.println(LogConstants.I + "new level is: " + level);
            dbUtil.updateMemberLevel(userId, level);
            roleHandler.updateRole(member);
        }
    }

    /*
     * calculates the XP required for a certain level.
     */
    public static long calcXpForLevel (int level) {
        return (long) Math.pow(level, 2) * 3600;
    }
}
