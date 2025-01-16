package net.snacj.module;

import net.snacj.command.IsoldeCommand;
import net.snacj.command.admin.AddGoldCommand;
import net.snacj.rpg.command.RpgEmbedCommand;
import net.snacj.rpg.command.buy.BuyDrinkOrFoodCommand;
import net.snacj.rpg.command.character.CreateCharacterCommand;
import net.snacj.rpg.command.character.MyCharacterCommand;
import net.snacj.rpg.command.games.CoinflipCommand;
import net.snacj.rpg.command.gold.DailyGoldCommand;
import net.snacj.rpg.command.gold.MyGoldCommand;
import net.snacj.rpg.command.location.ChangeLocationCommand;
import net.snacj.rpg.command.location.WhereAmICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandInteractListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName().toLowerCase()) {
            case "rpgembed" -> RpgEmbedCommand.execute(event);
            case "mygold" -> MyGoldCommand.execute(event);
            case "coinflip" -> CoinflipCommand.execute(event);
            case "addgold" -> AddGoldCommand.execute(event);
            case "isolde" -> IsoldeCommand.execute(event);
            case "changelocation" -> ChangeLocationCommand.execute(event);
            case "whereami" -> WhereAmICommand.execute(event);
            case "buy" -> BuyDrinkOrFoodCommand.execute(event);
            case "dailygold" -> DailyGoldCommand.execute(event);
            case "createchar" -> CreateCharacterCommand.execute(event);
            case "mychar" -> MyCharacterCommand.execute(event);
            default -> event.reply("This command doesn't exist").setEphemeral(true).queue();
        }
    }
}
