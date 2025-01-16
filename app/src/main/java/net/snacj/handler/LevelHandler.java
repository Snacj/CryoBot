package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.snacj.util.LogConstants;

public class LevelHandler {
    PostgreUtil dbUtil = new PostgreUtil();
    RoleHandler roleHandler = new RoleHandler();
    public void convert (Member member, long currentXp) {
        long userId = member.getUser().getIdLong();
        int currentLevel = dbUtil.getLevelFromUser(userId);
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
            dbUtil.updateUserLevel(userId, level);
            roleHandler.updateRole(member);
        }
    }

    public static long calcXpForLevel (int level) {
        return (long) Math.pow(level, 2) * 3600;
    }
}
