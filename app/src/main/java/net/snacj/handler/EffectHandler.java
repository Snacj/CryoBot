package net.snacj.handler;

import net.snacj.db.PostgreUtil;
import net.snacj.util.LogConstants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EffectHandler {
    PostgreUtil dbUtil = new PostgreUtil();

    public void scheduleExecution(long delay, TimeUnit timeUnit, long userId){
        System.out.println(LogConstants.K + "waiting for " + delay + " minutes...");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            removeEffect(userId);
            scheduler.shutdown();
        }, delay, timeUnit);
    }
    public void removeEffect(long userId) {
        dbUtil.updateUserEffect(userId, "none");
    }
}
