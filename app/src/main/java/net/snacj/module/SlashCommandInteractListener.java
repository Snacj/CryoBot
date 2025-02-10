package net.snacj.module;

import net.snacj.command.admin.AddCreditsCommand;
import net.snacj.rpg.command.RpgEmbedCommand;
import net.snacj.rpg.command.buy.BuyDrinkOrFoodCommand;
import net.snacj.rpg.command.character.CreateCharacterCommand;
import net.snacj.rpg.command.character.MyCharacterCommand;
import net.snacj.rpg.command.games.CoinflipCommand;
import net.snacj.rpg.command.credits.DailyCreditsCommand;
import net.snacj.rpg.command.credits.MyCreditsCommand;
import net.snacj.rpg.command.location.ChangeLocationCommand;
import net.snacj.rpg.command.location.WhereAmICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/*
 * This class is responsible for listening to slash command interactions.
 * It executes the appropriate command based on the command name.
 */
public class SlashCommandInteractListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName().toLowerCase()) {
            case "rpgembed" -> RpgEmbedCommand.execute(event);
            case "mycredits" -> MyCreditsCommand.execute(event);
            case "coinflip" -> CoinflipCommand.execute(event);
            case "addcredits" -> AddCreditsCommand.execute(event);
            case "changelocation" -> ChangeLocationCommand.execute(event);
            case "whereami" -> WhereAmICommand.execute(event);
            case "buy" -> BuyDrinkOrFoodCommand.execute(event);
            case "dailycredits" -> DailyCreditsCommand.execute(event);
            case "createchar" -> CreateCharacterCommand.execute(event);
            case "mychar" -> MyCharacterCommand.execute(event);
            default -> event.reply("This command doesn't exist").setEphemeral(true).queue();
        }
    }
}
