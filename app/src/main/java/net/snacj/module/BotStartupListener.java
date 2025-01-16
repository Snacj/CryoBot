package net.snacj.module;


import net.snacj.db.PostgreSQLConnection;
import net.snacj.db.PostgreUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import net.snacj.util.LogConstants;
import net.snacj.util.Scheduler;

import java.util.concurrent.CompletableFuture;

/*
 * This class is responsible for listening to the startup of the bot.
 * It initializes the database and sets up the scheduler.
 */
public class BotStartupListener extends ListenerAdapter {
    private static final CompletableFuture<String> guildIdFuture = new CompletableFuture<>();
    private static Guild guild;
    static PostgreUtil dbUtil = new PostgreUtil();

    /*
     * This method is called when the guild is ready.
     * It initializes the database and sets up the scheduler.
     */
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        System.out.println(LogConstants.K + "Guild is ready!");

        // Get guild from ready event
        String guildId = event.getGuild().getId();
        guild = event.getJDA().getGuildById(guildId);
        guildIdFuture.complete(guildId);

        // Setup database
        PostgreSQLConnection dbConnection = PostgreSQLConnection.getInstance();
        dbConnection.initDB(event);
        if (dbConnection.getConnection() != null) {
            System.out.println(LogConstants.K + "Database connection established.");
        } else {
            System.out.println(LogConstants.E + "Failed to make connection!");
        }

        // Scheduler
        Scheduler scheduler = new Scheduler();
        Runnable task = BotStartupListener::daily;
        scheduler.scheduleDailyTask(task);
    }

    /*
     * This method resets the user location and daily credits.
     */
    public static void daily () {
        dbUtil.resetUserLocation();
        dbUtil.resetDailyCredits();
    }

    /*
     * This method returns the guild.
     */
    public static Guild getGuild() {
        return guild;
    }

    /*
     * This method returns the guildIdFuture.
     */
    public static CompletableFuture<String> getGuildIdFuture() {
        return guildIdFuture;
    }
}
