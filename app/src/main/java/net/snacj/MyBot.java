package net.snacj;

import io.github.cdimascio.dotenv.Dotenv;
import net.snacj.module.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
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
                         new SlashCommandInteractListener()
                 )
                 .build();
         initializeCommands();
    }

    /*
     * This method initializes the commands.
     */
    public static void initializeCommands() {
        try {
            String guildId = BotStartupListener.getGuildIdFuture().get();
            Guild guild = getJda().getGuildById(guildId);

            if (guild == null) return;
            //jda.updateCommands().addCommands(
            guild.updateCommands().addCommands(
                    Commands.slash("rpgembed", "Displays RPG embed"),
                    Commands.slash("mycredits", "Shows your credits amount"),
                    Commands.slash("coinflip", "Flip a coin for credits")
                            .addOptions(new OptionData(OptionType.STRING, "side", "Predict the Coinflip", true)
                                            .addChoice("Heads", "heads")
                                            .addChoice("Tails", "tails"),
                                    new OptionData(OptionType.INTEGER, "bet", "Amount of credits to bet", true)),

                    Commands.slash("addcredits", "Admins can Add Credits")
                            .addOption(OptionType.USER, "user", "Which user", true)
                            .addOption(OptionType.INTEGER, "credits", "Amount of Credits to add.", true),

                    Commands.slash("changelocation", "Change Location to...")
                            .addOptions(new OptionData(OptionType.STRING, "location", "Choose desired Location", true)
                            .addChoice("Taverne", "Taverne")
                            .addChoice("Bar", "Bar")
                            .addChoice("Im Freien", "Im Freien")
                            .addChoice("Arena", "Arena")),

                    Commands.slash("buy", "Buy drinks or food at the bar")
                            .addOptions(new OptionData(OptionType.STRING, "drinkorfood", "Choose drink or food to buy", true)
                                    .addChoice("Beer", "beer")
                                    .addChoice("Strong Beer", "strongbeer")
                                    .addChoice("Light Beer", "lightbeer")),

                    Commands.slash("whereami", "Where am I?"),
                    Commands.slash("dailycredits", "Get daily 500 Credits!"),
                    Commands.slash("createchar", "Create a Character for your Adventure!")
                            .addOptions(new OptionData(OptionType.STRING, "name", "Choose name for character!", true))
                            .addOptions(new OptionData(OptionType.STRING, "race", "Choose Race.", true)
                                            .addChoice("Human", "Human")
                                    )
                            .addOptions(new OptionData(OptionType.STRING, "profession", "Choose Profession.", true)
                                            .addChoice("Bounty Hunter", "Bounty Hunter")
                                            .addChoice("Miner", "Miner")
                                            .addChoice("Pilot", "Pilot")
                                            .addChoice("Trader", "Trader"),
                            new OptionData(OptionType.INTEGER, "age", "Age of character", true)),
                    Commands.slash("mychar", "My Character"),
                    Commands.slash("isolde", "Play Isolde")

            ).queue(success -> System.out.println(LogConstants.K + "Commands registered successfully!"),
                    failure -> System.err.println(LogConstants.E + "Failed to register commands: " + failure.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This method returns the JDA instance.
     */
    public static JDA getJda() {
        return jda;
    }
}
