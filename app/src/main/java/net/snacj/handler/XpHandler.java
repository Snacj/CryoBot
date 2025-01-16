package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Member;
import net.snacj.util.LogConstants;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class XpHandler {
    private final Map<Long, Instant> startTimeMap = new HashMap<>();
    PostgreUtil dbUtil = new PostgreUtil();
    public void startCount(Member member) {
        Long userId = member.getIdLong();
        if(!startTimeMap.containsKey(userId)) {
            startTimeMap.put(userId, Instant.now());
        }
    }
    public void stopCount(Member member) {
        Long userId = member.getIdLong();
        Instant startTime = startTimeMap.remove(userId);
        if (startTime != null) {
            Duration duration = Duration.between(startTime, Instant.now());
            long seconds = duration.toSeconds();
            System.out.println(LogConstants.I + "User ID: " + userId + " was on a channel for " + seconds + " seconds.");
            dbUtil.updateUserTime(userId, seconds);
        }
    }
}
