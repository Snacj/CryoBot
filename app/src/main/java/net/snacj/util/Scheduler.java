package net.snacj.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for scheduling tasks.
 */
public class Scheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * This method schedules a task to run daily.
     * @param task
     */
    public void scheduleDailyTask(Runnable task) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.with(LocalTime.MIDNIGHT);

        if (!now.isBefore(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }

        long initialDelay = Duration.between(now, nextRun).getSeconds();

        scheduler.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);

    }

    /**
     * This method shuts down the scheduler.
     */
    public void shutdown() {
        scheduler.shutdown();
    }
}
