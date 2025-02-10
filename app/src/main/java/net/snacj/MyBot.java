package net.snacj;

import io.github.cdimascio.dotenv.Dotenv;
import net.snacj.command.InitCommands;
import net.snacj.module.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.snacj.util.LogConstants;

/*
 * This is the main class of the bot.
 * It initializes the bot and registers the commands.
 */
public class MyBot {
    private static JDA jda;

    public static void main(String[] args) {

        System.out.println(System.getProperty(LogConstants.I + "java.class.path"));

        Dotenv dotenv = Dotenv.load();
        String BOT_TOKEN = dotenv.get("BOT_TOKEN");

        jda = JDABuilder.createDefault(BOT_TOKEN)
                .disableCache(CacheFlag.EMOJI, CacheFlag.STICKER)
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.customStatus("exploring Space."))
                .addEventListeners(
                        new BotStartupListener(),
                        new WelcomeListener(),
                        new VoiceChannelListener(),
                        new SlashCommandInteractListener())
                .build();
        InitCommands.initializeCommands();
    }

    public static JDA getJda() {
        return jda;
    }
}
