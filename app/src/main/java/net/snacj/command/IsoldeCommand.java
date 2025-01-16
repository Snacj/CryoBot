package net.snacj.command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class IsoldeCommand {
    public static void execute (SlashCommandInteractionEvent event) {
        event.reply("Isolde aus dem Fenster springen.").queue();
    }
}
