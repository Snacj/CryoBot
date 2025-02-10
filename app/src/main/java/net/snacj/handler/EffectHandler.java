package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.snacj.util.LogConstants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * This class is responsible for handling the effects system.
 * It schedules the removal of an effect after a certain amount of time.
 */
public class EffectHandler {
    PostgreUtil dbUtil = new PostgreUtil();

    /*
     * schedules the removal of an effect after a certain amount of
     * time.
     */
    public void scheduleExecution(long delay, TimeUnit timeUnit, long userId) {
        System.out.println(LogConstants.K + "waiting for " + delay + " minutes...");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            removeEffect(userId);
            scheduler.shutdown();
        }, delay, timeUnit);
    }

    /*
     * removes the effect from the user.
     */
    public void removeEffect(long userId) {
        dbUtil.updateMemberEffect(userId, "none");
    }
}
