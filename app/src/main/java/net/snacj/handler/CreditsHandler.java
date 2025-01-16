package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.snacj.util.LogConstants;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/*
 * This class is responsible for handling the credits system.
 * It keeps track of the time a user is active and rewards them with credits.
 */
public class CreditsHandler {
    private final Map<Long, Instant> startTimeMap = new HashMap<>();
    PostgreUtil dbUtil = new PostgreUtil();

    /*
     * This method starts the count for a user.
     * It stores the start time in the startTimeMap.
     */
    public void startCount(Member member) {
        Long userId = member.getIdLong();
        if(!startTimeMap.containsKey(userId)) {
            startTimeMap.put(userId, Instant.now());
        }
    }

    /*
     * This method stops the count for a user.
     * It calculates the duration the user was active and rewards them with credits.
     */
    public void stopCount(Member member) {
        long userId = member.getIdLong();
        Instant startTime = startTimeMap.remove(userId);
        if (startTime != null) {
            Duration duration = Duration.between(startTime, Instant.now());
            long credits = duration.toMinutes();
            System.out.println(LogConstants.I + "User ID: " + userId + " earned " + credits + " credits.");
            dbUtil.updateUserCredits(userId, credits);
        }
    }

    /*
     * This method rewards users with daily credits.
     */
    public void dailyCredits (Member member) {
        long userId = member.getIdLong();

    }
}
