package net.snacj.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.snacj.MyBot;
import net.snacj.module.BotStartupListener;
import net.snacj.util.LogConstants;

/**
 * InitCommands
 */
public class InitCommands {

    public static void initializeCommands() {
        try {
            // Get guild id at startup && save guild
            String guildId = BotStartupListener.getGuildIdFuture().get();
            Guild guild = MyBot.getJda().getGuildById(guildId);

            if (guild == null) return;
            // Register commands
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
                            .addChoice("OSS: Medbay", "OSS: Medbay")
                            .addChoice("OSS: Bar", "OSS: Bar")
                            .addChoice("OSS: Hangar", "OSS: Hangar")
                            .addChoice("OSS: Bridge", "OSS: Bridge")),

                    Commands.slash("buy", "Buy drinks or food at the bar")
                            .addOptions(new OptionData(OptionType.STRING, "drinkorfood", "Choose drink or food to buy", true)
                                    .addChoice("Starfall Ale", "ale")
                                    .addChoice("Strong Starfall Ale", "strongale")
                                    .addChoice("Light Starfall Ale", "lightale")),

                    Commands.slash("whereami", "Where am I?"),

                    Commands.slash("dailycredits", "Get daily 500 Credits!"),

                    Commands.slash("createchar", "Create a Character for your Adventure!")
                            .addOptions(new OptionData(OptionType.STRING, "species", "Choose Species.", true)
                                            .addChoice("Human", "Marsian")
                                    )
                            .addOptions(new OptionData(OptionType.STRING, "profession", "Choose Profession.", true)
                                            .addChoice("Bounty Hunter", "Bounty Hunter")
                                            .addChoice("Miner", "Miner")
                                            .addChoice("Pilot", "Pilot")
                                            .addChoice("Trader", "Trader"),
                            new OptionData(OptionType.INTEGER, "age", "Age of character", true)),

                    Commands.slash("mychar", "My Character")

            ).queue(success -> System.out.println(LogConstants.K + "Commands registered successfully!"),
                    failure -> System.err.println(LogConstants.E + "Failed to register commands: " + failure.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
